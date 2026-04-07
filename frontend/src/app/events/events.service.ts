import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Event } from './event.model';

@Injectable({ providedIn: 'root' })
export class EventsService {
  private base = '/api/events';

  constructor(private http: HttpClient) {}

  list(page = 0, size = 10): Observable<any> {
    const params = new HttpParams().set('page', String(page)).set('size', String(size));
    return this.http.get<any>(this.base, { params });
  }

  get(id: number) {
    return this.http.get<Event>(`${this.base}/${id}`);
  }

  create(event: Event) {
    return this.http.post<Event>(this.base, event);
  }

  update(id: number, event: Event) {
    return this.http.put<Event>(`${this.base}/${id}`, event);
  }

  delete(id: number) {
    return this.http.delete<void>(`${this.base}/${id}`);
  }
}
