package org.vitaliistf.blocktracker.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity(name = "portfolio")
public class Portfolio {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Basic
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.REMOVE)
    private List<Coin> coins;
}
