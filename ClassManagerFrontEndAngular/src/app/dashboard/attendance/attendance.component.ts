import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AttendanceService } from './attendance.service';
import { ClassService } from '../classes/class.service';
import { StudentService } from '../students/students.service';

@Component({
  selector: 'app-attendance',
  templateUrl: './attendance.component.html',
  styleUrls: ['./attendance.component.css']
})
export class AttendanceComponent implements OnInit {
  classes: any[] = [];
  students: any[] = [];
  selectedClassId!: number;
  selectedStudentId!: number;
  
  // Variables para el feedback
  feedbackMessage: string = '';
  feedbackType: 'success' | 'error' = 'success';

  constructor(
    private attendanceService: AttendanceService,
    private classService: ClassService,
    private studentService: StudentService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.loadClasses();
    this.loadStudents();
  }

  loadClasses() {
    this.classService.getAllClasses().subscribe((res: any) => {
      this.classes = res;
    });
  }

  loadStudents() {
    this.studentService.getAllStudents().subscribe((res: any) => {
      this.students = res;
    });
  }

  markAttendance() {
    console.log("Marking attendance");
    const attendanceData = {
      classId: this.selectedClassId,
      studentId: this.selectedStudentId,
      date: new Date().toISOString()
    };

    this.attendanceService.registerAttendance(attendanceData).subscribe({
      next: () => {
        console.log("✅ Attendance marked successfully");
        this.showFeedback('✅ Asistencia marcada exitosamente', 'success');
      },
      error: (error) => {
        console.error("❌ Error marking attendance:", error);
        this.showFeedback('❌ Error al marcar la asistencia', 'error');
      }
    });
  }

  private showFeedback(message: string, type: 'success' | 'error') {
    this.feedbackMessage = message;
    this.feedbackType = type;
    
    // Limpiar el mensaje después de 3 segundos
    setTimeout(() => {
      this.feedbackMessage = '';
    }, 3000);
  }
}