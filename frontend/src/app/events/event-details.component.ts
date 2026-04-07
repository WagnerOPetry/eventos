import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EventsService } from './events.service';

@Component({
  template: `
  <div *ngIf="!loading">
    <h3>{{event?.title}}</h3>
    <p>{{event?.description}}</p>
    <p><strong>Quando:</strong> {{event?.eventDateTime | date:'full'}}</p>
    <p><strong>Onde:</strong> {{event?.location}}</p>
    <a class="btn btn-secondary" routerLink="/events">Voltar</a>
  </div>
  <div *ngIf="loading">Carregando...</div>
  `
})
export class EventDetailsComponent implements OnInit {
  event: any = null;
  loading = true;

  constructor(private route: ActivatedRoute, private service: EventsService) {}

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const id = Number(params.get('id'));
      this.service.get(id).subscribe(e => { this.event = e; this.loading = false; });
    });
  }
}
