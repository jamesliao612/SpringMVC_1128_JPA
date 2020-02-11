package com.web.portfolio.entity;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class Watch {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "Investor_id", referencedColumnName = "id")
    private Investor investor;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "watch_tstock",
            joinColumns = {
                @JoinColumn(name = "watch_id",
                        nullable = false, updatable = false)
            },
            inverseJoinColumns = {
                @JoinColumn(name = "tStock_id",
                        nullable = false, updatable = false)
            }
    )
    private Set<TStock> tStocks = new LinkedHashSet<TStock>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Investor getInvestor() {
        return investor;
    }

    public void setInvestor(Investor investor) {
        this.investor = investor;
    }

    public Set<TStock> gettStocks() {
        return tStocks;
    }

    public void settStocks(Set<TStock> tStocks) {
        this.tStocks = tStocks;
    }

    public Set<TStock> addtStock(TStock tStock) {
        tStocks.add(tStock);
        return tStocks;
    }

    public Set<TStock> removetStock(TStock tStock) {
        tStocks.remove(tStock);
        return tStocks;
    }

    public Watch() {
    }

    public Watch(String name, Investor investor) {
        this.name = name;
        this.investor = investor;
    }

}
