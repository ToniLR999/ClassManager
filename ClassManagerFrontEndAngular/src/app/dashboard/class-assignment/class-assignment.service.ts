import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ClassAssignmentService {
  private baseUrl = `${environment.apiUrl}/classes`;

  constructor(private http: HttpClient) {}

  getAllClasses(): Observable<any[]> {
    return this.http.get<any[]>(this.baseUrl);
  }

  getAllStudents(): Observable<any[]> {
    return this.http.get<any[]>(`${environment.apiUrl}/students`);
  }

  getStudentsByClassId(classId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/${classId}/students`);
  }

  assignStudentsToClass(classId: number, studentIds: number[]): Observable<any> {
    return this.http.post(`${this.baseUrl}/${classId}/students`, studentIds);
  }

  removeStudentFromClass(classId: number, studentId: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${classId}/students/${studentId}`);
  }
}
