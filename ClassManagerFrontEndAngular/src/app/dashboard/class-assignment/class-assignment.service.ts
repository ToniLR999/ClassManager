import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ClassAssignmentService {
  private ClassUrl = `${environment.apiUrl}/classes`;

  private StudentsUrl = `${environment.apiUrl}/students`;


  constructor(private http: HttpClient) {}

  getAllClasses(): Observable<any[]> {
    return this.http.get<any[]>(`${this.ClassUrl}/getClasses`);
  }

  getClassesPaginated(page: number, size: number): Observable<any[]> {

        const params = new HttpParams()
        .set('page', page)
        .set('size', size);
    return this.http.get<any[]>(`${this.ClassUrl}/getClasses/paginated`, {params});
  }

  getAllStudents(): Observable<any[]> {
    return this.http.get<any[]>(`${this.StudentsUrl}/all`);
  }

  getStudentsByClassId(classId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.StudentsUrl}/by-class/${classId}`);
  }

  assignStudentToClass(classId: number, studentId: number): Observable<any> {
    return this.http.post(`${this.StudentsUrl}/${studentId}/assign/${classId}`, null);
  }

 //Igual falta metodo para añadir varios students de una a una class 

  removeStudentFromClass(classId: number, studentId: number): Observable<any> {
    return this.http.delete(`${this.StudentsUrl}/${studentId}/delete/${classId}`);
  }
}
