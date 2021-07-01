package com.security.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account implements Serializable{
	@Id
	private String username;
	private String password;
	private String fullname;
	private String email;
	private String photo;
	
//	@JsonIgnore
//	@OneToMany(mappedBy = "account")
//	List<Order> orders;
	
//	@JsonIgnore
//	@OneToMany(mappedBy = "account")
//	List<AccountRole> accountRoles;
}
