import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import { AppComponent } from './app.component';
import { EventsListComponent } from './events/events-list.component';
import { EventFormComponent } from './events/event-form.component';
import { EventDetailsComponent } from './events/event-details.component';

@NgModule({
  declarations: [
    AppComponent,
    EventsListComponent,
    EventFormComponent,
    EventDetailsComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    ReactiveFormsModule,
    RouterModule.forRoot([
      { path: '', redirectTo: 'events', pathMatch: 'full' },
      { path: 'events', component: EventsListComponent },
      { path: 'events/new', component: EventFormComponent },
      { path: 'events/:id/edit', component: EventFormComponent },
      { path: 'events/:id', component: EventDetailsComponent }
    ])
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
