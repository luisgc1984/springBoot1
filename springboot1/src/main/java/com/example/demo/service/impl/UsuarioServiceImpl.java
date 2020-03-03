package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ChangePassword;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.IUsuarioRepository;
import com.example.demo.service.IUsuarioService;
import com.example.demo.util.Permisos;

@Service
public class UsuarioServiceImpl implements IUsuarioService{

	@Autowired
	private IUsuarioRepository usuarioRepository;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	@Override
	public List<Usuario> getAllUsuarios() {
		return usuarioRepository.findAll();
	}
	
	@Override
	public Usuario createUsuario(Usuario usuario) throws Exception {
		if( checkUsernameAvailable(usuario) && checkPasswordMatch(usuario) ) {
			//Se encripta la password
			usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
			
			return usuarioRepository.save(usuario);
		}
		return null;
		
	}
	
	@Override
	public Usuario getUsusuarioById(long id) throws Exception {
		return usuarioRepository.findById(id).orElseThrow( () -> new Exception("El usuario no existe") );
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
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	@Override
	public void removeUsuario(Long id) throws Exception {
		Usuario removeUsuario = getUsusuarioById(id);
		usuarioRepository.delete(removeUsuario);
	}
	
	@Override
	public Usuario changePassword(ChangePassword change) throws Exception {
		Usuario usuario = getUsusuarioById(change.getId());
		
		if( !Permisos.isAdmin() && !bCryptPasswordEncoder.matches(change.getCurrentPassword(), usuario.getPassword()) ) {
			throw new Exception("La password actual no es correcta");
		}
		if( bCryptPasswordEncoder.matches(change.getNewPassword(), usuario.getPassword()) ) {
			throw new Exception("La nueva password y la existente son iguales");
		}
		if( !change.getNewPassword().equalsIgnoreCase(change.getConfirmNewPassword()) ) {
			throw new Exception("La nueva password y la confirmacion no coinciden");
		}
		
		usuario.setPassword(bCryptPasswordEncoder.encode(change.getNewPassword()));
		return usuarioRepository.save(usuario);
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