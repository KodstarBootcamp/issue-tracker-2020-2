package com.kodstar.issuetracker.service;

import com.kodstar.issuetracker.dto.*;

import com.kodstar.issuetracker.entity.Comment;
import com.kodstar.issuetracker.entity.Issue;
import com.kodstar.issuetracker.entity.Label;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestParam;


import java.security.Principal;
import java.util.List;


public interface IssueService {

    IssueDTO createIssue(IssueDTO issue);

    IssueDTO editIssue(Long issueId, IssueDTO issue);

    void deleteIssue(Long issueId);

    PagesDTO<IssueDTO> getAllIssues(Pageable paging);

    IssueDTO findById(Long issueId);

    void deleteSelectedIssues(List<Long> selectedIssueIds);

    List<IssueDTO> findALlByTitleKeyword(String keyword);

    List<IssueDTO> findALlByDescKeyword(String keyword);

    List<IssueDTO> findAllByLabelsContains(Long labelId);

    IssueDTO addComment(Long issueId, CommentDTO commentDTO);

    void deleteComment(Long issueId, Long commentId);

    PagesDTO<IssueDTO> getAllIssuesOrderByCreateTime(boolean isAscending, Pageable paging);

    IssueDTO updateState(Long issueId, Long stateId);

    PagesDTO<IssueDTO> getAllIssuesOrderByUpdateTime(boolean isAscending, Pageable paging);

    PagesDTO<IssueDTO> getAllIssuesSort(String orderType, String byWhichSort, Pageable paging);
    IssueDTO addLabel(Long labelId, Long issueId);

    IssueDTO removeLabelFromIssue(Long labelId, Long issueId);

    IssueDTO addAssignee(Long userId, Long issueId);

    IssueDTO removeAssigneeFromIssue(Long userId, Long issueId);

    List<IssueDTO> findALlIssuesByCurrentUser(Principal principal);

    List<IssueHistoryDTO> getHistoryInformation(Long issueId);
}
