import { Component } from '@angular/core';
import { UntypedFormBuilder, Validators } from '@angular/forms';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-register',
  templateUrl: './register-component.component.html',
  styleUrls: ['./register-component.component.css']
})
export class RegisterComponent {
  loading = false;

  registerForm = this.fb.group({
    name: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    password: ['', Validators.required],
    confirmPassword: ['', Validators.required]
  });

  constructor(
    private fb: UntypedFormBuilder,
    private authService: AuthService,
    private router: Router,
    private snackbar: MatSnackBar
  ) {}

  onSubmit(): void {
    if (this.registerForm.invalid) return;

    const { password, confirmPassword } = this.registerForm.value;
    if (password !== confirmPassword) {
      this.snackbar.open('Las contraseñas no coinciden', 'Cerrar', { duration: 3000 });
      return;
    }

    this.loading = true;
    const data = {
      name: this.registerForm.value.name,
      email: this.registerForm.value.email,
      password
    };

    this.authService.register(data).subscribe({
      next: () => {
        this.snackbar.open('Cuenta creada con éxito', 'Cerrar', { duration: 3000 });
        this.router.navigate(['/login']);
      },
      error: () => {
        this.snackbar.open('Error al registrar usuario', 'Cerrar', { duration: 3000 });
        this.loading = false;
      }
    });
  }
}