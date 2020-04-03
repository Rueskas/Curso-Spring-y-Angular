import { Component, OnInit } from '@angular/core';
import { Customer } from './customer';
import { CustomerService } from './customer.service'

@Component({
  selector: 'app-customers',
  templateUrl: './customers.component.html'
})
export class CustomersComponent implements OnInit {
  customers: Customer[];
  constructor(private customerService: CustomerService) {
  }

  ngOnInit(): void {
    this.customerService.getCustomers().subscribe(
      (customers: Customer[]) => {
        this.customers = customers.map(
          c => new Customer(
            (c as any).id, (c as any).name, (c as any).surname, (c as any).email, (c as any).createdAt))
      }
    );
  }

}
