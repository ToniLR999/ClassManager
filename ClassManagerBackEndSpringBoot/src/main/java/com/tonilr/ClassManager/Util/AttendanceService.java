package com.tonilr.ClassManager.Util;


import com.tonilr.ClassManager.Model.Attendance;
import com.tonilr.ClassManager.Model.Class;
import com.tonilr.ClassManager.Model.Student;
import com.tonilr.ClassManager.Repository.AttendanceRepository;
import com.tonilr.ClassManager.Repository.ClassRepository;
import com.tonilr.ClassManager.Repository.StudentRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {

	@Autowired
    private final AttendanceRepository attendanceRepository;
    
	@Autowired
	private final ClassRepository classRepository;
	@Autowired
	private final StudentRepository studentRepository;
	
	

    public AttendanceService(AttendanceRepository attendanceRepository, ClassRepository classRepository,
			StudentRepository studentRepository) {
		super();
		this.attendanceRepository = attendanceRepository;
		this.classRepository = classRepository;
		this.studentRepository = studentRepository;
	}

	public void markAttendance(Long studentId, Long classId, String username) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Class clazz = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        if (!clazz.getProfessor().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized to mark attendance for this class");
        }

        LocalDate today = LocalDate.now();
        attendanceRepository.findByStudentAndClazzAndDate(student, clazz, today)
                .ifPresentOrElse(
                        a -> {},
                        () -> {
                            Attendance attendance = new Attendance();
                            attendance.setStudent(student);
                            attendance.setClazz(clazz);
                            attendance.setDate(today);
                            attendanceRepository.save(attendance);
                        });
    }

    public List<Attendance> getAttendanceByStudent(Long studentId) {
        return attendanceRepository.findByStudentId(studentId);
    }

    public List<Attendance> getAttendanceByClass(Long classId) {
        return attendanceRepository.findByClazzId(classId);
    }
}

