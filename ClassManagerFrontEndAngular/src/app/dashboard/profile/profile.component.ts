import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from 'src/app/auth/auth.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  user: any = {};
  newPassword: string = '';

  constructor(
    private authService: AuthService,
    private userService: UserService,
    private snack: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.user = { ...this.authService.currentUser! };
  }

  updateProfile() {
    const payload = {
      name: this.user.name,
      password: this.newPassword || undefined
    };

    this.userService.updateSelf(payload).subscribe({
      next: (res) => {
        this.snack.open('Perfil actualizado', 'Cerrar', { duration: 2000 });
        this.authService.refreshCurrentUser();
      },
      error: () => {
        this.snack.open('Error al actualizar', 'Cerrar', { duration: 3000 });
      }
    });
  }
}
