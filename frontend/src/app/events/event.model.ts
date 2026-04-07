export interface Event {
  id?: number;
  title: string;
  description?: string;
  eventDateTime: string; // ISO
  location?: string;
  createdAt?: string;
  updatedAt?: string;
}
