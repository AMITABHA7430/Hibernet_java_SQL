# Hibernate Learning Notes & Revision Guide

## Overview

This repository contains my practice and learning work for **Hibernate ORM** in Java.
The goal of this project is to understand how Java applications interact with relational databases using Hibernate instead of writing large amounts of raw JDBC code.

This README is written as a future revision guide so I can quickly revise Hibernate concepts before interviews, projects, Spring Framework, or exams.

---

# What is Hibernate?

Hibernate is an **ORM (Object Relational Mapping)** framework for Java.

ORM means:

* Java Objects ↔ Database Tables
* Object Fields ↔ Table Columns

Instead of manually writing SQL queries for every operation, Hibernate maps Java classes to database tables and performs database operations automatically.

---

# Why Hibernate?

Before Hibernate, developers mainly used JDBC.

Using JDBC:

* Need to write SQL manually
* Need to manage connections manually
* Need to convert ResultSet into objects manually
* Boilerplate code becomes very large

Hibernate solves these problems.

Advantages:

* Reduces boilerplate code
* Automatic table mapping
* Database independent
* Easier CRUD operations
* Better scalability
* Supports caching
* Integrates with Spring Framework

---

# Hibernate Architecture

```text
Java Application
       ↓
Hibernate API
       ↓
Configuration
       ↓
SessionFactory
       ↓
Session
       ↓
Database
```

---

# Important Hibernate Components

## 1. Configuration

Used to configure Hibernate settings.

Contains:

* Database URL
* Username
* Password
* Dialect
* Driver class
* Mapping information

Example:

```java
Configuration config = new Configuration();
config.configure();
```

Usually reads settings from:

```text
hibernate.cfg.xml
```

---

## 2. SessionFactory

* Heavyweight object
* Created once per application
* Responsible for creating sessions
* Thread-safe

Example:

```java
SessionFactory factory = config.buildSessionFactory();
```

---

## 3. Session

Main interface between Java application and database.

Used for:

* save()
* update()
* delete()
* get()
* createQuery()

Example:

```java
Session session = factory.openSession();
```

Think of Session as:

```text
Temporary connection between application and database
```

---

## 4. Transaction

Used to maintain database consistency.

Example:

```java
Transaction tx = session.beginTransaction();
```

Commit changes:

```java
tx.commit();
```

---

# Hibernate Project Structure

```text
Project
│
├── src/main/java
│   └── com.amitabha
│       ├── Main.java
│       └── student.java
│
├── src/main/resources
│   └── hibernate.cfg.xml
│
├── pom.xml
│
└── Database
```

---

# Maven Dependencies Used

## Hibernate Core

```xml
<dependency>
    <groupId>org.hibernate.orm</groupId>
    <artifactId>hibernate-core</artifactId>
    <version>6.x.x</version>
</dependency>
```

## MySQL Connector

```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>8.x.x</version>
</dependency>
```

---

# Entity Class

Hibernate maps a Java class to a database table.

Example:

```java
@Entity
public class student {

    @Id
    private int aid;
    private String aname;
    private String tech;
}
```

---

# Important Annotations

## @Entity

Marks class as Hibernate entity.

```java
@Entity
```

---

## @Id

Marks primary key.

```java
@Id
private int aid;
```

---

## @Table

Used when table name differs.

```java
@Table(name="student_data")
```

---

## @Column

Used for custom column names.

```java
@Column(name="student_name")
```

---

# Hibernate Configuration File

File:

```text
hibernate.cfg.xml
```

Basic structure:

```xml
<!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
        "http://hibernate.sourceforge.net/hibernate-configuration-3.0.dtd">

<hibernate-configuration>
    <session-factory>

        <property name="hibernate.connection.driver_class">
            com.mysql.cj.jdbc.Driver
        </property>

        <property name="hibernate.connection.url">
            jdbc:mysql://localhost:3306/test
        </property>

        <property name="hibernate.connection.username">
            root
        </property>

        <property name="hibernate.connection.password">
            password
        </property>

        <property name="hibernate.dialect">
            org.hibernate.dialect.MySQLDialect
        </property>

        <property name="hibernate.hbm2ddl.auto">
            update
        </property>

        <property name="hibernate.show_sql">
            true
        </property>

    </session-factory>
</hibernate-configuration>
```

---

# hbm2ddl.auto Values

## create

Creates new table every time.

---

## update

Updates existing table.

Most commonly used during development.

---

## create-drop

Creates table and deletes when application stops.

---

## validate

Checks schema only.

---

# Basic Save Operation

```java
Configuration config = new Configuration();
config.configure();
config.addAnnotatedClass(student.class);

SessionFactory factory = config.buildSessionFactory();
Session session = factory.openSession();

Transaction tx = session.beginTransaction();

student s1 = new student();
s1.setAid(101);
s1.setAname("Amitabha");
s1.setTech("Java");

session.persist(s1);

tx.commit();

session.close();
factory.close();
```

---

# CRUD Operations

## Create

```java
session.persist(obj);
```

---

## Read

```java
student s = session.get(student.class,101);
```

---

## Update

```java
session.merge(obj);
```

---

## Delete

```java
session.remove(obj);
```

---

# Difference Between JDBC and Hibernate

| JDBC                  | Hibernate                 |
| --------------------- | ------------------------- |
| SQL manually written  | Mostly automatic          |
| More boilerplate code | Less code                 |
| Manual mapping        | Automatic mapping         |
| Harder maintenance    | Easier maintenance        |
| Database dependent    | More database independent |

---

# Hibernate Lifecycle States

## 1. Transient State

Object created but not connected to Hibernate.

```java
student s = new student();
```

---

## 2. Persistent State

Object connected with session.

```java
session.persist(s);
```

---

## 3. Detached State

Session closed but object still exists.

```java
session.close();
```

---

# Common Methods

| Method             | Purpose                  |
| ------------------ | ------------------------ |
| persist()          | Save object              |
| get()              | Fetch data               |
| merge()            | Update data              |
| remove()           | Delete object            |
| beginTransaction() | Start transaction        |
| commit()           | Save changes permanently |

---

# Common Errors Faced During Learning

## 1. JDBC Driver Not Found

Cause:

* MySQL dependency missing

Solution:

* Add mysql connector dependency in pom.xml

---

## 2. Mapping Exception

Cause:

* Entity class not added

Solution:

```java
config.addAnnotatedClass(student.class);
```

---

## 3. Table Not Created

Cause:

* Wrong database URL
* Wrong credentials
* hbm2ddl.auto not configured

---

## 4. No Identifier Specified

Cause:

* Missing @Id annotation

---

# Connection Between Hibernate and Spring

Hibernate is very important before learning Spring Boot.

Why?

Because Spring uses:

* Spring Data JPA
* Hibernate internally
* ORM concepts
* Dependency Injection with repositories

If Hibernate concepts are clear:

* Spring Boot database handling becomes much easier
* JPA becomes easier
* Real backend development becomes easier

---

# Key Concepts to Remember for Revision

## Core Flow

```text
Configuration
   ↓
SessionFactory
   ↓
Session
   ↓
Transaction
   ↓
CRUD Operations
```

---

# Quick Revision Notes

## Hibernate

```text
Framework for ORM in Java
```

## SessionFactory

```text
Creates sessions
Heavyweight
One per application
```

## Session

```text
Performs database operations
```

## Transaction

```text
Ensures data consistency
```

## Entity

```text
Java class mapped to database table
```

---

# Future Topics to Learn

* HQL (Hibernate Query Language)
* Criteria API
* One-to-One Mapping
* One-to-Many Mapping
* Many-to-Many Mapping
* Lazy vs Eager Loading
* Caching
* JPA
* Spring Data JPA
* Hibernate with Spring Boot

---

# Final Understanding

Hibernate acts as a bridge between:

```text
Java Objects ↔ Relational Database
```

Instead of thinking in SQL tables all the time, developers can work mainly with Java objects.

This makes backend development:

* Faster
* Cleaner
* More maintainable
* More scalable

---

# Learning Progress

Topics completed today:

* Basic Hibernate setup
* Maven dependency management
* Hibernate configuration
* Entity class creation
* SessionFactory and Session usage
* Transactions
* CRUD basics
* Database connection
* GitHub upload

---

# Personal Revision Strategy

Before interviews or projects revise:

1. ORM concept
2. Hibernate architecture
3. SessionFactory vs Session
4. Entity annotations
5. CRUD methods
6. Transaction flow
7. hibernate.cfg.xml
8. Maven dependencies
9. Common exceptions
10. Hibernate → Spring Boot connection

---

# End Goal

Current path:

```text
Java → JDBC → Maven → Hibernate → Spring → Spring Boot → Backend Development
```

This repository is part of that backend development journey.
