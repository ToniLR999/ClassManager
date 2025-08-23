import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {
  changePasswordForm: FormGroup;
  loading = false;
  message = '';
  messageType: 'success' | 'error' = 'error';
  token: string = '';

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.changePasswordForm = this.fb.group({
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required]]
    }, { validators: this.passwordMatchValidator });
  }

  ngOnInit() {
    // Obtener el token de la URL
    this.token = this.route.snapshot.queryParams['token'];
    if (!this.token) {
      this.message = 'Token de reset no válido';
      this.messageType = 'error';
    }
  }

  passwordMatchValidator(form: FormGroup) {
    const password = form.get('password');
    const confirmPassword = form.get('confirmPassword');
    
    if (password && confirmPassword && password.value !== confirmPassword.value) {
      confirmPassword.setErrors({ passwordMismatch: true });
      return { passwordMismatch: true };
    }
    
    return null;
  }

  onSubmit() {
    if (this.changePasswordForm.valid && this.token) {
      this.loading = true;
      
      const { password } = this.changePasswordForm.value;
      
      this.http.post<{message: string, status: string}>(`${environment.apiUrl}/password/reset`, {
        token: this.token,
        newPassword: password
      }).subscribe({
        next: (response) => {
          this.messageType = 'success';
          this.message = response.message || 'Contraseña cambiada exitosamente';
          
          // Redirigir al login después de 2 segundos
          setTimeout(() => {
            this.router.navigate(['/login']);
          }, 2000);
        },
        error: (error) => {
          this.messageType = 'error';
          this.message = 'Error al cambiar la contraseña. El token puede haber expirado.';
          console.error('Error al cambiar password:', error);
        },
        complete: () => {
          this.loading = false;
        }
      });
    }
  }
}
