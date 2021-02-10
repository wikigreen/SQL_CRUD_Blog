# CRUD_blog
Studying console CRUD application

I want to present you my studying console CRUD application. App represents database for blog. 

## Table of Contents
* [Problem Statemen](#problem-statemen)
* [Requirements](#requirements)
* [How to run](#how-to-run)

## Problem Statemen
Problem Statement: I must implement console CRUD application. It should have following entities: 

* User (id, firstName, lastName, List<Post> posts, Region region, Role role)
* Post (id, content, created, updated)
* Region (id, name)
* Role (enum ADMIN, MODERATOR, USER)

As a database should be used .txt files:

* users.txt
* posts.txt
* regions.txt

User from should be able to create, get, update and delete entities.

Layers:
* model - POJO classes
* repository - classes that implement access to .txt files
* controller - classes that implements processing of requests from user
* view - classes that handle console.

For example: User, UserRepository, UserController, UserView etc.


It is advisable to user basic interface for repository layer:
interface GenericRepository<T,ID>

class UserRepository implements GenericRepository<User, Long>

## Requirements
* IDE
* Java 14 or newer
* Git
* Maven
* MySQL Community Server 8.0.23

## How to run 
Database migration:
1. Login to MySQL as root

2. CREATE USER 'blogger'@'localhost' IDENTIFIED BY 'blogger';

3. CREATE DATABASE crud_blog;

4. GRANT ALL PRIVILEGES ON crud_blog.* TO 'blogger'@'localhost';

Steps to run this application using your IDE:

1. Use command 'git clone https://github.com/wikigreen/SQL_CRUD_Blog'
2. Go to Main.java in src/main/java/com/vladimir/crudblog/Main.java
3. Right click the file and Run as Java application
4. Follow the instructions from console
