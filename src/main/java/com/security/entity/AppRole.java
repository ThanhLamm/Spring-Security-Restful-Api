package com.security.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "app_roles")
public class AppRole {
	@Id
	private String id;
	private String name;
	
	@JsonIgnore
	@OneToMany(mappedBy = "role")
	List<AccountRole> accountRoles;
}
