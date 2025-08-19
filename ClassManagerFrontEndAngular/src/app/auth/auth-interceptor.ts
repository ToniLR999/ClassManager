import { Injectable } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthService } from './auth.service';
import { Router } from '@angular/router';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.authService.getToken();

    // No agregar el token si la URL incluye /auth
    if (token && !req.url.includes('/auth')) {
      const cloned = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
      return next.handle(cloned).pipe(
        catchError((error: HttpErrorResponse) => {
          if (error.status === 401 || error.status === 403) {
            // Token expirado o inválido
            if (req.url.includes('/users/me') || req.url.includes('/api/')) {
              // Limpiar token y redirigir
              this.authService.logout();
              
              // No mostrar error en consola para endpoints protegidos
              if (req.url.includes('/users/me')) {
                console.warn('Token expirado o inválido, redirigiendo a login');
                return new Observable(); // Retornar observable vacío
              }
            }
          }
          
          return throwError(() => error);
        })
      );
    }

    return next.handle(req);
  }
}