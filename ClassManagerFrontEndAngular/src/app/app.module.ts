import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AuthModule } from './auth/auth-module';
import { DashboardComponent } from './dashboard/dashboard-component/dashboard-component.component';
import { StudentsComponent } from './dashboard/students/students.component';
import { GradesComponent } from './dashboard/grades/grades.component';
import { ClassesComponent } from './dashboard/classes/classes.component';
import { ReactiveFormsModule } from '@angular/forms';

import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

import { MatSnackBarModule } from '@angular/material/snack-bar'; 

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    StudentsComponent,
    GradesComponent,
    ClassesComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    AuthModule,
    HttpClientModule,
    ReactiveFormsModule,
    
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSnackBarModule
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }