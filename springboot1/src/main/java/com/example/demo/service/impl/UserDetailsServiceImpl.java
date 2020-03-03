package com.example.demo.service.impl;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Role;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.IUsuarioRepository;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	IUsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = usuarioRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("UserName no válido"));
		
		Set<GrantedAuthority> grandList = new HashSet<GrantedAuthority>();
		for( Role role : usuario.getRoles() ) {
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getDescripcion());
			grandList.add(grantedAuthority);
		}
		
		UserDetails user = new User(usuario.getUserName(), usuario.getPassword(), grandList);
		
		return user;
	}
}