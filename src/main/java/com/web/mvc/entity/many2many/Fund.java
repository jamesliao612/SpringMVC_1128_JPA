package com.web.mvc.entity.many2many;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import javax.persistence.Table;

@Entity
@Table(name = "Fund")
public class Fund {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @Column(name = "name",nullable = false)
    private String name;
    
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "Fund_Stock",
            joinColumns = @JoinColumn(name = "Fund_id"),
            inverseJoinColumns = @JoinColumn(name = "Stock_id")
    )
    @JsonIgnoreProperties(value = "funds")
    private Set<Stock> stocks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(Set<Stock> stocks) {
        this.stocks = stocks;
    }
    
    
}
