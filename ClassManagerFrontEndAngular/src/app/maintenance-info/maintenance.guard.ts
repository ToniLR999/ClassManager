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
    
    return this.http.get<{ active: boolean; maintenance: boolean; isProduction: boolean }>(`${environment.apiUrl}/api/system/status`).pipe(
      tap(response => {
      }),
      map((res) => {
        
        if (res && res.active) {
          return true;
        }
        
        return this.router.parseUrl('/maintenance');
      }),
      catchError((error) => {
        return of(this.router.parseUrl('/maintenance'));
      })
    );
  }
}


