package com.eworld.entity;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "Roles")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {
	
	@Id
	private String id;
	
	private String name;
	
	@OneToMany(mappedBy = "role",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
	private Set<UserRole> authorities;
}
