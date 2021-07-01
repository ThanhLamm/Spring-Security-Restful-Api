package com.security.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.entity.Account;
import com.security.repository.AccountRepository;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/accounts")
public class AccountRestController {
	@Autowired
	AccountRepository accountRepository;

	@GetMapping
	public ResponseEntity<List<Account>> findAll() {
		return ResponseEntity.ok(accountRepository.findAll());
	}

	@GetMapping("{username}")
	public ResponseEntity<Account> findOne(@PathVariable("username") String username) {
		if (!accountRepository.existsById(username)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(accountRepository.findById(username).get());
	}

	@PostMapping()
	public ResponseEntity<Account> post(@RequestBody Account account) {
		if (accountRepository.existsById(account.getUsername())) {
			return ResponseEntity.badRequest().build();
		}
		accountRepository.save(account);
		return ResponseEntity.ok(account);
	}

	@PutMapping("{username}")
	public ResponseEntity<Account> put(@RequestBody Account account, @PathVariable("username") String username) {
		if (!accountRepository.existsById(username)) {
			return ResponseEntity.notFound().build();
		}
		if(!accountRepository.existsById(account.getUsername())) {
			return ResponseEntity.badRequest().build();
		}
		accountRepository.save(account);
		return ResponseEntity.ok(account);
	}
	
	@DeleteMapping("{username}")
	public ResponseEntity<Void> delete(@PathVariable("username") String username) {
		if(!accountRepository.existsById(username)) {
			return ResponseEntity.notFound().build();
		}
		accountRepository.deleteById(username);
		return ResponseEntity.ok().build();
	}
}
