package com.demoTest.AplicationServices.Anuncios;

import java.util.List;

import org.springframework.stereotype.Service;

import com.demoTest.Entities.User;
import com.demoTest.Repositories.DemoRepository;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.RequiredArgsConstructor;

@Service
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SADemoImp implements SADemo{
	
	@Autowired
	private DemoRepository demoRepository;
	@Override
	public List<User> getDemos() {
		return demoRepository.findAll();
	}
	
	@Override
    public void addDemos(String demoName, int demoNumber, String demoLinkedValue) {
        demoRepository.save(new User(demoName, demoNumber, demoLinkedValue));
    }

}
