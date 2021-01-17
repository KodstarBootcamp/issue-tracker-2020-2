package com.kodstar.issuetracker.dto;

import com.kodstar.issuetracker.auth.User;
import com.kodstar.issuetracker.entity.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class IssueHistoryDTO {
    private Long id;
    private String user;
    private LocalDateTime createTime;
    private Comment comment;
    private State state;
    private Set<Label> labels;
    private HistoryType historyType;
    private User assignee;
}
