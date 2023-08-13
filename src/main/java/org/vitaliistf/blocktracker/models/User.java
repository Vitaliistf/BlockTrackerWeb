package org.vitaliistf.blocktracker.models;

import org.vitaliistf.blocktracker.util.validation.ValidEmail;
import org.vitaliistf.blocktracker.util.validation.ValidPassword;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@Entity(name = "usr")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Email should not be empty.")
    @ValidEmail
    @Basic
    @Column(name = "email")
    private String email;

    @NotEmpty(message = "Password should not be empty.")
    @ValidPassword
    @Basic
    @Column(name = "password")
    private String password;

    @Basic
    @Column(name = "enabled")
    private boolean enabled;

    public User(String email) {
        this.email = email;
        this.password = "OAuth2user";
        this.enabled = true;
    }
}
