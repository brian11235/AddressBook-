package com.cfm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cfm.dao.AddressBookDao;
import com.cfm.entity.User;

@Service
public class AddressBookServiceImpl implements AddressBookService{
	
	@Autowired
	AddressBookDao addressBookDao;
	public void saveUserData(User user) {
		addressBookDao.saveUserData(user);
	}

	public User getUserData(String id) {
		return addressBookDao.getUserData(id);
	}

	public void updateUserData(String id,User user) {
		addressBookDao.updateUserData(id, user);
	}

	public void deleteUserData(String id) {
		
		
	}
	
}
