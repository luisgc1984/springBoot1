package com.example.demo.controller;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.dto.ChangePassword;
import com.example.demo.entity.Usuario;
import com.example.demo.exceptions.UserNotFound;
import com.example.demo.repository.IRoleRepository;
import com.example.demo.service.IUsuarioService;

@Controller
public class UserController {

	@Autowired
	private IUsuarioService usuarioService;
	@Autowired
	private IRoleRepository roleRepository;
	
	@GetMapping({"/", "/login"})
	public String index() {
		return "index";
	}
	
	@GetMapping("/userForm")
	public String getUserFor(Model model) {
		model.addAttribute("userForm", new Usuario());
		model.addAttribute("roles", roleRepository.findAll());
		model.addAttribute("usuarioList", usuarioService.getAllUsuarios());
		model.addAttribute("tabList", "active");
		
		return "user-form/user-view";
	}
	
	@PostMapping("/userForm")
	public String addUsuario(@Valid @ModelAttribute("userForm")Usuario usuario, BindingResult result, ModelMap model) {
		
		if( result.hasErrors() ) {
			model.addAttribute("userForm", usuario);
			model.addAttribute("tabForm", "active");
		}else {
			try {
				usuarioService.createUsuario(usuario);
				model.addAttribute("userForm", new Usuario());
				model.addAttribute("tabList", "active");
			} catch (Exception e) {
				model.addAttribute("formErrorMessage", e.getMessage());
				model.addAttribute("userForm", usuario);
				model.addAttribute("tabForm", "active");
				model.addAttribute("roles", roleRepository.findAll());
				model.addAttribute("usuarioList", usuarioService.getAllUsuarios());
			}
		}
		
		model.addAttribute("roles", roleRepository.findAll());
		model.addAttribute("usuarioList", usuarioService.getAllUsuarios());
		
		return "user-form/user-view";
	}
	
	@GetMapping("/editUser/{id}")
	public String getUsuario(@PathVariable(name="id")Long id, Model model) throws Exception {
		
		model.addAttribute("userForm", usuarioService.getUsusuarioById(id));
		model.addAttribute("roles", roleRepository.findAll());
		model.addAttribute("usuarioList", usuarioService.getAllUsuarios());
		model.addAttribute("tabForm", "active");
		model.addAttribute("editMode", true);
		model.addAttribute("passwordForm", new ChangePassword(id));
		
		return "user-form/user-view";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')") //si no tiene permisos, devulve There was an unexpected error (type=Forbidden, status=403).
	@PostMapping("/editUser")
	public String editUsuario(@Valid @ModelAttribute("userForm")Usuario usuario, BindingResult result, ModelMap model) {
		
		if( result.hasErrors() ) {
			model.addAttribute("userForm", usuario);
			model.addAttribute("tabForm", "active");
			model.addAttribute("editMode", true);
			model.addAttribute("passwordForm", new ChangePassword(usuario.getId()));
		}else {
			try {
				usuarioService.updateUsuario(usuario);
				model.addAttribute("userForm", new Usuario());
				model.addAttribute("tabList", "active");
			} catch (Exception e) {
				model.addAttribute("formErrorMessage", e.getMessage());
				model.addAttribute("userForm", usuario);
				model.addAttribute("tabForm", "active");
				model.addAttribute("roles", roleRepository.findAll());
				model.addAttribute("usuarioList", usuarioService.getAllUsuarios());
				model.addAttribute("editMode", true);
				model.addAttribute("passwordForm", new ChangePassword(usuario.getId()));
			}
		}
		
		model.addAttribute("roles", roleRepository.findAll());
		model.addAttribute("usuarioList", usuarioService.getAllUsuarios());
		
		return "user-form/user-view";
	}
	
	@GetMapping("/userForm/cancel")
	public String cancelEditUsuario() {
		return "redirect:/userForm";
	}
	
	@GetMapping("/deleteUser/{id}")
	public String deleteUsuario(@PathVariable(name="id")Long id, Model model) throws Exception {
		
		try {
			usuarioService.removeUsuario(id);
		} catch (UserNotFound e) {
			model.addAttribute("listErrorMessage", e.getMessage());
		}
		
		return getUserFor(model);
	}
	
	@PostMapping(value="/editUser/changePassword")
	public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePassword change, Errors errors){
		try {
			if( errors.hasErrors() ) {
				String result = errors.getAllErrors()
						.stream().map(x -> x.getDefaultMessage())
						.collect(Collectors.joining(""));
				
				throw new Exception(result);
			}
			
			usuarioService.changePassword(change);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok("success");
	}
}