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
    public boolean addDemos(String demoName, int demoNumber, String demoLinkedValue) {
		try {
		User u = demoRepository.findById(demoName).get();
		}catch(Exception e) {
		demoRepository.save(new User(demoName, demoNumber, demoLinkedValue));
		return true;
		
        }
		return false;
    
	}

	@Override
	public boolean deleteDemo(String demoName, Integer demoNumber, String demoLinkedValue) {
		try {
			User u = demoRepository.findById(demoName).get();
			if(demoNumber==null || demoLinkedValue==null) demoRepository.deleteById(demoName);
			else demoRepository.delete(new User(demoName, demoNumber, demoLinkedValue));
			}catch(Exception e) {
			return false;
			
	        }
		return true;
	}

	@Override
	public boolean modifyDemo(String demoName, int demoNumber, String demoLinkedValue) {
		try {
	        demoRepository.save(new User(demoName, demoNumber, demoLinkedValue));
			}catch(Exception e) {
	        	e.printStackTrace();
	        	return false;
	        }
			return true;
		
	}

	@Override
	public User getOneDemo(String demoName) {
		try {
			User u = demoRepository.findById(demoName).get();
			return u;
			}catch(Exception e) {
			return null;
			
	        }
	}

}
