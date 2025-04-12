// src/app/app-routing.module.ts
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dash-board-component/dash-board-component.component';
import { AuthGuard } from './auth/auth-guard';
import { LoginComponent } from './auth/login-component/login-component.component';
import { RegisterComponent } from './auth/register-component/register-component.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },

  // Ruta protegida solo si estás logueado
  {
    path: 'dashboard',
    component: DashboardComponent,
    canActivate: [AuthGuard]
  },

  // Redirección por defecto si no se pone nada
  { path: '', redirectTo: '/login', pathMatch: 'full' },

  // Redirección si no existe la ruta
  { path: '**', redirectTo: '/login' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}