package com.web.mvc.entity.many2many;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.mvc.entity.JPAUtil;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class FundManager {

    static EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        menu();
    }

    public static void menu() throws Exception {
        System.out.println("---------------");
        System.out.println("1. 新增股票");
        System.out.println("2. 新增基金");
        System.out.println("3. 加入基金成分股");
        System.out.println("4. 查詢股票");
        System.out.println("5. 查詢基金");
        System.out.println("0. 離開");
        System.out.println("---------------");
        System.out.println("請輸入選項：");
        switch (sc.nextInt()) {
            case 1:
                System.out.println("請輸入股票代碼");
                addStock(sc.next());
                break;
            case 2:
                System.out.println("請輸入基金名稱");
                addFund(sc.next());
                break;
            case 3:
                System.out.println("請輸入基金名稱");
                String name = sc.next();
                System.out.println("請輸入股票代碼");
                String symbol = sc.next();
                addStocks(name, symbol);
                break;
            case 4:
                queryStock();
                break;
            case 5:
                queryFund();
                break;
            case 0:
                return;
        }
        menu();
    }

    private static void addStock(String symbol) {
        Stock stock = new Stock();
        stock.setSymbol(symbol);
        persist(stock);
        System.out.println("股票新增成功");
    }

    private static void addFund(String name) {
        Fund fund = new Fund();
        fund.setName(name);
        persist(fund);
        System.out.println("基金新增成功");
    }

    private static void addStocks(String name, String symbol) {
        TypedQuery<Fund> tq_fund
                = em.createQuery("SELECT f FROM Fund f WHERE f.name = :name", Fund.class);
        tq_fund.setParameter("name", name);
        TypedQuery<Stock> tq_stock
                = em.createQuery("SELECT s FROM Stock s WHERE s.symbol = :symbol", Stock.class);
        tq_stock.setParameter("symbol", symbol);
        if (tq_fund.getResultList().isEmpty()) {
            System.out.println("查無此基金");
        } else {
            if (tq_stock.getResultList().isEmpty()) {
                System.out.println("查無此股票");
            } else {
                Fund fund = tq_fund.getSingleResult();
                Stock stock = tq_stock.getSingleResult();
                fund.getStocks().add(stock);
                persist(fund);
                System.out.println("加入成功");
            }
        }

    }

    private static void queryStock() throws Exception {
        em.clear();
        //print(em.createQuery("SELECT s FROM Stock s", Stock.class).getResultList());
        
        prettyPrintingStock(em.createQuery("SELECT s FROM Stock s", Stock.class).getResultList());
    }

    private static void queryFund() throws Exception {
        em.clear();
        //print(em.createQuery("SELECT f FROM Fund f", Fund.class).getResultList());
        
        prettyPrintingFund(em.createQuery("SELECT f FROM Fund f", Fund.class).getResultList());
    }

    //老師的Query Fund呈現方式
    private static void prettyPrintingFund(List<Fund> funds) {
        String leftAlignFormat = "| %-4d | %-4s | %-20s |%n";
        System.out.format("+------+------+----------------------+%n");
        System.out.format("| ID   | Name | Stocks               |%n");
        System.out.format("+------+------+----------------------+%n");
        for (Fund f : funds) {
            String stocksString = f.getStocks().stream().map(s -> s.getSymbol()).collect(Collectors.joining(", "));
            System.out.format(leftAlignFormat, f.getId(), f.getName(), stocksString);
        }
        System.out.format("+------+------+----------------------+%n");
        System.out.println("按任意鍵繼續...");
        new Scanner(System.in).nextLine();
    }

    //老師的Query Stock呈現
    private static void prettyPrintingStock(List<Stock> stocks) {
        String leftAlignFormat = "| %-4d | %-8s | %-20s |%n";
        System.out.format("+------+----------+----------------------+%n");
        System.out.format("| ID   | Name     | Funds                |%n");
        System.out.format("+------+----------+----------------------+%n");
        for (Stock s : stocks) {
            String fundsString = s.getFunds().stream().map(f -> f.getName()).collect(Collectors.joining(", "));
            System.out.format(leftAlignFormat, s.getId(), s.getSymbol(), fundsString);
        }
        System.out.format("+------+----------+----------------------+%n");
        System.out.println("按任意鍵繼續...");
        new Scanner(System.in).nextLine();
    }

    private static void persist(Object object) {
        em.getTransaction().begin();
        em.persist(object);
        em.getTransaction().commit();
    }

    private static void print(Object object) throws Exception {
        ObjectMapper om = new ObjectMapper();
        System.out.println(om.writeValueAsString(object));
    }
}
