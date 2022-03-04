package com.demoTest.AplicationServices.Anuncios;

import java.util.List;
import java.util.Optional;

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

	@Override
	public void deleteDemo(String demoName, Integer demoNumber, String demoLinkedValue) {
		if(demoNumber==null || demoLinkedValue==null) demoRepository.deleteById(demoName);
		else demoRepository.delete(new User(demoName, demoNumber, demoLinkedValue));
	}

	@Override
	public void modifyDemo(String demoName, int demoNumber, String demoLinkedValue) {
		demoRepository.save(new User(demoName, demoNumber, demoLinkedValue));
		
	}

	@Override
	public User getOneDemo(String demoName) {
		return demoRepository.findById(demoName).get();
	}

}
