import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "src/environments/environment";

@Injectable({
  providedIn: 'root'
})
export class AttendanceService {
  private apiUrl = `${environment.apiUrl}/attendance`;

  constructor(private http: HttpClient) {}

  registerAttendance(data: { studentId: number, classId: number}) {
    const params = new HttpParams()
      .set('studentId', data.studentId.toString())
      .set('classId', data.classId.toString());

    return this.http.post(`${this.apiUrl}/mark`, null, { params }); 
  }

  getAttendanceByClass(classId: number) {
    return this.http.get(`${this.apiUrl}/class/${classId}`);
  }
}
