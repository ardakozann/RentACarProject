package com.turkcellcamp.rentacar.rentacar.dataaccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcellcamp.rentacar.rentacar.entities.concretes.User;

@Repository
public interface UserDao extends JpaRepository <User, Integer> {
	User getByEmail(String email);
}
