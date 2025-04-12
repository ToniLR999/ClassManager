package com.tonilr.ClassManager.Repository;

import com.tonilr.ClassManager.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}