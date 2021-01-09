package com.kodstar.issuetracker.service.impl;

import com.kodstar.issuetracker.dto.CommentDTO;
import com.kodstar.issuetracker.dto.IssueDTO;
import com.kodstar.issuetracker.dto.PagesDTO;
import com.kodstar.issuetracker.entity.Comment;
import com.kodstar.issuetracker.entity.Issue;
import com.kodstar.issuetracker.entity.State;
import com.kodstar.issuetracker.exceptionhandler.InvalidQueryParameterException;
import com.kodstar.issuetracker.exceptionhandler.IssueTrackerNotFoundException;
import com.kodstar.issuetracker.repo.IssueRepository;
import com.kodstar.issuetracker.repo.StateRepository;
import com.kodstar.issuetracker.service.CommentService;
import com.kodstar.issuetracker.service.IssueService;
import com.kodstar.issuetracker.utils.impl.FromCommentDTOToComment;
import com.kodstar.issuetracker.utils.impl.FromIssueToIssueDTO;
import com.kodstar.issuetracker.utils.impl.FromIssueDTOToIssue;
import com.kodstar.issuetracker.utils.impl.FromLabelToLabelDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class IssueServiceImpl implements IssueService {


    private final IssueRepository issueRepository;
    private final ModelMapper modelMapper;
    private final FromIssueToIssueDTO fromIssueToIssueDTO;
    private final FromIssueDTOToIssue fromIssueDTOToIssue;
    private final FromLabelToLabelDTO fromLabelToLabelDTO;
    private final CommentService commentService;
    private final FromCommentDTOToComment fromCommentDTOtoComment;

    private final StateRepository stateRepository;

    private final static String ASCENDING = "asc";
    private final static String DESCENDING = "desc";
    private final static String ORDER_TYPE_ERROR_MESSAGE = " Recieved OrderType is : %s\nOrder Type must be asc or desc";
    private final static String SORT_TYPE_ERROR_MESSAGE = " Sort Type can be just createDate or update";

    @Autowired
    public IssueServiceImpl(IssueRepository issueRepository, ModelMapper modelMapper,
                            FromIssueToIssueDTO fromIssueToIssueDTO, FromIssueDTOToIssue fromIssueDTOToIssue,
                            FromLabelToLabelDTO fromLabelToLabelDTO, CommentService commentService,
                            FromCommentDTOToComment fromCommentDTOtoComment, StateRepository stateRepository) {

        this.issueRepository = issueRepository;
        this.modelMapper = modelMapper;
        this.fromIssueToIssueDTO = fromIssueToIssueDTO;
        this.fromIssueDTOToIssue = fromIssueDTOToIssue;
        this.fromLabelToLabelDTO = fromLabelToLabelDTO;
        this.commentService = commentService;
        this.fromCommentDTOtoComment = fromCommentDTOtoComment;
        this.stateRepository = stateRepository;
    }

    @Override
    public IssueDTO createIssue(IssueDTO idt) {
        Issue issue = fromIssueDTOToIssue.convert(idt);
        IssueDTO issueDto = fromIssueToIssueDTO.convert(issueRepository.save(issue));
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
        List<IssueDTO> issueDTOList = fromIssueToIssueDTO.convertAll(issueRepository.findALlByTitleKeyword(keyword));
        return issueDTOList;
    }

    @Override
    public List<IssueDTO> findALlByDescKeyword(String keyword) {
        List<IssueDTO> issueDTOList = fromIssueToIssueDTO.convertAll(issueRepository.findALlByDescKeyword(keyword));
        return issueDTOList;
    }

    @Override
    public List<IssueDTO> findALlIssuesByLabel(String keyword) {
        List<IssueDTO> issueDTOList = fromIssueToIssueDTO.convertAll(issueRepository.findALlIssuesByLabel(keyword));
        return issueDTOList;
    }

    @Transactional
    @Override
    public IssueDTO addComment(Long issueId, CommentDTO commentDTO) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(NoSuchElementException::new);
        Comment addedComment = commentService.createComment(fromCommentDTOtoComment.convert(commentDTO));
        issue.getComments().add(addedComment);
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
    public IssueDTO updateState(Long issueId, Long stateId) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new IssueTrackerNotFoundException("Issue", issueId.toString()));
        State state = stateRepository.findById(stateId)
                .orElseThrow(() -> new IssueTrackerNotFoundException("State", stateId.toString()));
        issue.setState(state);
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
    public IssueDTO editIssue(Long issueId, IssueDTO issue) {
        Issue updatedIssue = issueRepository.findById(issueId)
                .orElseThrow(NoSuchElementException::new);

        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(issue, updatedIssue);

        IssueDTO issueDTO = fromIssueToIssueDTO.convert(issueRepository.save(updatedIssue));

        return issueDTO;

    }

    @Override
    public void deleteIssue(Long issueId) {
        issueRepository.deleteById(issueId);
    }


    @Override
    public void deleteSelectedIssues(List<Long> selectedIssueIds) {
        for (Long id : selectedIssueIds) {
            deleteIssue(id);
        }
    }

}
