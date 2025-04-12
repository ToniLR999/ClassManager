package com.tonilr.ClassManager.Repository;


import com.tonilr.ClassManager.Model.Attendance;
import com.tonilr.ClassManager.Model.Class;
import com.tonilr.ClassManager.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByStudentAndClazzAndDate(Student student, Class clazz, LocalDate date);
    List<Attendance> findByStudentId(Long studentId);
    List<Attendance> findByClazzId(Long classId);
}