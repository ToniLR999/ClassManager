package com.tonilr.ClassManager.Service;


import com.tonilr.ClassManager.DTO.ClassRequest;
import com.tonilr.ClassManager.DTO.ClassResponse;
import com.tonilr.ClassManager.Model.Class;
import com.tonilr.ClassManager.Model.User;
import com.tonilr.ClassManager.Repository.ClassRepository;
import com.tonilr.ClassManager.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ClassService {

	@Autowired
    private final ClassRepository classRepository;
    
	@Autowired
	private final UserRepository userRepository;
	
	

    public ClassService(ClassRepository classRepository, UserRepository userRepository) {
		this.classRepository = classRepository;
		this.userRepository = userRepository;
	}

    public Page<ClassResponse> getAllClasses(Pageable pageable) {
        return classRepository.findAll(pageable)
                .map(c -> new ClassResponse(c.getId(), c.getName(), c.getDescription(), 
                                          c.getSubjects(), c.getSchedule(), c.getProfessor().getUsername()));
    }
    
	@Transactional
	@CacheEvict(value = "classesByProfessor", key = "#username")
	public ClassResponse createClass(ClassRequest request, String username) {
        User professor = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Professor not found"));

        Class newClass = new Class();
        newClass.setName(request.getName());
        newClass.setDescription(request.getDescription());
        newClass.setSubjects(request.getSubjects());
        newClass.setSchedule(request.getSchedule());
        newClass.setProfessor(professor);

        Class saved = classRepository.save(newClass);
        return new ClassResponse(saved.getId(), saved.getName(), saved.getDescription(),saved.getSubjects(), saved.getSchedule(), professor.getUsername());
    }

    @Transactional
    @Cacheable(value = "classesByProfessor", key = "#username")
    public List<ClassResponse> getClassesByProfessor(String username) {
        return userRepository.findByUsername(username)
                .map(professor -> classRepository.findByProfessor(professor)
                        .stream()
                        .map(c -> new ClassResponse(c.getId(), c.getName(), c.getDescription(), c.getSubjects(), c.getSchedule(), professor.getUsername()))
                        .collect(Collectors.toList()))
                .orElseGet(List::of);
    }
    
    public ClassResponse getClassById(Long id, String username) {
        Class c = classRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class not found"));
        if (!c.getProfessor().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized access");
        }
        return new ClassResponse(c.getId(), c.getName(), c.getDescription() ,c.getSubjects(), c.getSchedule(), c.getProfessor().getUsername());
    }

    @Transactional
    @CacheEvict(value = "classesByProfessor", key = "#username")
    public ClassResponse updateClass(Long id, ClassRequest request, String username) {
        Class c = classRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class not found"));
        if (!c.getProfessor().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized access");
        }
        c.setName(request.getName());
        c.setDescription(request.getDescription());
        c.setSchedule(request.getSchedule());
        c.setSubjects(request.getSubjects());
        Class updated = classRepository.save(c);
        return new ClassResponse(updated.getId(), updated.getName(), updated.getDescription() , c.getSubjects(), updated.getSchedule(), updated.getProfessor().getUsername());
    }

    @Transactional
    @CacheEvict(value = "classesByProfessor", key = "#username")
    public void deleteClass(Long id, String username) {
        Class c = classRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class not found"));
        if (!c.getProfessor().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized access");
        }
        classRepository.delete(c);
    }
    
    public List<String> getSubjectsByClass(Long classId, String username) {
        Class clazz = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        if (!clazz.getProfessor().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized access to class students");
        }

        return clazz.getSubjects();
    }
}