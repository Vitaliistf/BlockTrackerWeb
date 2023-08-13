package org.vitaliistf.blocktracker.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@Entity(name = "coin")
public class Coin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "symbol")
    private String symbol;

    @Basic
    @Column(name = "amount")
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "portfolio_id", referencedColumnName = "id")
    private Portfolio portfolio;

    @OneToMany(mappedBy = "coin", cascade = CascadeType.REMOVE)
    private List<Transaction> transactions;

    @Basic
    @Column(name = "avg_buying_price")
    private BigDecimal avgBuyingPrice;
}
