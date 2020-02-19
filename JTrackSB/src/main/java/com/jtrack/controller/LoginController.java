package com.jtrack.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.jtrack.model.LoginHist;
import com.jtrack.service.LoginHistService;
import com.jtrack.service.UserService;

@Controller
public class LoginController {
	
	Logger logger = LogManager.getLogger(LoginController.class);
	
	@Autowired
	LoginHistService loginHistService;
	
	@Resource
	private UserService userService;

	@GetMapping(path="/")
	public String home() {
		 return "redirect:login";
	}
	
	@GetMapping("/login")
	public String login(Model model, boolean error, boolean logout) {
		
		if(logout) {
			logger.info("/logout");
		}
		
		logger.info("/login");
		
        return "login";
    }
	
	@GetMapping("/loginSuccess")
    public String successLogin(Model model, HttpServletRequest req) {
		logger.info("/loginSuccess");
		
		try {
			LoginHist loginHist = new LoginHist();
			loginHist.setUserId(userService.getCurrentUserId());
			loginHist.setIpAddr(getClientIpAddr(req));
			loginHistService.addLoginHist(loginHist);
		}catch(Exception e) {
			
		}

        return "redirect:job";
    }
	
	private String getClientIpAddr(HttpServletRequest request) {

        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("x-forwarded-for");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }

        return remoteAddr;
    }
}
