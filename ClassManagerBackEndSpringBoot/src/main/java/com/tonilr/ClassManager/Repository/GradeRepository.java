package com.tonilr.ClassManager.Repository;


import com.tonilr.ClassManager.Model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findByStudentId(Long studentId);
    List<Grade> findByClazzId(Long classId);
}