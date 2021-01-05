package com.kodstar.issuetracker.service;

import com.kodstar.issuetracker.dto.CommentDTO;
import com.kodstar.issuetracker.dto.IssueDTO;

import java.util.List;


public interface IssueService {

    IssueDTO createIssue(IssueDTO issue);

    IssueDTO editIssue(Long issueId, IssueDTO issue);

    void deleteIssue(Long issueId);

    List<IssueDTO> getAllIssues();

    IssueDTO findById(Long issueId);


    void deleteSelectedIssues(List<Long> selectedIssueIds);

    List<IssueDTO> findALlByTitleKeyword(String keyword);

    List<IssueDTO> findALlByDescKeyword(String keyword);

    List<IssueDTO> findALlIssuesByLabel(Long labelId);

    IssueDTO addComment(Long issueId, CommentDTO commentDTO);

    void deleteComment(Long issueId,Long commentId);

    List<IssueDTO> getAllIssuesOrderByCreateTime(boolean isAscending);

    IssueDTO updateState(Long issueId, Long stateId);



}
