package com.tonilr.ClassManager.Service;

import com.tonilr.ClassManager.Model.User;
import com.tonilr.ClassManager.Repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

	@Autowired
    private final UserRepository userRepository;

	
	
    public UserService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}



	public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}