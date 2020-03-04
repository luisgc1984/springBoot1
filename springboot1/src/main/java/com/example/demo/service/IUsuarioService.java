package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.ChangePassword;
import com.example.demo.entity.Usuario;
import com.example.demo.exceptions.UserNotFound;


public interface IUsuarioService {

	public List<Usuario> getAllUsuarios();

	public Usuario createUsuario(Usuario usuario) throws Exception;

	public Usuario getUsusuarioById(long id) throws UserNotFound;

	public Usuario updateUsuario(Usuario usuario) throws Exception;

	public void removeUsuario(Long id) throws Exception;

	public Usuario changePassword(ChangePassword change) throws Exception;
	
}