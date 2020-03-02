package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Usuario;


public interface IUsuarioService {

	public List<Usuario> getAllUsuarios();

	public Usuario createUsuario(Usuario usuario) throws Exception;

	public Usuario getUsusuarioById(long id) throws Exception;

	public Usuario updateUsuario(Usuario usuario) throws Exception;
	
}