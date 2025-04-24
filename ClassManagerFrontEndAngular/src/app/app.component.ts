import { Component, OnInit } from '@angular/core';
import { AuthService } from './auth/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'ClassManager';

  constructor(private authService: AuthService) {}


  ngOnInit(): void {
    if (this.authService.isAuthenticated()) {
      this.authService.refreshCurrentUser();
      const user = this.authService.currentUser$;
      console.log(this.authService.currentUser$);
    }
  }
}
