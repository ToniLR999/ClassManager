import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';

interface LoginResponse {
  token: string;
  role: string;
  user: string;

}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private currentUserSubject = new BehaviorSubject<any>(null);
  public currentUser$ = this.currentUserSubject.asObservable();

  private apiUrl = `${environment.apiUrl}/auth`;

  private userApiUrl = `${environment.apiUrl}/users`;


  constructor(private http: HttpClient, private router: Router) {}

  login(username: string, password: string): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, { username, password }).pipe(
      tap(res => {
        localStorage.setItem('token', res.token);
        localStorage.setItem('role', res.role);
        localStorage.setItem('user',  JSON.stringify({ username: res.user }));
      })
    );
  }

  register(data: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, data);
  }

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    localStorage.removeItem('user');
    this.router.navigate(['/login']);
  }

  isAuthenticated(): boolean {
    return !!localStorage.getItem('token');
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getUserRole(): string | null {
    return localStorage.getItem('role');
  }

  getCurrentUser() {
    // Usar datos del localStorage en lugar de hacer llamada HTTP
    const user = localStorage.getItem('user');
    return user ? JSON.parse(user) : null;
  }

  // Método para obtener datos del backend (solo cuando sea necesario)
  fetchCurrentUserFromBackend(): Observable<any> {
    if (!this.isAuthenticated()) {
      return new Observable(subscriber => {
        subscriber.error('No authenticated');
        subscriber.complete();
      });
    }
    return this.http.get<any>(`${this.userApiUrl}/me`);
  }

  // Esto se llama desde el perfil para refrescar los datos
  refreshCurrentUser() {
    // Solo actualizar si hay un token válido
    if (this.isAuthenticated()) {
      this.fetchCurrentUserFromBackend().subscribe({
        next: (user) => {
          console.log('Usuario actualizado desde backend:', user);
          this.currentUserSubject.next(user);
        },
        error: (error) => {
          console.warn('No se pudo obtener usuario del backend, usando localStorage:', error);
          const user = this.getCurrentUser();
          this.currentUserSubject.next(user);
        }
      });
    } else {
      // Si no hay token, usar datos del localStorage
      const user = this.getCurrentUser();
      this.currentUserSubject.next(user);
    }
  }

}