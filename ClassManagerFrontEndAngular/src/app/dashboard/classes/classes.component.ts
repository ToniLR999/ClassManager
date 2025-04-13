import { Component, OnInit } from '@angular/core';
import { ClassService } from 'src/app/dashboard/classes/class.service';

@Component({
  selector: 'app-classes',
  templateUrl: './classes.component.html',
  styleUrls: ['./classes.component.scss']
})
export class ClassesComponent implements OnInit {
  classes: any[] = [];
  filteredClasses: any[] = [];
  searchTerm = '';

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

  newClass = {
    name: '',
    description: '',
    teacherId: null, // o lo que uses para asignar al profesor actual
  };
  
  createClass() {
    if (!this.newClass.name || !this.newClass.description) {
      alert('Por favor, completa todos los campos');
      return;
    }
  
    this.classService.createClass(this.newClass).subscribe({
      next: () => {
        this.newClass = { name: '', description: '', teacherId: null };
        this.loadClasses();
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

  
}
