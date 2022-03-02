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
@Table(name = "DEMO_FIRST_TABLE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "DEMO_NAME")
	@NonNull
	private String demoName;
	
	@Column(name = "DEMO_NUMBER")
	private String demoNumber;
	
	@Column(name = "DEMO_LINKED_VALUE")
	private String demoLinkedValue;
}
