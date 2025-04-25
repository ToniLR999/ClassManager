import { Component, OnInit } from '@angular/core';
import { GradeService } from 'src/app/dashboard/grades/grades.service';
import { StudentService } from 'src/app/dashboard/students/students.service';
import { ClassService } from '../classes/class.service';

@Component({
  selector: 'app-grades',
  templateUrl: './grades.component.html',
  styleUrls: ['./grades.component.css'],
})
export class GradesComponent implements OnInit {
  grades: any[] = [];
  students: any[] = [];
  classes: any[] = [];


  newGrade = {
    studentId: null,
    classId: null,
    subject: '',
    value: null,
    description: ''
  };

  editMode = false;
  editGradeId: number | null = null;
  editGrade = {
    subject: '',
    score: null
  };

  constructor(
    private gradeService: GradeService,
    private studentService: StudentService,
    private classService: ClassService
  ) {}

  ngOnInit(): void {
    this.loadGrades();
    this.studentService.getAllStudents().subscribe(data => {
      this.students = data;
    });

    this.classService.getAllClasses().subscribe(data => {
      this.classes = data;
    });
  }

  loadGrades() {
    this.gradeService.getAllGrades().subscribe(data => {
      this.grades = data;
    });
  }

  createGrade() {
    this.gradeService.createGrade(this.newGrade).subscribe(() => {
      this.newGrade = { studentId: null, classId: null, subject: '', value: null, description: ''};
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
