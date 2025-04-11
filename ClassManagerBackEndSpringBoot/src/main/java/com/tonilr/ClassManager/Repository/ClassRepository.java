package com.tonilr.ClassManager.Repository;

import com.tonilr.ClassManager.Model.Class;
import com.tonilr.ClassManager.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassRepository extends JpaRepository<Class, Long> {
    List<Class> findByProfessor(User professor);
}
