package com.jtrack.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jtrack.model.LoginHist;
import com.jtrack.service.LoginHistService;

@RestController
@RequestMapping("/loginHist")
public class LoginHistController {

	@Autowired
	LoginHistService loginHistService;

	@GetMapping("")
	public List<LoginHist> getLoginHistList(){
		return loginHistService.getLoginHistList();
	}

	@PostMapping("")
	public LoginHist addLoginHist(@RequestBody LoginHist loginHist, HttpServletRequest req) {
		loginHist.setIpAddr(getClientIpAddr(req));
		return loginHistService.addLoginHist(loginHist);
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
