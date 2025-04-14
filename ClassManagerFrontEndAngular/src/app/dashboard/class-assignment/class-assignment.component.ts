import { Component, OnInit } from '@angular/core';
import { ClassAssignmentService } from 'src/app/dashboard/class-assignment/class-assignment.service';

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

  constructor(private assignmentService: ClassAssignmentService) {}

  ngOnInit(): void {
    this.assignmentService.getAllClasses().subscribe(data => {
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
    if (this.selectedClassId && this.selectedStudents.length > 0) {
      this.assignmentService.assignStudentsToClass(this.selectedClassId, this.selectedStudents)
        .subscribe(() => this.onClassSelect());
    }
  }

  removeStudent(studentId: number) {
    if (this.selectedClassId) {
      this.assignmentService.removeStudentFromClass(this.selectedClassId, studentId)
        .subscribe(() => this.onClassSelect());
    }
  }
}
