package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.entity.Role;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.IRoleRepository;
import com.example.demo.repository.IUsuarioRepository;

@SpringBootTest
class Springboot1ApplicationTests {

	@Autowired
	IUsuarioRepository usuarioRepository;
	@Autowired
	IRoleRepository roleRepository;
	
	@Test
	void crearUsuarioTest() {
		
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
		Usuario usuario = new Usuario();
		
		usuario.setNombre("administrador");
		usuario.setApellidos("administrador");
		usuario.setEmail("administrador@administrador.com");
		usuario.setUserName("administrador");		
		usuario.setPassword(bCryptPasswordEncoder.encode("1") );
		Usuario userInsertado = usuarioRepository.save(usuario);
		
		assertTrue(usuario.getUserName().equals(userInsertado.getUserName()));
	}
	
	@Test
	void crearRoleTest() {
		
		Role role = new Role();
		
		role.setDescripcion("ROLE_DEVELOPER");
		role.setNombre("DEVELOPER");
		
		Role roleInsertado = roleRepository.save(role);
		
		assertTrue(role.getDescripcion().equals(roleInsertado.getDescripcion()));
	}

}
