package com.web.portfolio.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class Investor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column
    private String email;
    
    @Column
    private String username;
    
    @Column
    private String password;
    
    @OneToMany
    private List<Watch> watchs;
    
    @OneToMany
    private List<Portfolio> portfolios;
}
