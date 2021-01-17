package com.kodstar.issuetracker.entity;

import com.kodstar.issuetracker.auth.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_issue_history")
public class IssueHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @ManyToOne
    private Issue issue;

    @CreatedBy
    private String user;

    @CreatedDate
    private LocalDateTime createTime;

    @OneToOne
    private Comment comment;

    @ManyToOne
    private State state;

    @ManyToMany()
    @JoinTable(name = "t_issue_history_label", joinColumns = @JoinColumn(name = "issue_history_id"), inverseJoinColumns = @JoinColumn(name = "label_id"))
    private Set<Label> labels;

    @Enumerated(EnumType.STRING)
    private HistoryType historyType;

    @OneToOne
    private User assignee;



}
