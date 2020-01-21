package com.web.mvc.entity.one2many;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.mvc.entity.JPAUtil;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class CarSystem {

    static EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        menu();
    }

    public static void menu() throws Exception {
        System.out.println("---------------");
        System.out.println("1. 新增 Driver");
        System.out.println("2. 新增 Car");
        System.out.println("3. 查詢 Drivers");
        System.out.println("4. 查詢 Cars");
        System.out.println("5. 單查 Driver");
        System.out.println("6. 單查 Car");
        System.out.println("7. 買車 Buy");
        System.out.println("8. 賣車 Sell");
        System.out.println("9. 資產 Asset");
        System.out.println("0. 離開 Exit");
        System.out.println("---------------");
        System.out.println("請輸入選項:");
        switch (sc.nextInt()) {
            case 1:
                System.out.println("請輸入Driver Name:");
                addDriver(sc.next());
                break;
            case 2:
                System.out.println("請輸入Car Name:");
                String newCar = sc.next();
                System.out.println("請輸入Cost:");
                Integer newCost = sc.nextInt();
                addCar(newCar, newCost);
                break;
            case 3:
                queryDrivers();
                break;
            case 4:
                queryCars();
                break;
            case 5:
                System.out.println("請輸入欲查詢Driver Name:");
                getDriver(sc.next());
                break;
            case 6:
                System.out.println("請輸入欲查詢Car Name");
                getCar(sc.next());
                break;
            case 7:
                System.out.println("請輸入買車人:");
                String name1 = sc.next();
                System.out.println("請輸入車名:");
                String name2 = sc.next();
                buy(name1, name2);
                break;
            case 8:
                System.out.println("請輸入賣方:");
                String seller = sc.next();
                System.out.println("請輸入欲售車輛:");
                String car = sc.next();
                System.out.println("請輸入買方:");
                String buyer = sc.next();
                sell(seller, car, buyer);
                break;
            case 9:
                asset();
                break;
            case 0:
                return;
        }
        menu();
    }

    public static void addDriver(String name) {
        Driver driver = new Driver();
        driver.setName(name);
        persist(driver);
        System.out.println("Driver新增成功");
    }

    public static void addCar(String name,Integer cost) {        
        Price price = new Price();
        price.setCost(cost);
        Car car = new Car();
        car.setName(name);
        car.setPrice(price);
        persist(car);
        System.out.println("Car新增成功");
    }

    public static void queryDrivers() throws Exception {
        em.clear();
        print(em.createQuery("SELECT d FROM Driver d", Driver.class).getResultList());
    }

    public static void queryCars() throws Exception {
        em.clear();
        print(em.createQuery("SELECT c FROM Car c", Car.class).getResultList());
    }

    public static void getDriver(String name) throws Exception {
        em.clear();
        TypedQuery<Driver> tq
                = em.createQuery("SELECT d FROM Driver d WHERE d.name = :name", Driver.class);
        tq.setParameter("name", name);
        int size = tq.getResultList().size();
        if (size == 0) {
            System.out.println("查無此人!");
        } else {
            print(tq.getResultList());
        }
    }

    public static void getCar(String name) throws Exception {
        em.clear();
        TypedQuery<Car> tq
                = em.createQuery("SELECT c FROM Car c WHERE c.name = :name", Car.class);
        tq.setParameter("name", name);
        int size = tq.getResultList().size();
        if (size == 0) {
            System.out.println("查無此車!");
        } else {
            print(tq.getResultList());
        }
    }

    public static void buy(String name1, String name2) {
        em.clear();
        TypedQuery<Driver> tq1
                = em.createQuery("SELECT d FROM Driver d WHERE d.name = :name", Driver.class);
        tq1.setParameter("name", name1);
        TypedQuery<Car> tq2
                = em.createQuery("SELECT c FROM Car c WHERE c.name = :name", Car.class);
        tq2.setParameter("name", name2);
        if (tq1.getResultList().isEmpty()) {
            System.out.println("查無此人");
        } else {
            if (tq2.getResultList().isEmpty()) {
                System.out.println("查無此車");
            } else {
                Driver driver = tq1.getSingleResult();
                Car car = tq2.getSingleResult();
                car.setDriver(driver);
                persist(car);
                System.out.println("購買成功!");
            }
        }
    }

    public static void sell(String sellerName, String carName, String buyerName) {
        TypedQuery<Driver> seller
                = em.createQuery("SELECT d FROM Driver d WHERE d.name = :name", Driver.class);
        seller.setParameter("name", sellerName);
        TypedQuery<Car> selledCar
                = em.createQuery("SELECT c FROM Car c WHERE c.name = :name", Car.class);
        selledCar.setParameter("name", carName);
        TypedQuery<Driver> buyer
                = em.createQuery("SELECT d FROM Driver d WHERE d.name = :name", Driver.class);
        buyer.setParameter("name", buyerName);
        if (seller.getResultList().isEmpty()) {
            System.out.println("查無此賣方");
        } else {
            if (selledCar.getResultList().isEmpty()) {
                System.out.println("查無此欲售車輛");
            } else {
                if (buyer.getResultList().isEmpty()) {
                    System.out.println("查無此買方");
                } else {
                    Car car_selled = selledCar.getSingleResult();
                    Driver driver_buyer = buyer.getSingleResult();
                    car_selled.setDriver(driver_buyer);
                    persist(car_selled);
                    System.out.println("過戶成功!");
                }
            }
        }
    }
    
    public static void asset(){
        List<Driver> drivers = em.createQuery("SELECT d FROM Driver d", Driver.class).getResultList();
        drivers.stream().forEach(d -> {
            int sum = d.getCars().stream().mapToInt(c -> c.getPrice().getCost()).sum();
            System.out.printf("%s 擁有車輛合計總資產為 %,d\n", d.getName(),sum);
        });
    }

    public static void persist(Object object) {
        em.getTransaction().begin();
        em.persist(object);
        em.getTransaction().commit();
    }

    public static void print(Object object) throws Exception {
        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(object);
        System.out.println(json);
    }
}
