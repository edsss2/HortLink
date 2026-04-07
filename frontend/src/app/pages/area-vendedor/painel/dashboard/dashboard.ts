import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; // Para @if, @for, etc.

// 1. Importe os componentes do PrimeNG
import { CardModule } from 'primeng/card';
import { ChartModule } from 'primeng/chart';
import { TableModule } from 'primeng/table'; // Para a lista de produto

import { DashboardDTO } from '../../../../models/dto/dashboard.dto';
import { DASHBOARD_MOCK_DATA } from '../../../../models/dto/dashboard.mock';
import { ComercioService } from '../../../../services/comercio-service';
import { UsuarioService } from '../../../../services/usuario-service';

@Component({
  selector: 'app-dashboard',
  imports: [CommonModule, CardModule, ChartModule, TableModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})

export class Dashboard implements OnInit {

  public dados: DashboardDTO | null = null;
  public isLoading = true;

  public chartData: any;
  public chartOptions: any;

  constructor(
    private comercioService: ComercioService,
    private usuarioService: UsuarioService
  ) {}

  ngOnInit(): void {
    // 1. Pegamos o usuário logado
    const usuarioLogado = this.usuarioService.getCurrentUser(); 

    // 2. Verificamos se ele tem um comércio vinculado e chamamos a API
    if (usuarioLogado && usuarioLogado.comercioProfileId) {
      this.carregarDados(usuarioLogado.comercioProfileId);
    } else {
      console.error("ID do comércio não encontrado no usuário logado");
      this.isLoading = false; 
    }
  }

  carregarDados(id: number): void {
    this.isLoading = true;
    
    this.comercioService.getDashboardData(id).subscribe({
      next: (res: DashboardDTO) => {
        this.dados = res;
        // Só configuramos o gráfico DEPOIS que os dados chegam
        this.configurarGrafico(); 
        this.isLoading = false;
      },
      error: (err) => {
        console.error("Erro ao carregar dashboard", err);
        this.isLoading = false;
      }
    });
  }

  configurarGrafico(): void {
    // Se chegou aqui via carregarDados, o 'dados' já está preenchido
    if (!this.dados || !this.dados.vendasUltimos7Dias) return;

    
    
    const labels = this.dados.vendasUltimos7Dias.map(v => v.data);
    const data = this.dados.vendasUltimos7Dias.map(v => v.total);

    this.chartData = {
      labels: labels,
      datasets: [
        {
          label: 'Faturamento Diário',
          data: data,
          backgroundColor: 'rgba(54, 162, 235, 0.2)', 
          borderColor: 'rgb(54, 162, 235)',
          borderWidth: 1,
          fill: true
        }
      ]
    };
    
    this.chartOptions = {

      scales: {
        y: { beginAtZero: true }
      }
    };
  }
}