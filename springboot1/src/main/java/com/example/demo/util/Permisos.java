package com.example.demo.util;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public abstract class Permisos {

	public static boolean isAdmin() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		UserDetails loggUser = null;
		if( principal instanceof UserDetails ) {
			loggUser = (UserDetails) principal;
		}
		
		GrantedAuthority roleAdmin = loggUser.getAuthorities().stream()
				.filter(x -> x.getAuthority().equals("ROLE_ADMIN"))
				.findFirst().orElse(null);

		return roleAdmin != null ? true : false;
	}
}