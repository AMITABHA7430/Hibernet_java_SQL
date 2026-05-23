package com.amitabha;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Main {

    public static void main(String[] args) {

        student s1 = new student();

        s1.setAid(104);
        s1.setAname("Ayan");
        s1.setTech("c#");

        Configuration config = new Configuration();
        config.addAnnotatedClass(com.amitabha.student.class);
        config.configure("hibernate.cfg.xml");

        SessionFactory sf = config.buildSessionFactory();

        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();

//        session.persist(s1);//its for adding data
//        student s2=session.find(student.class,101);//to fetch the data
        session.merge(s1);//use to update a querry and can also create one if not exist
//        System.out.println(s2);
        transaction.commit();
        session.close();
        sf.close();

    }
}