package com.demoTest.AplicationServices.Anuncios;

import java.util.List;

import com.demoTest.Entities.User;

public interface SADemo {
	List<User> getDemos();

	void addDemos(String demoName, int demoNumber, String demoLinkedValue);
}
