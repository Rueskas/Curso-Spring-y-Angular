import { Component, OnInit } from '@angular/core';
import { Customer } from './customer';
import { CustomerService } from './customer.service';
import { Router, ActivatedRoute } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html'
})
export class FormComponent implements OnInit {
  title: string = "Create Customer";
  customer: Customer = new Customer();
  errors: String[];
  constructor(private _customerService: CustomerService, private _router: Router,
    private _activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.loadCustomer();
  }

  public loadCustomer(): void {
    this._activatedRoute.params.subscribe(params => {
      let id = params['id'];
      if (id) {
        this._customerService.getCustomer(id).subscribe(
          c => {
            this.customer = c;
            console.log(c);
          }
        )
      }
    })
  }

  public create(): void {
    this._customerService.postCustomer(this.customer).subscribe(
      _ => {
        Swal.fire('Created!', `Customer ${this.customer.getName()} has been created successfully.`, 'success');
        this._router.navigate(['/customers']);
      },
      err => {
        this.errors = err.error.errors as String[];
        console.error("Status Code: " + err.status);
        console.error(err.error.errors)
      }
    )
  }

  public update(): void {
    this._customerService.putCustomer(this.customer).subscribe(
      _ => {
        Swal.fire('Edited!', `Customer ${this.customer.getName()} has been edited successfully.`, 'success');
        this._router.navigate(['/customers']);
      },
      err => {
        this.errors = err.error.errors as String[];
        console.error("Status Code: " + err.status);
        console.error(err.error.errors)
      }
    )
  }

}
