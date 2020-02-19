package com.jtrack.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jtrack.model.LoginHist;

public interface LoginHistDao extends JpaRepository <LoginHist, String> {

}
