import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ClassService {
  private apiUrl = `${environment.apiUrl}/classes`;

  constructor(private http: HttpClient) {}

  // Obtener todas las clases
  getAllClasses(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  // Obtener clase por ID
  getClassById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  // Crear nueva clase
  createClass(classData: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, classData);
  }

  // Actualizar clase existente
  updateClass(id: number, classData: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, classData);
  }

  // Eliminar clase
  deleteClass(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }
}
