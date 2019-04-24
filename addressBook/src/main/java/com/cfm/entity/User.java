package com.cfm.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * User info
 * @author linbrian
 *
 */
public class User implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String password;
	private String phone;
	private String address;
	private String email;
	private List<Contact> contactList;
	
	public User(String id, String password, String phone, String address, String email) {
		super();
		this.id = id;
		this.password = password;
		this.phone = phone;
		this.address = address;
		this.email = email;
		this.contactList = new ArrayList<Contact>();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Contact> getContactList() {
		return contactList;
	}

	public void setContactList(List<Contact> contactList) {
		this.contactList = contactList;
	}
	
	
}
