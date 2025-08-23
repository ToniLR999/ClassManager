import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaintenanceInfo } from './maintenance-info.model';

@Component({
  selector: 'app-info',
  templateUrl: './maintenance-info.component.html',
  styleUrls: ['./maintenance-info.component.css']
})
export class InfoComponent implements OnInit, OnDestroy {
  maintenanceInfo: MaintenanceInfo | null = null;
  isLoading = true; 
  error = false;
  private interval: any;

  ngOnInit() {
    this.loadMaintenanceInfo();
    // Actualizar cada 3 minutos
    this.interval = setInterval(() => this.loadMaintenanceInfo(), 180000);
  }

  ngOnDestroy() {
    if (this.interval) {
      clearInterval(this.interval);
    }
  }

  loadMaintenanceInfo() {
    this.isLoading = true;
    this.error = false;

    try {
      const info = this.computeLocalMaintenanceInfo();
      this.maintenanceInfo = info;
      this.isLoading = false;
    } catch (e) {
      console.error('Error computing maintenance info:', e);
      this.error = true;
      this.isLoading = false;
    }
  }

  private computeLocalMaintenanceInfo(): MaintenanceInfo {
    const now = new Date();
    const day = now.getDay(); // 0=Domingo, 1=Lunes, ... 6=Sábado
    const hour = now.getHours();

    const isWeekday = day >= 1 && day <= 5;
    const withinHours = hour >= 10 && hour < 19; // 10:00-19:00
    const active = isWeekday && withinHours;

    return {
      status: active ? 'UP' : 'DOWN',
      schedule: 'Lun-Vie 10:00-19:00',
      nextStart: this.getNextStartTime(active, day, hour),
      scheduleStatus: active ? 'ACTIVO' : 'INACTIVO',
      message: this.generateMessage(active, day, hour)
    };
  }

  private getNextStartTime(active: boolean, day: number, hour: number): string {
    if (active) return 'Disponible ahora';
    if (day === 6 || day === 0) return 'Lunes 10:00';
    return hour < 10 ? 'Hoy 10:00' : 'Mañana 10:00';
  }

  private generateMessage(active: boolean, day: number, hour: number): string {
    if (active) return 'La aplicación está funcionando normalmente.';
    if (day === 6 || day === 0) return 'La aplicación está cerrada durante el fin de semana.';
    if (hour < 10) return 'La aplicación estará disponible hoy a las 10:00.';
    return 'La aplicación estará disponible mañana a las 10:00.';
  }

  refreshInfo() {
    this.loadMaintenanceInfo();
  }
}