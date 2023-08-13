package org.vitaliistf.blocktracker.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity(name = "confirmation_token")
public class ConfirmationToken {

    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "token")
    private String token;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Basic
    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

    public ConfirmationToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.expiryDate = calculateExpiryDate();
    }

    private LocalDateTime calculateExpiryDate() {
        return LocalDateTime.now().plusMinutes(ConfirmationToken.EXPIRATION);
    }

    public boolean isExpired() {
        return expiryDate.isBefore(LocalDateTime.now());
    }
}