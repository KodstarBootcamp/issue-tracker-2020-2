package com.kodstar.issuetracker.service.impl;

import com.kodstar.issuetracker.auth.User;
import com.kodstar.issuetracker.dto.CommentDTO;
import com.kodstar.issuetracker.dto.IssueDTO;
import com.kodstar.issuetracker.dto.IssueHistoryDTO;
import com.kodstar.issuetracker.dto.PagesDTO;
import com.kodstar.issuetracker.entity.*;
import com.kodstar.issuetracker.exceptionhandler.InvalidQueryParameterException;
import com.kodstar.issuetracker.exceptionhandler.IssueTrackerNotFoundException;
import com.kodstar.issuetracker.repo.*;
import com.kodstar.issuetracker.service.CommentService;
import com.kodstar.issuetracker.service.IssueService;
import com.kodstar.issuetracker.utils.impl.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.*;


@Service
public class IssueServiceImpl implements IssueService {


    private final IssueRepository issueRepository;
    private final LabelRepository labelRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final FromIssueToIssueDTO fromIssueToIssueDTO;
    private final FromIssueDTOToIssue fromIssueDTOToIssue;
    private final CommentService commentService;
    private final FromCommentDTOToComment fromCommentDTOtoComment;
    private final StateRepository stateRepository;
    private final IssueHistoryRepository issueHistoryRepository;
    private final FromIssueHistoryToIssueHistoryDTO fromIssueHistoryToIssueHistoryDTO;

    private final static String ASCENDING = "asc";
    private final static String DESCENDING = "desc";
    private final static String ORDER_TYPE_ERROR_MESSAGE = " Recieved OrderType is : %s\nOrder Type must be asc or desc";
    private final static String SORT_TYPE_ERROR_MESSAGE = " Sort Type can be just createDate or update";

    @Autowired
    public IssueServiceImpl(IssueRepository issueRepository, LabelRepository labelRepository, UserRepository userRepository, ModelMapper modelMapper,
                            FromIssueToIssueDTO fromIssueToIssueDTO, FromIssueDTOToIssue fromIssueDTOToIssue,
                            CommentService commentService,
                            FromCommentDTOToComment fromCommentDTOtoComment, StateRepository stateRepository,
                            IssueHistoryRepository issueHistoryRepository,
                            FromIssueHistoryToIssueHistoryDTO fromIssueHistoryToIssueHistoryDTO) {

        this.issueRepository = issueRepository;
        this.labelRepository = labelRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.fromIssueToIssueDTO = fromIssueToIssueDTO;
        this.fromIssueDTOToIssue = fromIssueDTOToIssue;
        this.commentService = commentService;
        this.fromCommentDTOtoComment = fromCommentDTOtoComment;
        this.stateRepository = stateRepository;
        this.issueHistoryRepository = issueHistoryRepository;
        this.fromIssueHistoryToIssueHistoryDTO=fromIssueHistoryToIssueHistoryDTO;
    }

    @Override
    @Transactional
    public IssueDTO createIssue(IssueDTO idt) {
        Issue issue = fromIssueDTOToIssue.convert(idt);
        issue = issueRepository.save(issue);
        IssueDTO issueDto = fromIssueToIssueDTO.convert(issue);
        IssueHistory issueHistory = new IssueHistory();
        issueHistory.setIssue(issue);
        issueHistory.setHistoryType(HistoryType.ISSUE_CREATED);
        issueHistoryRepository.save(issueHistory);
        return issueDto;
    }

    @Override
    public PagesDTO<IssueDTO> getAllIssues(Pageable paging) {
        Page<Issue> pages = issueRepository.findAll(paging);
        PagesDTO<IssueDTO> pagesDTO = new PagesDTO<>();
        modelMapper.map(pages, pagesDTO);
        return pagesDTO;
    }

    @Override
    public IssueDTO findById(Long issueId) {
        IssueDTO issueDTO = fromIssueToIssueDTO.convert(
                issueRepository.findById(issueId)
                        .orElseThrow(NoSuchElementException::new));

        return issueDTO;
    }

    @Override
    public List<IssueDTO> findALlByTitleKeyword(String keyword) {
        List<IssueDTO> issueDTOList = fromIssueToIssueDTO.convertAll(issueRepository.findALlByTitleContaining(keyword));
        return issueDTOList;
    }

    @Override
    public List<IssueDTO> findALlByDescKeyword(String keyword) {
        List<IssueDTO> issueDTOList = fromIssueToIssueDTO.convertAll(issueRepository.findALlByDescriptionContaining(keyword));
        return issueDTOList;
    }

    @Override
    public List<IssueDTO> findAllByLabelsContains(Long labelId) {

        Label label = labelRepository.findById(labelId)
                .orElseThrow(NoSuchElementException::new);

        List<IssueDTO> issueDTOList = fromIssueToIssueDTO.convertAll(issueRepository.findAllByLabelsContains(label));
        return issueDTOList;
    }

    @Transactional
    @Override
    public IssueDTO addComment(Long issueId, CommentDTO commentDTO) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(NoSuchElementException::new);
        Comment addedComment = commentService.createComment(fromCommentDTOtoComment.convert(commentDTO));
        issue.getComments().add(addedComment);

        IssueHistory issueHistory = new IssueHistory();
        issueHistory.setIssue(issue);
        issueHistory.setHistoryType(HistoryType.COMMENT_ADDED);
        issueHistory.setComment(addedComment);
        issueHistoryRepository.save(issueHistory);
        return fromIssueToIssueDTO.convert(issueRepository.save(issue));
    }

    @Override
    public void deleteComment(Long issueId, Long commentId) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new IssueTrackerNotFoundException("Issue", issueId.toString()));
        Optional<Comment> comment = issue.getComments().stream()
                .filter(x -> x.getId().equals(commentId))
                .findFirst();
        if (comment.isPresent()) {
            issue.getComments().remove(comment.get());
            IssueHistory issueHistory = issueHistoryRepository.findByComment(comment.get())
                    .orElseThrow(() -> new IssueTrackerNotFoundException("Issue History with Comment"+commentId+" is not found"));
            issueHistoryRepository.delete(issueHistory);
        } else {
            throw new IssueTrackerNotFoundException("Comment", commentId.toString());
        }
        issueRepository.save(issue);
    }

    @Override
    public PagesDTO<IssueDTO> getAllIssuesOrderByCreateTime(boolean isAscending, Pageable paging) {
        if (isAscending) {

            Page<Issue> pages = issueRepository.findAllByOrderByCreateTime(paging);
            PagesDTO<IssueDTO> pagesDTO = new PagesDTO<>();
            modelMapper.map(pages, pagesDTO);
            return pagesDTO;

        } else {
            Page<Issue> pages = issueRepository.findAllByOrderByCreateTimeDesc(paging);
            PagesDTO<IssueDTO> pagesDTO = new PagesDTO<>();
            modelMapper.map(pages, pagesDTO);
            return pagesDTO;
        }

    }

    @Override
    @Transactional
    public IssueDTO updateState(Long issueId, Long stateId) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new IssueTrackerNotFoundException("Issue", issueId.toString()));
        State state = stateRepository.findById(stateId)
                .orElseThrow(() -> new IssueTrackerNotFoundException("State", stateId.toString()));
        issue.setState(state);

        IssueHistory issueHistory = new IssueHistory();
        issueHistory.setIssue(issue);
        issueHistory.setHistoryType(HistoryType.STATE_CHANGED);
        issueHistory.setState(state);
        issueHistoryRepository.save(issueHistory);

        return fromIssueToIssueDTO.convert(issueRepository.save(issue));
    }

    @Override
    public PagesDTO<IssueDTO> getAllIssuesOrderByUpdateTime(boolean isAscending, Pageable paging) {
        if (isAscending) {
            Page<Issue> pages = issueRepository.findAllByOrderByUpdateTime(paging);
            PagesDTO<IssueDTO> pagesDTO = new PagesDTO<>();
            modelMapper.map(pages, pagesDTO);
            return pagesDTO;

        } else {
            Page<Issue> pages = issueRepository.findAllByOrderByUpdateTimeDesc(paging);
            PagesDTO<IssueDTO> pagesDTO = new PagesDTO<>();
            modelMapper.map(pages, pagesDTO);
            return pagesDTO;

        }

    }

    @Override
    public PagesDTO<IssueDTO> getAllIssuesSort(String orderType, String byWhichSort, Pageable paging) {
        if (byWhichSort == null) {
            return getAllIssues(paging);
        }

        if (byWhichSort.equalsIgnoreCase("createDate")) {
            if (orderType.equalsIgnoreCase(ASCENDING)) {
                return getAllIssuesOrderByCreateTime(true, paging);
            } else if (orderType.equalsIgnoreCase(DESCENDING)) {
                return getAllIssuesOrderByCreateTime(false, paging);
            } else {
                throw new InvalidQueryParameterException(String.format(ORDER_TYPE_ERROR_MESSAGE, orderType));
            }
        } else if (byWhichSort.equalsIgnoreCase("update")) {
            if (orderType.equalsIgnoreCase(ASCENDING)) {
                return getAllIssuesOrderByUpdateTime(true, paging);

            } else if (orderType.equalsIgnoreCase(DESCENDING)) {
                return getAllIssuesOrderByUpdateTime(false, paging);
            } else {
                throw new InvalidQueryParameterException(String.format(ORDER_TYPE_ERROR_MESSAGE, orderType));
            }

        } else {
            throw new InvalidQueryParameterException(SORT_TYPE_ERROR_MESSAGE);
        }
    }

    @Override
    @Transactional
    public IssueDTO removeLabelFromIssue(Long labelId, Long issueId) {

        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(NoSuchElementException::new);

        Label label = labelRepository.findById(labelId)
                .orElseThrow(NoSuchElementException::new);

        issue.getLabels().remove(label);

        IssueHistory issueHistory = new IssueHistory();
        issueHistory.setIssue(issue);
        issueHistory.setHistoryType(HistoryType.LABEL_REMOVED);
        HashSet<Label> labels = new HashSet<>();
        labels.add(label);
        issueHistory.setLabels(labels);
        issueHistoryRepository.save(issueHistory);
        return fromIssueToIssueDTO.convert(issueRepository.save(issue));
    }


    @Override
    @Transactional
    public IssueDTO addLabel(Long labelId, Long issueId) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(NoSuchElementException::new);

        Label label = labelRepository.findById(labelId)
                .orElseThrow(NoSuchElementException::new);
        issue.getLabels().add(label);
        System.out.println(issue.getLabels());

        IssueHistory issueHistory = new IssueHistory();
        issueHistory.setIssue(issue);
        issueHistory.setHistoryType(HistoryType.LABEL_ADDED);
        HashSet<Label> labels = new HashSet<>();
        labels.add(label);
        issueHistory.setLabels(labels);
        issueHistoryRepository.save(issueHistory);

        return fromIssueToIssueDTO.convert(issueRepository.save(issue));
    }

    @Override
    @Transactional
    public IssueDTO editIssue(Long issueId, IssueDTO issue) {
        Issue updatedIssue = issueRepository.findById(issueId)
                .orElseThrow(NoSuchElementException::new);

        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(issue, updatedIssue);
        IssueDTO issueDTO = fromIssueToIssueDTO.convert(issueRepository.save(updatedIssue));
        IssueHistory issueHistory = new IssueHistory();
        issueHistory.setIssue(updatedIssue);
        issueHistory.setHistoryType(HistoryType.ISSUE_MODIFIED);
        issueHistoryRepository.save(issueHistory);
        return issueDTO;

    }

    @Override
    @Transactional
    public void deleteIssue(Long issueId) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(NoSuchElementException::new);
        IssueHistory issueHistory = new IssueHistory();
        issueHistory.setIssue(issue);
        issueHistory.setHistoryType(HistoryType.ISSUE_DELETED);
        issueHistoryRepository.save(issueHistory);
        issueRepository.deleteById(issueId);

    }


    @Override
    public void deleteSelectedIssues(List<Long> selectedIssueIds) {
        for (Long id : selectedIssueIds) {
            deleteIssue(id);
        }
    }

    @Override
    @Transactional
    public IssueDTO addAssignee(Long userId, Long issueId) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(NoSuchElementException::new);

        User user = userRepository.findById(userId)
                .orElseThrow(NoSuchElementException::new);

        issue.getAssignees().add(user);

        IssueHistory issueHistory = new IssueHistory();
        issueHistory.setIssue(issue);
        issueHistory.setHistoryType(HistoryType.ASSIGNEE_ADDED);
        Set<User> assignees = new HashSet<>();
        assignees.add(user);
        issueHistory.setAssignee(user);
        issueHistoryRepository.save(issueHistory);
        return fromIssueToIssueDTO.convert(issueRepository.save(issue));
    }

    @Override
    @Transactional
    public IssueDTO removeAssigneeFromIssue(Long userId, Long issueId) {

        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(NoSuchElementException::new);

        User user = userRepository.findById(userId)
                .orElseThrow(NoSuchElementException::new);

        issue.getAssignees().remove(user);
        IssueHistory issueHistory = new IssueHistory();
        issueHistory.setIssue(issue);
        issueHistory.setHistoryType(HistoryType.ASSIGNEE_REMOVED);
        Set<User> assignees = new HashSet<>();
        assignees.add(user);
        issueHistory.setAssignee(user);
        issueHistoryRepository.save(issueHistory);
        return fromIssueToIssueDTO.convert(issueRepository.save(issue));
    }

    @Override
    public List<IssueDTO> findALlIssuesByCurrentUser(Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        List<Issue> issues = issueRepository.findAllByAssigneesContains(user);
        return fromIssueToIssueDTO.convertAll(issues);

    }

    @Override
    public List<IssueHistoryDTO> getHistoryInformation(Long issueId) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new IssueTrackerNotFoundException("Issue", issueId.toString()));
        List<IssueHistory> issueHistories=issueHistoryRepository.findByIssue(issue);
        return fromIssueHistoryToIssueHistoryDTO.convertAll(issueHistories);
    }

}
