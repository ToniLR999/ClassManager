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
  editSubjects: string[] = [];

  selectedDate: Date | null = null;
  selectedTime: string = '';
  editSelectedDate: Date | null = null;
  editSelectedTime: string = '';

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

    // Ajustamos la diferencia horaria
    const timezoneOffset = dateWithTime.getTimezoneOffset(); // en minutos
    dateWithTime.setMinutes(dateWithTime.getMinutes() - timezoneOffset);


    this.newClass.schedule = dateWithTime.toISOString(); 

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

editClass: { 
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

startEdit(clase: any) {
  this.editMode = true;
  this.editClassId = clase.id;
  this.editClass = {
    name: clase.name,
    description: clase.description,
    subjects: clase.subjects ? [...clase.subjects] : [],
    schedule: clase.schedule, 
    teacherId: clase.teacherId || null
  };

  if (clase.schedule) {
    const scheduleDate = new Date(clase.schedule);
    this.editSelectedDate = scheduleDate;
    this.editSelectedTime = scheduleDate.toISOString().substring(11, 16); // HH:mm
  } else {
    this.editSelectedDate = null;
    this.editSelectedTime = '';
  }

  this.editSubjects = clase.subjects ? [...clase.subjects] : [];

   // Extraer la hora y minuto de la fecha para el campo time
   this.editSelectedDate = this.editClass.schedule ? new Date(this.editClass.schedule) : null;
   this.editSelectedTime = this.editSelectedDate ? this.editSelectedDate.toISOString().substr(11, 5) : ''; // Formato HH:mm
}

cancelEdit() {
  this.editMode = false;
  this.editClassId = null;
  this.editClass = { name: '', description: '', teacherId: null, schedule: '', subjects: [] };
  this.editSelectedDate = null;
  this.editSelectedTime = '';
  this.editSubjects = [];

}

trackSubject(index: number, item: string): string {
  return item;
}


updateClass() {
  if (!this.editClassId) return;


  if (this.editSelectedDate && this.editSelectedTime) {
    const [hours, minutes] = this.editSelectedTime.split(':').map(Number);
    const dateWithTime = new Date(this.editSelectedDate);
    dateWithTime.setHours(hours);
    dateWithTime.setMinutes(minutes);
    dateWithTime.setSeconds(0);
    dateWithTime.setMilliseconds(0);

        // Ajustamos la diferencia horaria
    const timezoneOffset = dateWithTime.getTimezoneOffset(); // en minutos
    dateWithTime.setMinutes(dateWithTime.getMinutes() - timezoneOffset);

    this.editClass.schedule = dateWithTime.toISOString(); // <---- Ahora seguro correcto
  }
 
  this.editClass.subjects = this.editSubjects;
 
  this.classService.updateClass(this.editClassId, this.editClass).subscribe({
    next: () => {
      this.cancelEdit();
      this.loadClasses();
    },
    error: () => alert('Error al actualizar la clase')
  });
}

addEditSubject(event: MatChipInputEvent): void {
  const value = (event.value || '').trim();
  if (value) {
    this.editSubjects.push(value);
  }
  event.chipInput!.clear();
}

removeEditSubject(subject: string): void {
  const index = this.editSubjects.indexOf(subject);
  if (index >= 0) {
    this.editSubjects.splice(index, 1);
  }
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
