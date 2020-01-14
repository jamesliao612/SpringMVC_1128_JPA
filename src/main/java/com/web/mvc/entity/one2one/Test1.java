package com.web.mvc.entity.one2one;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.mvc.entity.JPAUtil;
import java.util.List;
import javax.persistence.EntityManager;

public class Test1 {

    static EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

    public static void main(String[] args) throws Exception {
        //add("老李");
        //get((long) 51);
        //query();
        //update(51L, "小胖");
        delete(51L);
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
