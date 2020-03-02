package com.example.demo.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity
public class Usuario implements Serializable{

	private static final long serialVersionUID = 6916437357598746694L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="NOMBRE")
	@NotBlank(message = "Debes especificar el nombre")
	private String nombre;
	
	@Column(name="APELLIDOS")
	@NotBlank
	private String apellidos;
	
	@Column(name="EMAIL")
	@NotBlank
	private String email;
	
	@Column(name="USERNAME")
	@NotBlank
	@Size(min=4, max=10, message="Error en el tamano. Debe ser entre 4 y 10.")	
	private String userName;
	
	@Column(name="PASSWORD")
	@NotBlank
	private String password;
	
	@Transient
	private String confirmPassword;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="usuario_roles", 
			joinColumns = @JoinColumn(name="usuario_id"),  
			inverseJoinColumns = @JoinColumn(name="role_id"))
	private Set<Role> roles;

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}