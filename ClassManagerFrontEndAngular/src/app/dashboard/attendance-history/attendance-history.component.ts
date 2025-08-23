import { Component, OnInit } from '@angular/core';
import { AttendanceService } from '../attendance/attendance.service';
import { ClassService } from '../classes/class.service';
import { StudentService } from '../students/students.service';

@Component({
  selector: 'app-attendance-history',
  templateUrl: './attendance-history.component.html',
  styleUrls: ['./attendance-history.component.css']
})
export class AttendanceHistoryComponent implements OnInit {
  attendance: any[] = [];
  classes: any[] = [];
  students: any[] = [];
  selectedClassId!: number;
  selectedStudentId!: number;

  constructor(
    private attendanceService: AttendanceService,
    private classService: ClassService,
    private studentService: StudentService
  ) {}

  ngOnInit(): void {
    this.classService.getAllClasses().subscribe(res => this.classes = res);
    this.studentService.getAllStudents().subscribe(res => this.students = res);
  }

  fetchByClass() {
    if (!this.selectedClassId) return;
    this.attendanceService.getAttendanceByClass(this.selectedClassId).subscribe(res => {
      this.attendance = res as any[];
    });
  }

  fetchByStudent() {
    if (!this.selectedStudentId) return;
    // Nota: getAttendanceByStudent fue eliminado del servicio
    // Se puede implementar una nueva funcionalidad o usar getAttendanceByClass
    console.warn('Funcionalidad de búsqueda por estudiante no implementada');
  }
}
