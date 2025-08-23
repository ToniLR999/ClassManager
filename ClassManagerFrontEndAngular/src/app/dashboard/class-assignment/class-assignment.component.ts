import { Component, OnInit } from '@angular/core';
import { forkJoin } from 'rxjs';
import { ClassAssignmentService } from 'src/app/dashboard/class-assignment/class-assignment.service';
import { ClassService } from 'src/app/dashboard/classes/class.service';

@Component({
  selector: 'app-class-assignment',
  templateUrl: './class-assignment.component.html',
  styleUrls: ['./class-assignment.component.css']
})
export class ClassAssignmentComponent implements OnInit {
  classes: any[] = [];
  students: any[] = [];
  selectedClassId: number | null = null;
  selectedStudents: number[] = [];
  assignedStudents: any[] = [];

  constructor(
    private assignmentService: ClassAssignmentService,
    private classService: ClassService
  ) {}

  ngOnInit(): void {
    this.classService.getAllClasses().subscribe(data => {
      this.classes = data;
    });

    this.assignmentService.getAllStudents().subscribe(data => {
      this.students = data;
    });
  }

  onClassSelect() {
    if (this.selectedClassId) {
      this.assignmentService.getStudentsByClassId(this.selectedClassId).subscribe(data => {
        this.assignedStudents = data;
        this.selectedStudents = [];
      });
    }
  }
  assignStudents() {
    const classId = this.selectedClassId;
  
    if (classId !== null && this.selectedStudents.length > 0) {
      const requests = this.selectedStudents.map(studentId =>
        this.assignmentService.assignStudentToClass(classId, studentId)
      );
  
      forkJoin(requests).subscribe({
        next: () => this.onClassSelect(),
        error: err => console.error('Error asignando estudiantes:', err)
      });
    }
  }

  removeStudent(studentId: number) {
    if (this.selectedClassId) {
      this.assignmentService.removeStudentFromClass(this.selectedClassId, studentId)
        .subscribe(() => this.onClassSelect());
    }
  }
}
