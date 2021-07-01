package com.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.security.entity.AppRole;

@Repository
public interface AppRoleRepository extends JpaRepository<AppRole, String>{

}
