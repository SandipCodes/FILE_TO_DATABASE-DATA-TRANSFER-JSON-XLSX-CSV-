package com.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.app.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {

}
