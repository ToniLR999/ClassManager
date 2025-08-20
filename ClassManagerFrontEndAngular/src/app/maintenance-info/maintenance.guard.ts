import { Injectable } from '@angular/core';
import { CanActivate, Router, UrlTree } from '@angular/router';
import { Observable, of } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class MaintenanceGuard implements CanActivate {
  constructor(private router: Router) {}

  canActivate(): Observable<boolean | UrlTree> {
    const now = new Date();
    const day = now.getDay(); // 0=Domingo, 1=Lunes, ... 6=Sábado
    const hour = now.getHours();

    const isWeekday = day >= 1 && day <= 5;
    const withinHours = hour >= 10 && hour < 19; // 10:00-19:00

    if (isWeekday && withinHours) {
      return of(true);
    }
    return of(this.router.parseUrl('/maintenance'));
  }
}


