package org.amitabha;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {

    public static void main(String[] args) {

        student s1 = new student();

        s1.setAid(101);
        s1.setAname("Amitabha");
        s1.setTech("Java");

        Configuration config = new Configuration();
        config.addAnnotatedClass(student);
        config.configure();

        SessionFactory sf = config.buildSessionFactory();

        Session session = sf.openSession();

        session.persist(s1);

    }
}