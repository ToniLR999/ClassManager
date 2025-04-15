import { Component } from '@angular/core';
import { Validators } from '@angular/forms';
import { UntypedFormBuilder } from '@angular/forms';

import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-login',
  templateUrl: './login-component.component.html',
  styleUrls: ['./login-component.component.css']
})
export class LoginComponent {
  loading = false;

  loginForm = this.fb.group({
    username: ['', Validators.required],
    password: ['', Validators.required]
  });

  constructor(
    private fb: UntypedFormBuilder,
    private authService: AuthService,
    private router: Router,
    private snackbar: MatSnackBar
  ) {}

  onSubmit(): void {
    console.log(this.loginForm.value);

    if (this.loginForm.invalid) return;

    this.loading = true;
    
    const { username, password } = this.loginForm.value;

    this.authService.login(username!, password!).subscribe({
      next: () => {
        this.router.navigate(['/dashboard']);
        this.snackbar.open('Inicio de sesión exitoso', 'Cerrar', { duration: 3000 });
      },
      error: (err) => {
        console.error('Error de login:', err);
        this.snackbar.open('Email o contraseña incorrectos', 'Cerrar', { duration: 3000 });
        this.loading = false;
      }
    });
  }
}
