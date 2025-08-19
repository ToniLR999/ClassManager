import { Component, OnInit } from '@angular/core';
import { AuthService } from './auth/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'ClassManager';

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    // Solo verificar autenticación sin hacer llamadas HTTP automáticas
    if (this.authService.isAuthenticated()) {
      // Usar datos del localStorage en lugar de hacer llamada HTTP
      const user = this.authService.getCurrentUser();
      console.log('Usuario autenticado:', user);
    } else {
      console.log('No hay usuario autenticado');
    }
  }
}
