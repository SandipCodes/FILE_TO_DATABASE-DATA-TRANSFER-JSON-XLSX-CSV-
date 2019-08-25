package com.app.service;

import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.app.model.User;

public interface UserService {

	public void saveUser(User u);
	public void updateUser(User u);
	public void deleteUser(Integer id);
	public User getUserById(Integer id);
	public List<User> getAllUsers();
	public boolean saveDataFromFile(CommonsMultipartFile file);

}
