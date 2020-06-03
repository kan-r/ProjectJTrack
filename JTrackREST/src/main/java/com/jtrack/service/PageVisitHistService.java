package com.jtrack.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jtrack.dao.PageVisitHistDao;
import com.jtrack.model.PageVisitHist;

@Service
@Transactional
public class PageVisitHistService {
	
	Logger logger = LogManager.getLogger(PageVisitHistService.class);
	
	@Autowired
	private PageVisitHistDao pageVisitHistDao;

	public List<PageVisitHist> getPageVisitHistList(){
		logger.info("getPageVisitHistList()");
		return pageVisitHistDao.findAll(Sort.by("dateCrt"));
	}
	
	public List<PageVisitHist> getPageVisitHistList(String pageId){
		logger.info("getPageVisitHistList({})", pageId);
		
		PageVisitHist pageVisitHist = new PageVisitHist();
		pageVisitHist.setPageId(pageId);
		Example<PageVisitHist> ex = Example.of(pageVisitHist);
		
		return pageVisitHistDao.findAll(ex, Sort.by("dateCrt"));
	}
	
	public long getPageVisitCount(){
		logger.info("getNumberOfPageVisits()");
		return pageVisitHistDao.count();
	}
	
	public long getPageVisitCount(String pageId){
		logger.info("getNumberOfPageVisits({})", pageId);
		
		PageVisitHist pageVisitHist = new PageVisitHist();
		pageVisitHist.setPageId(pageId);
		Example<PageVisitHist> ex = Example.of(pageVisitHist);
		
		return pageVisitHistDao.count(ex);
	}
	
	public PageVisitHist addPageVisitHist(PageVisitHist pageVisitHist) {
		logger.info("addPageVisitHist({})", pageVisitHist);
		
		pageVisitHist.setDateCrt(new Date());
		
		SimpleDateFormat dtFmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String histId = pageVisitHist.getPageId() + "-" + pageVisitHist.getIpAddr() + "-" + dtFmt.format(pageVisitHist.getDateCrt());
  
        pageVisitHist.setHistId(histId);
        
		 
	    return pageVisitHistDao.save(pageVisitHist);
	}
}
