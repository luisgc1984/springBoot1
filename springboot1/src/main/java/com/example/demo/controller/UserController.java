package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Usuario;
import com.example.demo.repository.IRoleRepository;
import com.example.demo.service.IUsuarioService;

@Controller
public class UserController {

	@Autowired
	private IUsuarioService usuarioService;
	@Autowired
	private IRoleRepository roleRepository;
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/userForm")
	public String getUserFor(Model model) {
		model.addAttribute("userForm", new Usuario());
		model.addAttribute("roles", roleRepository.findAll());
		model.addAttribute("usuarioList", usuarioService.getAllUsuarios());
		model.addAttribute("listTab", "active");
		
		return "user-form/user-view";
	}
}