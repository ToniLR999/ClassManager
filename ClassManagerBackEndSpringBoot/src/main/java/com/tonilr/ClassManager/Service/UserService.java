package com.tonilr.ClassManager.Service;

import com.tonilr.ClassManager.Model.User;
import com.tonilr.ClassManager.Repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}