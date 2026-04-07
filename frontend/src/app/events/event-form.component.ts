import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { EventsService } from './events.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  template: `
  <div class="card">
    <div class="card-body">
      <h5 class="card-title">{{isEdit ? 'Editar' : 'Novo'}} evento</h5>

      <form [formGroup]="form" (ngSubmit)="save()">
        <div class="mb-3">
          <label class="form-label">Título</label>
          <input formControlName="title" class="form-control" />
        </div>
        <div class="mb-3">
          <label class="form-label">Descrição</label>
          <textarea formControlName="description" class="form-control"></textarea>
        </div>
        <div class="mb-3">
          <label class="form-label">Data e hora</label>
          <input type="datetime-local" formControlName="eventDateTime" class="form-control" />
        </div>
        <div class="mb-3">
          <label class="form-label">Local</label>
          <input formControlName="location" class="form-control" />
        </div>

        <button class="btn btn-primary" [disabled]="form.invalid">Salvar</button>
        <a class="btn btn-secondary ms-2" routerLink="/events">Cancelar</a>
      </form>
    </div>
  </div>
  `
})
export class EventFormComponent implements OnInit {
  form = this.fb.group({
    title: ['', [Validators.required, Validators.maxLength(100)]],
    description: ['', [Validators.maxLength(1000)]],
    eventDateTime: ['', [Validators.required]],
    location: ['', [Validators.maxLength(200)]]
  });

  isEdit = false;
  id?: number;

  constructor(private fb: FormBuilder,
              private service: EventsService,
              private route: ActivatedRoute,
              private router: Router) {}

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.isEdit = true;
        this.id = Number(id);
        this.service.get(this.id).subscribe(e => {
          // convert ISO to local datetime-local value
          const dt = new Date(e.eventDateTime);
          const local = new Date(dt.getTime() - dt.getTimezoneOffset()*60000).toISOString().slice(0,16);
          this.form.patchValue({
            title: e.title,
            description: e.description,
            eventDateTime: local,
            location: e.location
          });
        });
      }
    });
  }

  save() {
    if (this.form.invalid) return;
    const raw = this.form.value;
    // convert datetime-local to ISO
    const iso = new Date(raw.eventDateTime).toISOString();
    const payload = {
      title: raw.title as string,
      description: raw.description as string | undefined,
      eventDateTime: iso,
      location: raw.location as string | undefined
    } as import('./event.model').Event;

    if (this.isEdit && this.id) {
      this.service.update(this.id, payload).subscribe(() => this.router.navigate(['/events']));
    } else {
      this.service.create(payload).subscribe(() => this.router.navigate(['/events']));
    }
  }
}