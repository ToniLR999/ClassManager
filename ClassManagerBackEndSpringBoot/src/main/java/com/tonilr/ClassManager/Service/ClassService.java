package com.tonilr.ClassManager.Service;


import com.tonilr.ClassManager.DTO.ClassRequest;
import com.tonilr.ClassManager.DTO.ClassResponse;
import com.tonilr.ClassManager.Model.Class;
import com.tonilr.ClassManager.Model.User;
import com.tonilr.ClassManager.Repository.ClassRepository;
import com.tonilr.ClassManager.Repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClassService {

	@Autowired
    private final ClassRepository classRepository;
    
	@Autowired
	private final UserRepository userRepository;
	
	

    public ClassService(ClassRepository classRepository, UserRepository userRepository) {
		this.classRepository = classRepository;
		this.userRepository = userRepository;
	}

	public ClassResponse createClass(ClassRequest request, String username) {
        User professor = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Professor not found"));

        Class newClass = new Class();
        newClass.setName(request.getName());
        newClass.setDescription(request.getDescription());
        newClass.setSchedule(request.getSchedule());
        newClass.setProfessor(professor);

        Class saved = classRepository.save(newClass);
        return new ClassResponse(saved.getId(), saved.getName(), saved.getDescription(), saved.getSchedule(), professor.getUsername());
    }

    public List<ClassResponse> getClassesByProfessor(String username) {
        User professor = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Professor not found"));

        return classRepository.findByProfessor(professor)
                .stream()
                .map(c -> new ClassResponse(c.getId(), c.getName(), c.getDescription(), c.getSchedule(), professor.getUsername()))
                .collect(Collectors.toList());
    }
}