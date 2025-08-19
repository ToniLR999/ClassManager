import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { MaintenanceInfo } from './maintenance-info.model';

@Component({
  selector: 'app-maintenance-info',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './maintenance-info.component.html',
  styleUrls: ['./maintenance-info.component.css']
})
export class MaintenanceInfoComponent implements OnInit, OnDestroy {
  maintenanceInfo: MaintenanceInfo | null = null;
  isLoading = true; 
  error = false;
  debugInfo: any = null;
  private interval: any;

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.loadMaintenanceInfo();
    // Actualizar cada 5 minutos
    this.interval = setInterval(() => this.loadMaintenanceInfo(), 300000);
  }

  ngOnDestroy() {
    if (this.interval) {
      clearInterval(this.interval);
    }
  }

  loadMaintenanceInfo() {
    this.isLoading = true;
    this.error = false;

    console.log('MaintenanceInfo: Cargando información del sistema...');

    this.http.get(`${environment.apiUrl}/api/system/status`)
      .subscribe({
        next: (response: any) => {
          console.log('MaintenanceInfo: Respuesta del servidor:', response);
          this.debugInfo = response;
          
          this.maintenanceInfo = {
            status: response.active ? 'UP' : 'DOWN',
            schedule: 'L-V 10:00-19:00 (Europe/Madrid)',
            nextStart: 'N/A',
            scheduleStatus: response.active ? 'ACTIVO' : 'INACTIVO',
            message: this.generateMessage(response)
          };
          
          console.log('MaintenanceInfo: Estado procesado:', this.maintenanceInfo);
          this.isLoading = false;
        },
        error: (err) => {
          console.error('MaintenanceInfo: Error loading maintenance info:', err);
          this.error = true;
          this.isLoading = false;
        }
      });
  }

  private generateMessage(response: any): string {
    console.log('MaintenanceInfo: Generando mensaje para:', response);
    
    if (response.active) {
      return 'La aplicación está funcionando normalmente.';
    }

    if (response.isProduction === false) {
      return 'La aplicación está en modo desarrollo y debería estar activa.';
    }

    if (response.scheduleStatus === 'INACTIVO') {
      return 'La aplicación está temporalmente cerrada por horario de trabajo.';
    }

    if (response.schedule && response.schedule.includes('Fin de semana')) {
      return 'La aplicación está cerrada durante el fin de semana.';
    }

    if (response.nextStart && response.nextStart.includes('Lunes')) {
      return 'La aplicación estará disponible el próximo lunes a las 10:00.';
    }

    return 'La aplicación está temporalmente no disponible.';
  }

  getStatusClass(): string {
    if (!this.maintenanceInfo) return '';
    
    if (this.maintenanceInfo.status === 'UP' && this.maintenanceInfo.scheduleStatus === 'ACTIVO') {
      return 'status-active';
    }
    
    return 'status-maintenance';
  }

  getCurrentTime(): string {
    return new Date().toLocaleString('es-ES', {
      weekday: 'long',
      year: 'numeric',
      month: 'long',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  }

  refreshInfo() {
    this.loadMaintenanceInfo();
  }
}