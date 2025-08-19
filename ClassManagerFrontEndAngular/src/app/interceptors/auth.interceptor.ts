import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private router: Router) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    // Agregar token si existe
    const token = localStorage.getItem('token');
    if (token) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
    }

    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401 || error.status === 403) {
          // Token expirado o inválido
          if (request.url.includes('/users/me') || request.url.includes('/api/')) {
            // Para endpoints protegidos, limpiar token y redirigir
            localStorage.removeItem('token');
            localStorage.removeItem('role');
            localStorage.removeItem('user');
            
            // Solo redirigir si no estamos ya en login
            if (!this.router.url.includes('/login')) {
              this.router.navigate(['/login']);
            }
          }
          
          // No mostrar error en consola para endpoints protegidos
          if (request.url.includes('/users/me')) {
            console.warn('Token expirado o inválido, redirigiendo a login');
            return new Observable(); // Retornar observable vacío
          }
        }
        
        return throwError(() => error);
      })
    );
  }
}
