import { Component, OnInit } from '@angular/core';
import { EventsService } from './events.service';
import { Event } from './event.model';

@Component({
  template: `
  <div class="d-flex justify-content-between mb-3">
    <h3>Eventos</h3>
    <a class="btn btn-primary" routerLink="/events/new">Novo evento</a>
  </div>

  <div *ngIf="loading" class="text-center">Carregando...</div>

  <table *ngIf="!loading" class="table table-striped">
    <thead><tr><th>Título</th><th>Data</th><th>Local</th><th></th></tr></thead>
    <tbody>
      <tr *ngFor="let e of events">
        <td><a [routerLink]="['/events', e.id]">{{e.title}}</a></td>
        <td>{{e.eventDateTime | date:'short'}}</td>
        <td>{{e.location}}</td>
        <td>
          <a class="btn btn-sm btn-secondary" [routerLink]="['/events', e.id, 'edit']">Editar</a>
          <button class="btn btn-sm btn-danger ms-2" (click)="remove(e.id)">Excluir</button>
        </td>
      </tr>
    </tbody>
  </table>

  <nav *ngIf="totalPages>1">
    <ul class="pagination">
      <li class="page-item" [class.disabled]="page===0"><a class="page-link" (click)="go(page-1)">Anterior</a></li>
      <li class="page-item" *ngFor="let p of pages" [class.active]="p===page"><a class="page-link" (click)="go(p)">{{p+1}}</a></li>
      <li class="page-item" [class.disabled]="page+1>=totalPages"><a class="page-link" (click)="go(page+1)">Próximo</a></li>
    </ul>
  </nav>
  `
})
export class EventsListComponent implements OnInit {
  events: Event[] = [];
  page = 0;
  size = 10;
  totalPages = 0;
  pages: number[] = [];
  loading = false;

  constructor(private service: EventsService) {}

  ngOnInit() {
    this.load();
  }

  load() {
    this.loading = true;
    this.service.list(this.page, this.size).subscribe(res => {
      this.events = res.content || res;
      this.totalPages = res.totalPages || 1;
      this.pages = Array.from({length: this.totalPages}, (_, i) => i);
      this.loading = false;
    }, () => this.loading = false);
  }

  go(p: number) {
    if (p<0 || p>=this.totalPages) return;
    this.page = p;
    this.load();
  }

  remove(id?: number) {
    if (!id || !confirm('Confirma exclusão?')) return;
    this.service.delete(id).subscribe(() => this.load());
  }
}
