package com.web.portfolio.controller;

import com.web.portfolio.entity.TStock;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

@RestController
@RequestMapping("/portfolio/price")
public class PriceController {
    
    @PersistenceContext
    protected EntityManager em;
    
    @GetMapping(value = {"/refresh"})
    @Transactional
    public List<TStock> refresh() {
        Query query = em.createQuery("select t from TStock t");
        List<TStock> list = query.getResultList();
        for (TStock tStock : list) {
            // 取得報價資訊
            try {
                Stock stock = YahooFinance.get(tStock.getSymbol());
                tStock.setChange(stock.getQuote().getChange());
                tStock.setChangeInPercent(stock.getQuote().getChangeInPercent());
                tStock.setPreClosed(stock.getQuote().getPreviousClose());
                tStock.setPrice(stock.getQuote().getPrice());
                tStock.setTransactionDate(stock.getQuote().getLastTradeTime().getTime());
                tStock.setVolumn(stock.getQuote().getVolume());
                // 更新報價
                em.persist(tStock);
                em.flush();
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
        return list;
    }
}