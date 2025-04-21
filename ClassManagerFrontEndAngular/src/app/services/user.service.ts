import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private api = `${environment.apiUrl}/users`;

  constructor(private http: HttpClient) {}

  // Obtener datos del usuario logueado
  getMe(): Observable<any> {
    return this.http.get(`${this.api}/me`);
  }

  getUsersPaginated(page: number, size: number): Observable<any> {
        const params = new HttpParams()
        .set('page', page)
        .set('size', size);
    return this.http.get(this.api,{params});
  }

  // Actualizar el perfil del usuario actual NO IMPLEMENTADO EN EL BACKEND
  updateSelf(data: { name?: string; password?: string }): Observable<any> {
    return this.http.put(`${this.api}/me`, data);
  }
}
