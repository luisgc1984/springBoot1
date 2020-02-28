package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
//@SequenceGenerator(name="seq_role", initialValue=1, allocationSize=100)
@Getter @Setter
public class Role implements Serializable{

	private static final long serialVersionUID = 4625095532653658514L;
	
	@Id
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_role")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(name="NOMBRE")
	private String nombre;
	@Column(name="DESCRIPCION")
	private String descripcion;
	
}