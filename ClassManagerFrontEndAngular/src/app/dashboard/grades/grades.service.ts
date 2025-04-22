import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class GradeService {
  private apiUrl = `${environment.apiUrl}/grades`;

  constructor(private http: HttpClient) {}

  getAllGrades(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  getGradesByStudent(studentId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/student/${studentId}`);
  }

  getGradesByClass(classId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/class/${classId}`);
  }

  createGrade(gradeData: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/register`, gradeData);
  }

  //Falta pasarle el body con los datos nuevos
  updateGrade(gradeId: number, newGrade: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${gradeId}`,newGrade);
  }

  deleteGrade(gradeId: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${gradeId}`);
  }
}
