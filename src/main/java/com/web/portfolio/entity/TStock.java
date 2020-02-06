package com.web.portfolio.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class TStock {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column
    private String symbol;

    // 以下是報價資料
    @Column
    private BigDecimal preClosed;

    @Column
    private BigDecimal price;

    @Column
    private BigDecimal change;

    @Column
    private BigDecimal changeInPercent;

    @Column
    private Long volumn;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "watch_id", referencedColumnName = "id")
    @JsonIgnoreProperties(value = "tStocks")
    private Classify classify;

    @ManyToMany(mappedBy = "tStocks")
    @JsonIgnoreProperties(value = "tStocks")
    private Set<Watch> watchs;

    public TStock(String symbol, String name, Classify classify) {
        this.name = name;
        this.symbol = symbol;
        this.classify = classify;
    }

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

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getPreClosed() {
        return preClosed;
    }

    public void setPreClosed(BigDecimal preClosed) {
        this.preClosed = preClosed;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getChange() {
        return change;
    }

    public void setChange(BigDecimal change) {
        this.change = change;
    }

    public BigDecimal getChangeInPercent() {
        return changeInPercent;
    }

    public void setChangeInPercent(BigDecimal changeInPercent) {
        this.changeInPercent = changeInPercent;
    }

    public Long getVolumn() {
        return volumn;
    }

    public void setVolumn(Long volumn) {
        this.volumn = volumn;
    }

    public Classify getClassify() {
        return classify;
    }

    public void setClassify(Classify classify) {
        this.classify = classify;
    }

    public Set<Watch> getWatchs() {
        return watchs;
    }

    public void setWatchs(Set<Watch> watchs) {
        this.watchs = watchs;
    }

}
