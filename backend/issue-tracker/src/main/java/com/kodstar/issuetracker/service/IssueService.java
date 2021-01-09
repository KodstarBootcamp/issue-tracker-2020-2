package com.kodstar.issuetracker.service;

import com.kodstar.issuetracker.dto.CommentDTO;
import com.kodstar.issuetracker.dto.IssueDTO;

import com.kodstar.issuetracker.dto.PagesDTO;
import org.springframework.data.domain.Pageable;


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

    List<IssueDTO> findALlIssuesByLabel(String keyword);

    IssueDTO addComment(Long issueId, CommentDTO commentDTO);

    void deleteComment(Long issueId, Long commentId);

    PagesDTO<IssueDTO> getAllIssuesOrderByCreateTime(boolean isAscending, Pageable paging);

    IssueDTO updateState(Long issueId, Long stateId);

    PagesDTO<IssueDTO> getAllIssuesOrderByUpdateTime(boolean isAscending, Pageable paging);

    PagesDTO<IssueDTO> getAllIssuesSort(String orderType, String byWhichSort, Pageable paging);

}
