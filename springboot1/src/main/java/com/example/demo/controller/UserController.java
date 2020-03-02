package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
	
	@PostMapping("/userForm")
	public String addUsuario(@Valid @ModelAttribute("userForm")Usuario usuario, BindingResult result, ModelMap model) {
		
		if( result.hasErrors() ) {
			model.addAttribute("userForm", usuario);
			model.addAttribute("listForm", "active");
		}else {
			try {
				usuarioService.createUsuario(usuario);
				model.addAttribute("userForm", new Usuario());
				model.addAttribute("listTab", "active");
			} catch (Exception e) {
				model.addAttribute("formErrorMessage", e.getMessage());
				model.addAttribute("userForm", usuario);
				model.addAttribute("listForm", "active");
				model.addAttribute("roles", roleRepository.findAll());
				model.addAttribute("usuarioList", usuarioService.getAllUsuarios());
			}
		}
		
		model.addAttribute("roles", roleRepository.findAll());
		model.addAttribute("usuarioList", usuarioService.getAllUsuarios());
		
		return "user-form/user-view";
	}
}