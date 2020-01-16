package com.web.mvc.entity.one2one;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.mvc.entity.JPAUtil;
import javax.persistence.EntityManager;

public class Test_HusbandWife {

    static EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

    public static void main(String[] args) throws Exception {
        //add("John", "Mary");
        //add("Tom", "Jane");
        //queryHusband();
        //queryWife();
        //get(Husband.class, 501L);
        //get(Wife.class, 651L);
        //update(501L, "Jojo", null);
        //delete(Husband.class, 501L);
    }

    public static void add(String name1, String name2) {
        Husband husband = new Husband();
        husband.setName(name1);

        Wife wife = new Wife();
        wife.setName(name2);

        husband.setWife(wife);

        persist(husband);
        System.out.println("ADD OK!");
    }

    public static void queryHusband() throws Exception {
        print(em.createQuery("SELECT h FROM Husband h", Husband.class).getResultList());
    }

    public static void queryWife() throws Exception {
        print(em.createQuery("SELECT w FROM Wife w", Wife.class).getResultList());
    }

    public static void get(Class clas, Long id) throws Exception {
        print(em.find(clas, id));
    }

    public static void update(Long id, String name1, String name2) {
        Husband husband = em.find(Husband.class, id);
        if (husband != null) {
            if (name1 != null) {
                husband.setName(name1);
            }
            if (name2 != null) {
                husband.getWife().setName(name2);
            }
            persist(husband);
            System.out.println("UPDATE OK!");
        } else {
            System.out.println("HusbandID不存在");
        }
    }

    public static void delete(Class clas, Long id) {
        Object object = em.find(clas, id);
        if (object == null) {
            System.out.println("資料不存在");
        }else{
            em.getTransaction().begin();
            em.remove(object);
            em.getTransaction().commit();
            System.out.println("DELETE OK!");
        }
    }

    public static void print(Object object) throws Exception {
        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(object);
        System.out.println(json);
    }

    public static void persist(Object object) {
        em.getTransaction().begin();
        em.persist(object);
        em.getTransaction().commit();
    }
}
