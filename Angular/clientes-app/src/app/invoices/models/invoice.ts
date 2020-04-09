import { InvoiceItem } from './invoice-item';
import { Customer } from '../../customers/customer';

export class Invoice {
  id: number;
  description: string;
  createdAt: Date;
  observations: string;
  items: InvoiceItem[] = [];
  customer: Customer;
  get total() {
    if (this.items.length > 0) {
      return this.items.map(item => item.price).reduce((sum, current) => sum + current);
    } else {
      return 0;
    }
  }
}
