import { Component, OnInit } from '@angular/core';
import { GradeService } from 'src/app/dashboard/grades/grades.service';
import { StudentService } from 'src/app/dashboard/students/students.service';

@Component({
  selector: 'app-grades',
  templateUrl: './grades.component.html',
  styleUrls: ['./grades.component.css'],
})
export class GradesComponent implements OnInit {
  grades: any[] = [];
  students: any[] = [];

  newGrade = {
    studentId: null,
    subject: '',
    score: null
  };

  editMode = false;
  editGradeId: number | null = null;
  editGrade = {
    subject: '',
    score: null
  };

  constructor(
    private gradeService: GradeService,
    private studentService: StudentService
  ) {}

  ngOnInit(): void {
    this.loadGrades();
    this.studentService.getAllStudents().subscribe(data => {
      this.students = data;
    });
  }

  loadGrades() {
    this.gradeService.getAllGrades().subscribe(data => {
      this.grades = data;
    });
  }

  createGrade() {
    this.gradeService.createGrade(this.newGrade).subscribe(() => {
      this.newGrade = { studentId: null, subject: '', score: null };
      this.loadGrades();
    });
  }

  startEdit(grade: any) {
    this.editMode = true;
    this.editGradeId = grade.id;
    this.editGrade = { subject: grade.subject, score: grade.score };
  }

  updateGrade() {
    if (!this.editGradeId) return;
    this.gradeService.updateGrade(this.editGradeId, this.editGrade).subscribe(() => {
      this.cancelEdit();
      this.loadGrades();
    });
  }

  cancelEdit() {
    this.editMode = false;
    this.editGradeId = null;
    this.editGrade = { subject: '', score: null };
  }

  deleteGrade(id: number) {
    if (confirm('¿Eliminar calificación?')) {
      this.gradeService.deleteGrade(id).subscribe(() => {
        this.loadGrades();
      });
    }
  }
}
