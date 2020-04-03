import { Component, OnInit } from '@angular/core';
import { Customer } from './customer';
import { CustomerService } from './customer.service'
import Swal from 'sweetalert2';

@Component({
  selector: 'app-customers',
  templateUrl: './customers.component.html'
})
export class CustomersComponent implements OnInit {
  customers: Customer[];
  constructor(private _customerService: CustomerService) {
  }

  ngOnInit(): void {
    this._customerService.getCustomers().subscribe(
      (customers: Customer[]) => {
        this.customers = customers.map(
          c => new Customer(
            (c as any).id, (c as any).name, (c as any).surname, (c as any).email, (c as any).createdAt))
      }
    );
  }

  public delete(customer: Customer): void {
    const swalWithBootstrapButtons = Swal.mixin({
      customClass: {
        confirmButton: 'btn btn-success',
        cancelButton: 'btn btn-danger'
      },
      buttonsStyling: false
    })

    swalWithBootstrapButtons.fire({
      title: 'Are you sure?',
      text: "You won't be able to revert this!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Yes, delete it!',
      cancelButtonText: 'No, cancel!',
      reverseButtons: true
    }).then((result) => {
      if (result.value) {
        this._customerService.deleteCustomer(customer.getId())
          .subscribe(
            _ => {
              this.customers = this.customers.filter(c => c.getId() != customer.getId())
              swalWithBootstrapButtons.fire(
                'Deleted!',
                'Customer ' + customer.getName() + ' has been deleted.',
                'success'
              );
            }
          )
      } else {
        swalWithBootstrapButtons.fire(
          'Canceled!',
          'Customer ' + customer.getName() + ' has not been deleted.',
          'error'
        );
      }
    })
  }

}
