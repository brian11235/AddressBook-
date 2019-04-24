package com.cfm.dao;

import com.cfm.entity.User;
/**
 * Interface for database operation
 * @author linbrian
 *
 */
public interface AddressBookDao {
	
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
	void updateUserData(String id,User user);
	/**
	 * 
	 * @param id
	 */
	void deleteUserData(String id);
	
}
