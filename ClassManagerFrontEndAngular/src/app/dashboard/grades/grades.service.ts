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

  getGradesByStudentId(studentId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/student/${studentId}`);
  }

  createGrade(gradeData: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, gradeData);
  }

  updateGrade(id: number, gradeData: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, gradeData);
  }

  deleteGrade(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }
}
