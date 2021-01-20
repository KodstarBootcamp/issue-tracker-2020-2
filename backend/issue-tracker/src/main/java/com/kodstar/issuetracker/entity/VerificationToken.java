package com.kodstar.issuetracker.entity;

import com.kodstar.issuetracker.auth.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@RequiredArgsConstructor
public class VerificationToken {
    public static final int EXPIRATION =  60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    private Date expiryDate;

    public VerificationToken(String token, User user) {
        this.token =token;
        this.user =user;
    }


    public void setExpiryDate(int minutes) {
        Calendar now = Calendar.getInstance();
         now.add(Calendar.MINUTE, minutes);
        this.expiryDate = now.getTime();
    }

    public boolean isExpired() {
        return new Date().after(this.expiryDate);
    }
}


