import { Injectable } from '@angular/core';
import { CanActivate, Router, UrlTree } from '@angular/router';
import { Observable, of } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class MaintenanceGuard implements CanActivate {
  constructor(private router: Router) {}

  canActivate(): Observable<boolean | UrlTree> {
    // ✅ En desarrollo (localhost) siempre permitir acceso
    if (!environment.production) {
      return of(true);
    }

    // ✅ En producción, aplicar lógica de mantenimiento
    const now = new Date();
    const day = now.getDay(); // 0=Domingo, 1=Lunes, ... 6=Sábado
    const hour = now.getHours();

    const isWeekday = day >= 1 && day <= 5;
    const withinHours = hour >= 10 && hour < 19; // 10:00-19:00

    if (isWeekday && withinHours) {
      return of(true);
    }
    
    // ✅ Cambiar ruta de /maintenance a /info
    return of(this.router.parseUrl('/info'));
  }
}


