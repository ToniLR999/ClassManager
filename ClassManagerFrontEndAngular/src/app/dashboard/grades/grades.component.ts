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
  subjects: any[] = [];



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
    classId: null,
    subject: '',
    score: null
  };
  editSubjects: string[] = [];
  editSubjectsLoading = false;



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

  onClassSelected(classId: number | null) {
    if (classId !== null) {
      this.gradeService.getSubjectsByClass(classId).subscribe({
        next: (subjects) => {
          this.subjects = subjects;
        },
        error: (err) => {
          console.error('Error cargando subjects:', err);
          this.subjects = [];
        }
      });
    } else {
      this.subjects = [];
    }
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
    this.editGrade = {
      classId: grade.classId,
      subject: grade.subject,
      score: grade.score
    };
  
    if (grade.classId) {
      this.editSubjectsLoading = true;
      this.gradeService.getSubjectsByClass(grade.classId).subscribe({
        next: (subjects) => {
          this.editSubjects = subjects;
          this.editSubjectsLoading = false;
        },
        error: (err) => {
          console.error('Error cargando materias de la clase:', err);
          this.editSubjects = [];
          this.editSubjectsLoading = false;
        }
      });
    }
  }
  
  

  onEditClassSelected(classId: number | null) {
    if (classId !== null) {
      this.editSubjectsLoading = true;
      this.editGrade.subject = ''; // Limpiar el campo de materia
  
      this.gradeService.getSubjectsByClass(classId).subscribe({
        next: (subjects) => {
          this.editSubjects = subjects;
          this.editSubjectsLoading = false;
        },
        error: (err) => {
          console.error('Error cargando materias de la clase:', err);
          this.editSubjects = [];
          this.editSubjectsLoading = false;
        }
      });
    } else {
      this.editSubjects = [];
      this.editGrade.subject = '';
    }
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
    this.editGrade = {classId: null, subject: '', score: null };
  }

  deleteGrade(id: number) {
    if (confirm('¿Eliminar calificación?')) {
      this.gradeService.deleteGrade(id).subscribe(() => {
        this.loadGrades();
      });
    }
  }
}
