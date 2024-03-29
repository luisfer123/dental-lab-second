package com.dental.lab.data.Entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.dental.lab.data.enums.ERole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"users"})
@Entity
@Table(name = "Authority")
public class AuthorityEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "role", columnDefinition = "enum('ROLE_USER', 'ROLE_CLIENT', 'ROLE_TECHNICIAN', 'ROLE_DENTIST', 'ROLE_ADMIN')")
	private ERole role;
	
	@Column(name = "description")
	private String description;
	
	@ManyToMany(mappedBy = "authorities")
	@Builder.Default
	private Set<UserEntity> users = new HashSet<>();

	public AuthorityEntity(ERole role) {
		super();
		this.role = role;
	}
	
	public Set<UserEntity> getUsers() {
		if(users == null)
			users = new HashSet<>();
		
		return users;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == this)
			return true;
		
		if(!(o instanceof AuthorityEntity))
			return false;
		
		AuthorityEntity other = (AuthorityEntity) o;
		return id != null &&
				id.equals(other.id);
	}
	
	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

}
