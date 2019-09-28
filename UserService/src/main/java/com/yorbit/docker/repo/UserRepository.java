package com.yorbit.docker.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yorbit.docker.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
