import { Component, OnInit } from '@angular/core';
import { ClassService } from 'src/app/dashboard/classes/class.service';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { MatChipInputEvent } from '@angular/material/chips';


@Component({
  selector: 'app-classes',
  templateUrl: './classes.component.html',
  styleUrls: ['./classes.component.css']
})
export class ClassesComponent implements OnInit {
  readonly addOnBlur = true;

  classes: any[] = [];
  filteredClasses: any[] = [];
  searchTerm = '';
  readonly separatorKeysCodes: number[] = [ENTER, COMMA];
  subjects: string[] = [];

  selectedDate: Date | null = null;
  selectedTime: string = '';

  constructor(private classService: ClassService) {}

  ngOnInit(): void {
    this.loadClasses();
  }

  loadClasses() {
    this.classService.getAllClasses().subscribe((data) => {
      this.classes = data;
      this.filteredClasses = data;
    });
  }

  onSearchChange() {
    const term = this.searchTerm.toLowerCase();
    this.filteredClasses = this.classes.filter((c) =>
      c.name.toLowerCase().includes(term)
    );
  }

  newClass: { 
    name: string; 
    description: string; 
    subjects: string[]; 
    schedule: string; 
    teacherId: number | null; 
  } = {
    name: '',
    description: '',
    subjects: [],
    schedule: '',
    teacherId: null,
  };
  
  createClass() {
    if (!this.newClass.name || !this.newClass.description) {
      alert('Por favor, completa todos los campos');
      return;
    }

    const [hours, minutes] = this.selectedTime.split(':').map(Number);
    const dateWithTime = new Date(this.selectedDate!);
    dateWithTime.setHours(hours);
    dateWithTime.setMinutes(minutes);
    dateWithTime.setSeconds(0);
    dateWithTime.setMilliseconds(0);

    this.newClass.schedule = new Date().toISOString(); 

    this.newClass.subjects = this.subjects;

    this.classService.createClass(this.newClass).subscribe({
      next: () => {
        this.newClass = { name: '', description: '',subjects: [],  schedule: ' ', teacherId: null };
        this.loadClasses();
        this.selectedDate = null;
        this.selectedTime = '';
        this.subjects = [];

      },
      error: () => {
        alert('Error al crear la clase');
      },
    });
  }

  editMode = false;
editClassId: number | null = null;

editClass = {
  name: '',
  description: '',
  teacherId: null
};

startEdit(clase: any) {
  this.editMode = true;
  this.editClassId = clase.id;
  this.editClass = {
    name: clase.name,
    description: clase.description,
    teacherId: clase.teacherId || null
  };
}

cancelEdit() {
  this.editMode = false;
  this.editClassId = null;
  this.editClass = { name: '', description: '', teacherId: null };
}

updateClass() {
  if (!this.editClassId) return;

  this.classService.updateClass(this.editClassId, this.editClass).subscribe({
    next: () => {
      this.cancelEdit();
      this.loadClasses();
    },
    error: () => alert('Error al actualizar la clase')
  });
}

deleteClass(id: number) {
  if (!confirm('¿Seguro que quieres eliminar esta clase?')) return;

  this.classService.deleteClass(id).subscribe({
    next: () => this.loadClasses(),
    error: () => alert('Error al eliminar la clase')
  });
}

addSubject(event: MatChipInputEvent): void {
  const value = (event.value || '').trim();
  if (value) {
    this.subjects.push(value);
  }
  event.chipInput!.clear();

}

removeSubject(subject: string): void {
  const index = this.subjects.indexOf(subject);
  if (index >= 0) {
    this.subjects.splice(index, 1);
  }
}

}
