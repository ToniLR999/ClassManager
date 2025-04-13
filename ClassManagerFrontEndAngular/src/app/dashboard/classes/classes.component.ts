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
}
