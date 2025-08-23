import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-password-reset',
  templateUrl: './password-reset.component.html',
  styleUrls: ['./password-reset.component.css']
})
export class PasswordResetComponent {
  resetForm: FormGroup;
  loading = false;
  message = '';
  messageType: 'success' | 'error' = 'success';

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private http: HttpClient
  ) {
    this.resetForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]]
    });
  }

  onSubmit() {
    if (this.resetForm.valid) {
      this.loading = true;
      this.message = '';
      
      const email = this.resetForm.get('email')?.value;
      
      this.http.post<{message: string, status: string}>(`${environment.apiUrl}/password/forgot`, null, {
        params: { email }
      }).subscribe({
        next: (response) => {
          this.messageType = 'success';
          this.message = response.message || 'Se ha enviado un correo para restablecer tu contraseña. Revisa tu bandeja de entrada.';
          this.resetForm.reset();
        },
        error: (error) => {
          this.messageType = 'error';
          this.message = 'Error al enviar el correo. Verifica que el email sea correcto.';
          console.error('Error en reset de password:', error);
        },
        complete: () => {
          this.loading = false;
        }
      });
    }
  }

  goToLogin() {
    this.router.navigate(['/login']);
  }
}
