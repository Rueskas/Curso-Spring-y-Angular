import { Component, OnInit } from '@angular/core';
import { Customer } from '../customer';
import { CustomerService } from '../customer.service';
import { ActivatedRoute } from '@angular/router';
import swal from 'sweetalert2';

@Component({
  selector: 'customer-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.css']
})
export class DetailComponent implements OnInit {

  customer: Customer;
  title: String = "Customer Detail";
  private selectedAvatar: File;
  constructor(private _customerService: CustomerService,
    private _activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this._activatedRoute.params.subscribe(
      params => {
        let id: number = +params["id"];
        if (id) {
          this._customerService.getCustomer(id).subscribe(
            customer => {
              this.customer = customer;
            }
          )
        }
      }
    )
  }

  setAvatar(event) {
    this.selectedAvatar = event.target.files[0];

  }

  public uploadFile() {
    this._customerService.uploadAvatar(this.selectedAvatar, "" + this.customer.getId())
      .subscribe(customer => {
        this.customer = customer;
        swal.fire('Avatar uploaded', 'Avatar uploaded succesfully', 'success');
      })
  }

}
