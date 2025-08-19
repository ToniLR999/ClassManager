import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/auth/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard-component.component.html',
  styleUrls: ['./dashboard-component.component.css']
})
export class DashboardComponent implements OnInit {
  user$ = this.authService.currentUser$;
  constructor(public authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    // Solo verificar autenticación sin hacer llamadas HTTP automáticas
    if (this.authService.isAuthenticated()) {
      const user = this.authService.getCurrentUser();
      console.log('Usuario en dashboard:', user);
    } else {
      console.log('No hay usuario autenticado en dashboard');
    }
  }

  

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}