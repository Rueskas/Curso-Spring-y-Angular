import { Region } from './region';
import { Invoice } from '../invoices/models/invoice';

export class Customer {

  id: number;
  name: string;
  surname: string;
  email: string;
  createdAt: Date;
  avatar: string;
  region: Region;
  invoices: Invoice[] = []

}
