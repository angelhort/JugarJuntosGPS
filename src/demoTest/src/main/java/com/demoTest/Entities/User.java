package com.demoTest.Entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "demo_first_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "demo_name")
	@NonNull
	private String demoName;
	
	@Column(name = "demo_number")
	private int demoNumber;
	
	@Column(name = "demo_linked_value")
	private String demoLinkedValue;
}
