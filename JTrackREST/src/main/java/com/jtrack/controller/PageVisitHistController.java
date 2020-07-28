package com.jtrack.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jtrack.model.PageVisitHist;
import com.jtrack.service.PageVisitHistService;

@RestController
@RequestMapping("/pageVisitHist")
public class PageVisitHistController {

	@Autowired
	PageVisitHistService pageVisitHistService;

	@GetMapping("")
	public List<PageVisitHist> getPageVisitHistList(){
		return pageVisitHistService.getPageVisitHistList();
	}
	
	@GetMapping("/{pageId}")
	public List<PageVisitHist> getPageVisitHistList(@PathVariable String pageId){
		return pageVisitHistService.getPageVisitHistList(pageId);
	}
	
	@GetMapping("/count")
	public long getPageVisitCount(){
		return pageVisitHistService.getPageVisitCount();
	}
	
	@GetMapping("/{pageId}/count")
	public long getPageVisitCount(@PathVariable String pageId){
		return pageVisitHistService.getPageVisitCount(pageId);
	}

	@PostMapping("")
	public PageVisitHist addPageVisitHist(@RequestBody PageVisitHist pageVisitHist, HttpServletRequest req) {
		pageVisitHist.setIpAddr(getClientIpAddr(req));
		return pageVisitHistService.addPageVisitHist(pageVisitHist);
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
