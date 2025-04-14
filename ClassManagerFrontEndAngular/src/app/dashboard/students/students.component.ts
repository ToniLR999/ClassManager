import { Component, OnInit } from '@angular/core';
import { StudentService } from 'src/app/dashboard/students/students.service';

@Component({
  selector: 'app-students',
  templateUrl: './students.component.html',
  styleUrls: ['./students.component.css'],
})
export class StudentsComponent implements OnInit {
  students: any[] = [];

  newStudent = {
    name: '',
    email: ''
  };

  editMode = false;
  editStudentId: number | null = null;
  editStudent = {
    name: '',
    email: ''
  };

  constructor(private studentService: StudentService) {}

  ngOnInit(): void {
    this.loadStudents();
  }

  loadStudents() {
    this.studentService.getAllStudents().subscribe(data => {
      this.students = data;
    });
  }

  createStudent() {
    if (!this.newStudent.name || !this.newStudent.email) return;
    this.studentService.createStudent(this.newStudent).subscribe(() => {
      this.newStudent = { name: '', email: '' };
      this.loadStudents();
    });
  }

  startEdit(student: any) {
    this.editMode = true;
    this.editStudentId = student.id;
    this.editStudent = { name: student.name, email: student.email };
  }

  updateStudent() {
    if (!this.editStudentId) return;
    this.studentService.updateStudent(this.editStudentId, this.editStudent).subscribe(() => {
      this.cancelEdit();
      this.loadStudents();
    });
  }

  cancelEdit() {
    this.editMode = false;
    this.editStudentId = null;
    this.editStudent = { name: '', email: '' };
  }

  deleteStudent(id: number) {
    if (confirm('¿Eliminar alumno?')) {
      this.studentService.deleteStudent(id).subscribe(() => {
        this.loadStudents();
      });
    }
  }
}
