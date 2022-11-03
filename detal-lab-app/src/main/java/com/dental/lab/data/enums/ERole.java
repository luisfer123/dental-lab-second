package com.dental.lab.data.enums;

import com.dental.lab.data.Entities.AuthorityEntity;
import com.dental.lab.data.Entities.UserEntity;

/**
 * {@linkplain ERole} defines the {@code User} roles. {@linkplain AuthorityEntity} is a wrapper for
 * a given {@linkplain ERole}. Each {@linkplain UserEntity} may have one or more 
 * {@linkplain AuthorityEntity}s associated, which each corresponds with one {@linkplain ERole} 
 * (Role). {@code ROLE_USER} is mandatory and all registered {@linkplain User}s must have
 * it.
 * 
 * @author Luis Fernando Martinez Oritz
 *
 */
public enum ERole {
	
	ROLE_USER ("Rol Usuario"),
	
	ROLE_CLIENT ("Rol Cliente"),
	
	ROLE_TECHNICIAN ("Rol Técnico"),
	
	ROLE_DENTIST ("Rol Dentista"),
	
	ROLE_ADMIN ("Rol Administrador")
	
	;
	
	private final String nameInSpanish;
	
	private ERole(String nameInSpanish) {
		this.nameInSpanish = nameInSpanish;
	}
	
	public String getNameInSpanish() {
		return this.nameInSpanish;
	}

}
