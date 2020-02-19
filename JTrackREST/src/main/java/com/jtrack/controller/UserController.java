package com.jtrack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jtrack.exception.InvalidDataException;
import com.jtrack.model.User;
import com.jtrack.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;

	@GetMapping(path="/user")
	public List<User> getUserList(){
		return userService.getUserList();
	}
	
	@GetMapping(path="/user/{id}")
	public ResponseEntity<Object> getUser(@PathVariable String id){
		User user = userService.getUser(id);
		
		if(user == null) {
			return ResponseEntity.badRequest().body("User does not exist");
		}
		
		return ResponseEntity.ok(user);
	}

	@PostMapping("/user")
	public ResponseEntity<Object> addUser(@RequestBody User user) {
		
		try {
			 return ResponseEntity.ok(userService.addUser(user));
		} catch (InvalidDataException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/user")
	public ResponseEntity<Object> updateUser(@RequestBody User user) {
		
		try {
			return ResponseEntity.ok(userService.updateUser(user));
		} catch (InvalidDataException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("/user/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable String id) {
		
		try {
			userService.deleteUser(id);
			return ResponseEntity.ok().build();
		} catch (InvalidDataException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
