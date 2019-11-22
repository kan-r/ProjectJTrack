package com.jtrack.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jtrack.model.User;

public interface UserDao extends JpaRepository <User, String> {

}
