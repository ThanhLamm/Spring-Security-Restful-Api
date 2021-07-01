package com.security.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.security.entity.AccountRole;

@Repository
public interface AccountRoleRepository extends JpaRepository<AccountRole, Long>{
	@Query(value = "select * from account_role where username = ?", nativeQuery = true)
	List<AccountRole> findByAccount(String username);
}
