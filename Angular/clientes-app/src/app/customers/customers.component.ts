import { Component, OnInit } from '@angular/core';
import { Customer } from './customer';
import { CustomerService } from './customer.service'
import Swal from 'sweetalert2';
import { ActivatedRoute } from '@angular/router'
import { tap } from 'rxjs/operators';
import { ModalService } from './detail/modal.service';

@Component({
  selector: 'app-customers',
  templateUrl: './customers.component.html'
})
export class CustomersComponent implements OnInit {
  customers: Customer[];
  paginator: any;
  selectedCustomer: Customer;
  constructor(private _customerService: CustomerService,
    private _activatedRoute: ActivatedRoute, private _modalService: ModalService) {
  }

  ngOnInit(): void {
    this._activatedRoute.paramMap.subscribe(params => {
      let page: number = +params.get('page');

      if (!page) {
        page = 0;
      }
      this._customerService.getCustomers(page)
        .pipe(
          tap(
            (response: any) => {
              console.log(response);
              this.customers = response.content.map(customer => {
                return new Customer(
                  customer.id, customer.name, customer.surname, customer.email, customer.createdAt)
              })
              this.paginator = response;
            }
          )
        )
        .subscribe();
    });
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

  public selectCustomer(customer: Customer) {
    this.selectedCustomer = customer;
    this._modalService.openModal();
  }
}
