package com.web.mvc.entity.one2one;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.mvc.entity.JPAUtil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class Test_Person {

    static EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

    public static void main(String[] args) throws Exception {
        //add("老鐵");
        //get((long) 51);
        //query();
        //update(51L, "小胖");
        //delete(51L);
        queryKeyword("%老");
        // 1.%老:搜尋結尾是老; 2.%老%:搜尋只要有老; 3.老%:搜尋開頭是老
    }

    public static void add(String name) {
        Person p = new Person();
        p.setName(name);
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
        System.out.println("ADD OK!");
    }

    public static void get(Long id) throws Exception {
        Person p = em.find(Person.class, id);
        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(p);
        System.out.println(json);
    }

    public static void query() throws Exception {
        ObjectMapper om = new ObjectMapper();
        List<Person> list =
        em.createQuery("SELECT p FROM Person p", Person.class).getResultList();
        String json = om.writeValueAsString(list);
        System.out.println(json);
    }
    
    public static void query(String name) throws Exception {
        ObjectMapper om = new ObjectMapper();
        TypedQuery<Person> tq =
        em.createQuery("SELECT p FROM Person p WHERE p.name = :name", Person.class);
        tq.setParameter("name", name);
        List<Person> list = tq.getResultList();
        String json = om.writeValueAsString(list);
        System.out.println(json);
    }
    
    public static void queryKeyword(String keyword) throws Exception {
        ObjectMapper om = new ObjectMapper();
        TypedQuery<Person> tq =
        em.createQuery("SELECT p FROM Person p WHERE p.name like :keyword", Person.class);
        tq.setParameter("keyword", keyword);
        List<Person> list = tq.getResultList();
        String json = om.writeValueAsString(list);
        System.out.println(json);
    }
    
    public static void update(Long id, String name) throws Exception{
        Person p = em.find(Person.class, id);
        if(p != null){
            p.setName(name);
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
            System.out.println("Update OK!");
        }else System.out.println("No data!");
    }
    
    public static void delete(Long id) throws Exception{
        Person p = em.find(Person.class, id);
        if(p != null){
            em.getTransaction().begin();
            em.remove(p);
            em.getTransaction().commit();
            System.out.println("Delete OK!");
        }else System.out.println("No data!");
    }
}
