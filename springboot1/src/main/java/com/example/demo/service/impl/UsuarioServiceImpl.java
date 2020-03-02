package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Usuario;
import com.example.demo.repository.IUsuarioRepository;
import com.example.demo.service.IUsuarioService;

@Service
public class UsuarioServiceImpl implements IUsuarioService{

	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	
	@Override
	public List<Usuario> getAllUsuarios() {
		return usuarioRepository.findAll();
	}
	
	@Override
	public Usuario createUsuario(Usuario usuario) throws Exception {
		if( checkUsernameAvailable(usuario) && checkPasswordMatch(usuario) ) {
			return usuarioRepository.save(usuario);
		}
		return null;
		
	}
	
	@Override
	public Usuario getUsusuarioById(long id) throws Exception {
		return usuarioRepository.findById(id).orElseThrow( () -> new Exception("El usuaio no existe") );
	}
	
	@Override
	public Usuario updateUsuario(Usuario usuario) throws Exception {
		
		Usuario editUsuario = getUsusuarioById(usuario.getId());
		
		editUsuario.setNombre(usuario.getNombre());
		editUsuario.setApellidos(usuario.getApellidos());
		editUsuario.setEmail(usuario.getEmail());
		editUsuario.setUserName(usuario.getUserName());
		editUsuario.setRoles(usuario.getRoles());
		
		Usuario save = usuarioRepository.save(editUsuario);
		
		return save;
	}
	
	private boolean checkUsernameAvailable(Usuario usuario) throws Exception {
		Optional<Usuario> findByUserName = usuarioRepository.findByUserName(usuario.getUserName());
		if( findByUserName.isPresent() ) {
			throw new Exception("El UserName ya existe");
		}
		return true;
	}
	
	private boolean checkPasswordMatch(Usuario usuario) throws Exception {
		if( !usuario.getPassword().equalsIgnoreCase(usuario.getConfirmPassword()) ) {
			throw new Exception("Las password no coinciden");
		}
		return true;
	}
}