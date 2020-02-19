package com.jtrack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jtrack.model.LoginHist;
import com.jtrack.service.LoginHistService;

@RestController
public class LoginHistController {

	@Autowired
	LoginHistService loginHistService;

	@GetMapping(path="/loginHist")
	public List<LoginHist> getLoginHistList(){
		return loginHistService.getLoginHistList();
	}
	
}
