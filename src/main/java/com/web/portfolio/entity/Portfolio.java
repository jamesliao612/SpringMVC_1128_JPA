package com.web.portfolio.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Integer amount;

    @Column
    private Double cost;

    @Column
    private Date date = new Date();

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "investor_id", referencedColumnName = "id")
    @JsonIgnoreProperties(value = "portfolio")
    private Investor investor;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tStock_id",
            foreignKey = @ForeignKey(name = "tStock_fk", value = ConstraintMode.CONSTRAINT))
    private TStock tStock;

    public Portfolio(Double cost, Integer amount, Investor investor, TStock tStock) {
        this.amount = amount;
        this.cost = cost;
        this.investor = investor;
        this.tStock = tStock;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Investor getInvestor() {
        return investor;
    }

    public void setInvestor(Investor investor) {
        this.investor = investor;
    }

    public TStock getTstock() {
        return tStock;
    }

    public void setTstock(TStock tstock) {
        this.tStock = tstock;
    }

}
