package com.jtrack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jtrack.exception.InvalidDataException;
import com.jtrack.model.User;
import com.jtrack.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserService userService;

	@GetMapping(path="")
	public List<User> getUserList(){
		return userService.getUserList();
	}
	
	@GetMapping("/{id}")
	public User getUser(@PathVariable String id){
		return userService.getUser(id);
	}

	@PostMapping("")
	public User addUser(@RequestBody User user) throws InvalidDataException {
		return userService.addUser(user);
	}
	
	@PutMapping("/{id}")
	public User updateUser(@PathVariable String id, @RequestBody User user) throws InvalidDataException {
		user.setUserId(id);
		return userService.updateUser(user);
	}
	
	@DeleteMapping("/{id}")
	public String deleteUser(@PathVariable String id) throws InvalidDataException {
		userService.deleteUser(id);
		return "";
	}
}
