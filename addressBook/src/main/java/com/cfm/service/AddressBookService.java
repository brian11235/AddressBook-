package com.cfm.service;

import com.cfm.entity.User;

public interface AddressBookService {
	
	/**
	 * put user info from db
	 * @param user
	 */
	void saveUserData(User user);
	/**
	 * get user info from db
	 * @return
	 */
	public User getUserData(String id);
	/**
	 * 
	 * @param id
	 */
	void updateUserData(String id, User user);
	/**
	 * 
	 * @param id
	 */
	void deleteUserData(String id);
}
