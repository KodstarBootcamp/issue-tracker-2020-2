package com.kodstar.issuetracker.repo;

import com.kodstar.issuetracker.auth.User;
import com.kodstar.issuetracker.entity.Issue;


import com.kodstar.issuetracker.entity.Label;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface IssueRepository extends JpaRepository<Issue, Long> {
    List<Issue> findAll();
  

   // @Query(value = "select * from t_issue i where i.issue_title like %:keyword%", nativeQuery = true)
    List<Issue> findALlByTitleContaining(String keyword);

    Page<Issue> findAll(Pageable pageable);



   // @Query(value = "select * from t_issue i where  i.issue_description like %:keyword%", nativeQuery = true)
    List<Issue> findALlByDescriptionContaining(String keyword);

    List<Issue> findAllByLabelsContains(Label label);

    Page<Issue> findAllByOrderByCreateTime(Pageable pageable);

    Page<Issue> findAllByOrderByCreateTimeDesc(Pageable pageable);

    Page<Issue> findAllByOrderByUpdateTime(Pageable pageable);

    Page<Issue> findAllByOrderByUpdateTimeDesc(Pageable pageable);

    List<Issue> findAllByAssigneesContains(User user);





}
