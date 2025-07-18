# 🏨 Hotel Reservation System

A simple **Hotel Reservation System** implemented in Java using **JDBC** to perform CRUD operations on a MySQL database.

---

## 📌 **Project Description**

This project allows users to:

✅ Reserve a room  
✅ View all reservations  
✅ Get room number by reservation ID and guest name  
✅ Update reservation details  
✅ Delete a reservation

---

## ⚙️ **Technologies Used**

- Java 17+ (or your installed version)  
- MySQL 8.0  
- JDBC (MySQL Connector/J)  
- IntelliJ IDEA (recommended) or any IDE

---

## 🗃️ **Database Schema**

Run the following SQL script to create the required table:

```sql
CREATE DATABASE IF NOT EXISTS hotel_db;

USE hotel_db;

CREATE TABLE IF NOT EXISTS Reservations (
    reservation_id INT AUTO_INCREMENT PRIMARY KEY,
    guest_name VARCHAR(100) NOT NULL,
    room_number INT NOT NULL,
    contact_number VARCHAR(15) NOT NULL,
    reservation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
