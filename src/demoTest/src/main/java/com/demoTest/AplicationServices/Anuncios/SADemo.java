package com.demoTest.AplicationServices.Anuncios;

import java.util.List;
import java.util.Optional;

import com.demoTest.Entities.User;


public interface SADemo {
	List<User> getDemos();

	boolean addDemos(String demoName, int demoNumber, String demoLinkedValue);

	boolean deleteDemo(String demoName, Integer demoNumber, String demoLinkedValue);

	boolean modifyDemo(String demoName, int demoNumber, String demoLinkedValue);

	User getOneDemo(String demoName);

}
