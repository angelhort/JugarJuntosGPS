package com.demoTest.AplicationServices.Anuncios;

import java.util.List;
import java.util.Optional;

import com.demoTest.Entities.User;

public interface SADemo {
	List<User> getDemos();

	void addDemos(String demoName, int demoNumber, String demoLinkedValue);

	void deleteDemo(String demoName, Integer demoNumber, String demoLinkedValue);

	void modifyDemo(String demoName, int demoNumber, String demoLinkedValue);

	User getOneDemo(String demoName);

}
