package com.web.portfolio.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    private Date date;
    
    @ManyToOne
    private Investor investor;
    
    @OneToOne
    private Tstock tstock;
}
