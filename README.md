# 💧 Water Supply Management System

A Java Swing–based desktop application designed to manage water supply records efficiently using a MySQL database.

---

## 📌 Project Overview
The Water Supply Management System helps users maintain water supply details such as customer name, location, litres supplied, quantity, and price.  
It provides a simple graphical interface for adding, updating, and viewing records stored in a MySQL database.

---

## 🚀 Features
- Add new water supply records  
- Update existing records  
- View all records in a table format  
- Automatic price calculation  
- User-friendly Java Swing GUI  
- Data persistence using MySQL  

---

## 🛠️ Technologies Used
- Java (JDK 21)
- Java Swing (GUI)
- JDBC
- MySQL
- Eclipse IDE
- Git & GitHub

---

## 📂 Project Structure
Water-Supply-Management-System ├── src │   └── com.jayanth.watersupply │       ├── Main.java │       └── Water.java ├── README.md └── .gitignore

---

## 🗄️ Database Details
**Database Name:** `intern`  
**Table Name:** `water`

```sql
CREATE TABLE water (
    NAME VARCHAR(50),
    LOCATION VARCHAR(50),
    LITRES INT,
    QUANTITY INT,
    PRICE INT
);

