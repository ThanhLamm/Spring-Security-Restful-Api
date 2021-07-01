package com.security.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.entity.AccountRole;
import com.security.repository.AccountRepository;
import com.security.repository.AccountRoleRepository;
import com.security.repository.AppRoleRepository;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/authorities")
public class AuthorityRestController {
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	AccountRoleRepository accountRoleRepository;
	
	@Autowired
	AppRoleRepository appRoleRepository;
	
	@GetMapping()
	public Map<String, Object> getAuthorities() {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("authorities", accountRoleRepository.findAll());
		data.put("roles", appRoleRepository.findAll());
		data.put("accounts", accountRepository.findAll());
		return data;
	}
	
	@PostMapping("")
	public AccountRole create(@RequestBody AccountRole accountRole) {
		return accountRoleRepository.save(accountRole);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Long id) {
		accountRoleRepository.deleteById(id);
	}
}
