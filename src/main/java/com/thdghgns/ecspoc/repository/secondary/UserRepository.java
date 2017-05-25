package com.thdghgns.ecspoc.repository.secondary;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thdghgns.ecspoc.entity.secondary.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
