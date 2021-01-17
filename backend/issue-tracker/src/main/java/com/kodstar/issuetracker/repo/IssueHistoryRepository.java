package com.kodstar.issuetracker.repo;

import com.kodstar.issuetracker.entity.Issue;
import com.kodstar.issuetracker.entity.IssueHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueHistoryRepository extends JpaRepository<IssueHistory,Long> {
    List<IssueHistory> findByIssue(Issue issue);
}
