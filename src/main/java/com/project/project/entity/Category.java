package com.project.project.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Data
@Entity
@Table(
		name="categories", uniqueConstraints ={@UniqueConstraint(columnNames = {"name"})}
	)
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="name",nullable = false)
	private String name;
	
	@Column(name="detail",nullable = true)
	private String detail;
	

}
