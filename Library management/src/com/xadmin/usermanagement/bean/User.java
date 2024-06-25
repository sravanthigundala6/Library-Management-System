package com.xadmin.usermanagement.bean;

import java.sql.Date;

/**
 * User.java
 * This is a model class represents a User entity
 * @author Sravanthi Gundala
 *
 */
public class User {
	protected int id;
	protected String name;
	protected String email;
	protected String country;
	protected String bookname;
	protected Date issuedDate;
	protected Date dueDate;
	protected int fine;
	
	
	

	public User() {
	}

	public User(String name, String email, String country,String bookname,Date issuedDate,Date dueDate,int fine) {
		super();
		this.name = name;
		this.email = email;
		this.country = country;
		this.bookname=bookname;
		this.issuedDate=issuedDate;
		this.dueDate=dueDate;
		this.fine=0;
		
	}

	public User(int id, String name, String email, String country,String bookname,Date issuedDate,Date dueDate,int fine) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.country = country;
		this.bookname=bookname;
		this.issuedDate=issuedDate;
		this.dueDate=dueDate;
		this.fine=fine;
	}

	public int getFine() {
		return fine;
	}

	public void setFine(int fine) {
		this.fine = fine;
	}

	public String getBookname() {
		return bookname;
	}

	public Date getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(Date issuedDate) {
		this.issuedDate = issuedDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
}
