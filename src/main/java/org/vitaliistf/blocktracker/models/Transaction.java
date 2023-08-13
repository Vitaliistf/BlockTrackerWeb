package org.vitaliistf.blocktracker.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity(name = "transaction")
public class Transaction {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "coin_id")
    private Coin coin;

    @Basic
    @Column(name = "amount")
    private BigDecimal amount;

    @Basic
    @Column(name = "price")
    private BigDecimal price;

    @Basic
    @Column(name = "is_buy")
    private boolean isBuy;

    @Basic
    @Column(name = "date")
    private LocalDateTime date;
}
