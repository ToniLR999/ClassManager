import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';


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
import { FormsModule } from '@angular/forms';
import { ClassAssignmentComponent } from './dashboard/class-assignment/class-assignment.component';
import { AttendanceComponent } from './dashboard/attendance/attendance.component';
import { ProfileComponent } from './dashboard/profile/profile.component';
import { AttendanceHistoryComponent } from './dashboard/attendance-history/attendance-history.component';
import { AuthInterceptor } from './auth/auth-interceptor';
import { MatSelectModule } from '@angular/material/select';


@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    StudentsComponent,
    GradesComponent,
    ClassesComponent,
    ClassAssignmentComponent,
    AttendanceComponent,
    ProfileComponent,
    AttendanceHistoryComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    AuthModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSnackBarModule,
    MatSelectModule
  ],
  providers: [AuthModule,   {
    provide: HTTP_INTERCEPTORS,
    useClass: AuthInterceptor,
    multi: true
  }],
  bootstrap: [AppComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppModule { }