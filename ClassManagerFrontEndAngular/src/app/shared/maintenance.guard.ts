import { Injectable } from '@angular/core';
import { CanActivate, Router, UrlTree } from '@angular/router';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class MaintenanceGuard implements CanActivate {
  constructor(private http: HttpClient, private router: Router) {}

  canActivate(): Observable<boolean | UrlTree> {
    console.log('MaintenanceGuard: Verificando estado del sistema...');
    
    return this.http.get<{ active: boolean; maintenance: boolean; isProduction: boolean }>(`${environment.apiUrl}/api/system/status`).pipe(
      tap(response => {
        console.log('MaintenanceGuard: Respuesta del servidor:', response);
      }),
      map((res) => {
        console.log('MaintenanceGuard: active =', res.active, 'isProduction =', res.isProduction);
        
        if (res && res.active) {
          console.log('MaintenanceGuard: Aplicación activa, permitiendo acceso');
          return true;
        }
        
        console.log('MaintenanceGuard: Aplicación inactiva, redirigiendo a mantenimiento');
        return this.router.parseUrl('/maintenance');
      }),
      catchError((error) => {
        console.error('MaintenanceGuard: Error al verificar estado:', error);
        console.log('MaintenanceGuard: Redirigiendo a mantenimiento por error');
        return of(this.router.parseUrl('/maintenance'));
      })
    );
  }
}


