package com.jtrack.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jtrack.model.PageVisitHist;

public interface PageVisitHistDao extends JpaRepository <PageVisitHist, String> {

}
