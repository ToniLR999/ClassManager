import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ClassesComponent } from './dashboard/classes/classes.component';
import { StudentsComponent } from './dashboard/students/students.component';
import { GradesComponent } from './dashboard/grades/grades.component';
import { LoginComponent } from './auth/login-component/login-component.component';
import { RegisterComponent } from './auth/register-component/register-component.component';
import { DashboardComponent } from './dashboard/dashboard-component/dashboard-component.component';
import { AuthGuard } from './auth/auth.guard';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },

  {
    path: 'dashboard',
    component: DashboardComponent,
    canActivate: [AuthGuard], // Asegúrate de que esta ruta esté protegida
    children: [
      { path: '', redirectTo: '/classes', pathMatch: 'full' }, // Ruta por defecto
      { path: 'classes', component: ClassesComponent },
      { path: 'students', component: StudentsComponent },
      { path: 'grades', component: GradesComponent }
    ]
  },

  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: '**', redirectTo: '/login' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}