package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.app.model.User;
import com.app.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService service;

	// 1. View All data of User

	@GetMapping("/all")
	public String ViewAll(ModelMap map) {
		// call getAllUoms() of service class
		List<User> obs = service.getAllUsers();
        
		// keep data into ModelMap
		map.addAttribute("list", obs);
	
		return "UserData";
   	}// ViewAll()

	// 2. on click upload
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String uploadDoc(@RequestParam("fileObj") CommonsMultipartFile file1, ModelMap map) {

		if (file1 != null) {

			boolean flag = service.saveDataFromFile(file1);

			if (flag) {
				map.addAttribute("result", "file uploaded and its Data saved to DB ");
			} else {
				map.addAttribute("result", "file not uploaded and its Data not saved to DB ");
			}
		} // if
		else {
			map.addAttribute("message","Plz Select right file..");
		}
			// call getAllUoms() of service class
		List<User> obs = service.getAllUsers();

		
		// keep data into ModelMap
		map.addAttribute("list", obs);
		return "UserData";
	}// method
}
