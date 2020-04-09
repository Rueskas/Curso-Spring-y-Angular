import { Product } from './product';

export class InvoiceItem {
  id: number;
  amount: number = 1;
  product: Product;
  private _price: number;
  get price(): number {
    return this.amount * this.product.price;
  }

}
