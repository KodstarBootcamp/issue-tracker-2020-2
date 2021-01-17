package com.kodstar.issuetracker.repo;

import com.kodstar.issuetracker.entity.Comment;
import com.kodstar.issuetracker.entity.Issue;
import com.kodstar.issuetracker.entity.IssueHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IssueHistoryRepository extends JpaRepository<IssueHistory,Long> {
    List<IssueHistory> findByIssue(Issue issue);
    Optional<IssueHistory> findByComment(Comment comment);
}
