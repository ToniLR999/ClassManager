import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { LoginComponent } from './auth/login-component/login-component.component';
import { RegisterComponent } from './auth/register-component/register-component.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AuthInterceptor } from './auth/auth-interceptor';
import { DashboardComponent } from './dashboard/dash-board-component/dash-board-component.component';
import { AuthModule } from './auth/auth-module';
import { ClassesComponent } from './dashboard/classes/classes.component';
import { StudentsComponent } from './dashboard/students/students.component';
import { GradesComponent } from './dashboard/grades/grades.component';


@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    ClassesComponent,
    StudentsComponent,
    GradesComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    AuthModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
