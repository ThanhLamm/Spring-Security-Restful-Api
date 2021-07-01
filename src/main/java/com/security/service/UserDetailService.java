package com.security.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import com.security.entity.Account;
import com.security.entity.AccountRole;
import com.security.entity.AppRole;
import com.security.repository.AccountRepository;
import com.security.repository.AccountRoleRepository;
import com.security.repository.AppRoleRepository;

@Service
public class UserDetailService implements UserDetailsService{

	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	AccountRoleRepository accountRoleRepository;
	
	@Autowired
	AppRoleRepository appRoleRepository;
	
	@Autowired
	BCryptPasswordEncoder pe;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		try {
			Account acc = accountRepository.findById(username.trim()).get();
			
			System.out.println(acc.toString());
			
			String password = acc.getPassword();
			
//			String[] roles = acc.getAccountRoles().stream()
//					.map(au -> au.getRole().getId())
//					.collect(Collectors.toList()).toArray(new String[0]);
			
//			List<String> listRole = new ArrayList<String>();
			
			String[] roles = accountRoleRepository.findByAccount(username).stream()
					.map(au -> au.getRole().getId())
					.collect(Collectors.toList()).toArray(new String[0]);
			
			return User.withUsername(username).password(password).roles(roles).build();
//		} catch (Exception e) {
//			System.out.println(username+" not found!");
//			throw new UsernameNotFoundException(username +" not found!");
//		}
		
	}
	
	public void loginFromOAuth2(OAuth2AuthenticationToken oauth2) {
		String fullname = oauth2.getPrincipal().getAttribute("name");
		String email = oauth2.getPrincipal().getAttribute("email");
		Optional<Account> acc = accountRepository.findByEmail(email);
		UserDetails user = null;
		if(acc.isPresent()) {
			Account account = acc.get();
			String[] roles = accountRoleRepository.findByAccount(account.getUsername()).stream()
					.map(au -> au.getRole().getId())
					.collect(Collectors.toList()).toArray(new String[0]);
			user = User.withUsername(email)
					.password(account.getPassword()).roles(roles).build();
		} else {			
			String password = Long.toHexString(System.currentTimeMillis());		
			//luu vao data
			Account account = new Account(email, password, fullname, email, "image");
			accountRepository.save(account);
			accountRoleRepository.save(new AccountRole(0L, account, new AppRole("GUEST", null, null)));
			user = User.withUsername(email)
					.password(pe.encode(password)).roles("GUEST").build();
		}		
		
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
	}
	
}
