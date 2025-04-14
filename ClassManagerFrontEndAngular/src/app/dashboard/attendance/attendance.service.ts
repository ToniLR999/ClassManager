import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "src/environments/environment";

@Injectable({
  providedIn: 'root'
})
export class AttendanceService {
  private apiUrl = `${environment.apiUrl}/attendance`;

  constructor(private http: HttpClient) {}

  registerAttendance(data: { studentId: number, classId: number, date: string }) {
    return this.http.post(`${this.apiUrl}`, data);
  }

  getAttendanceByClass(classId: number) {
    return this.http.get(`${this.apiUrl}/class/${classId}`);
  }

  getAttendanceByStudent(studentId: number) {
    return this.http.get(`${this.apiUrl}/student/${studentId}`);
  }
}
