package com.web.portfolio;

import com.web.mvc.entity.JPAUtil;
import com.web.portfolio.entity.Classify;
import com.web.portfolio.entity.Investor;
import com.web.portfolio.entity.Portfolio;
import com.web.portfolio.entity.Qweqweqweqwe;
import com.web.portfolio.entity.Watch;
import javax.persistence.EntityManager;

public class Build {

    static EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

    public static void main(String[] args) throws Exception {
        init();
    }

    public static void init() throws Exception {
        Investor investor1 = new Investor("admin", "1234", "admin@java.com", 10000000);
        Investor investor2 = new Investor("john", "1111", "john@java.com", 5000000);

        Classify classify1 = new Classify("股票", true);
        Classify classify2 = new Classify("匯率", true);
        Classify classify3 = new Classify("指數", false);

        Qweqweqweqwe ts1 = new Qweqweqweqwe("2330.TW", "台積電", classify1);
        Qweqweqweqwe ts2 = new Qweqweqweqwe("2317.TW", "鴻海", classify1);
        Qweqweqweqwe ts3 = new Qweqweqweqwe("1101.TW", "台泥", classify1);

        Qweqweqweqwe ts4 = new Qweqweqweqwe("USDTWD=x", "美金台幣", classify2);
        Qweqweqweqwe ts5 = new Qweqweqweqwe("JPYTWD=x", "日幣台幣", classify2);
        Qweqweqweqwe ts6 = new Qweqweqweqwe("CNYTWD=x", "人民幣台幣", classify2);

        Qweqweqweqwe ts7 = new Qweqweqweqwe("^TWII", "台灣加權", classify3);
        Qweqweqweqwe ts8 = new Qweqweqweqwe("^IXIC", "納斯達克", classify3);
        Qweqweqweqwe ts9 = new Qweqweqweqwe("^DJI", "道瓊工業", classify3);

        Portfolio portfolio1 = new Portfolio(60.5, 2000, investor1, ts1);
        Portfolio portfolio2 = new Portfolio(35.5, 5000, investor1, ts2);

        Watch watch = new Watch();
        watch.addtStock(ts1);
        watch.addtStock(ts2);
        watch.addtStock(ts4);
        watch.addtStock(ts6);
        watch.addtStock(ts7);
        watch.addtStock(ts9);
        watch.setInvestor(investor1);
        watch.setName("我的觀察股");

        em.getTransaction().begin();
        em.persist(ts1);
        em.persist(ts2);
        em.persist(ts3);
        em.persist(ts4);
        em.persist(ts5);
        em.persist(ts6);
        em.persist(ts7);
        em.persist(ts8);
        em.persist(ts9);
        em.persist(investor1);
        em.persist(investor2);
        em.persist(portfolio1);
        em.persist(portfolio2);
        em.persist(watch);
        em.getTransaction().commit();

        System.out.println("OK");
    }
}
