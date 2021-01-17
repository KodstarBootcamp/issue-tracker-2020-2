package com.kodstar.issuetracker.repo;

import com.kodstar.issuetracker.entity.IssueHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueHistoryRepository extends JpaRepository<IssueHistory,Long> {
}
