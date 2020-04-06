import { Component, OnInit, Input } from '@angular/core';
import { Customer } from '../customer';
import { CustomerService } from '../customer.service';
//import { ActivatedRoute } from '@angular/router';
import { Global } from '../../../assets/global'
import swal from 'sweetalert2';
import { HttpEventType } from '@angular/common/http';
import { ModalService } from './modal.service';

@Component({
  selector: 'customer-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.css']
})
export class DetailComponent implements OnInit {

  @Input() customer: Customer;
  title: String = "Customer Detail";
  url = Global.url;
  selectedAvatar: File;
  progress: number = 0;
  constructor(private _customerService: CustomerService,
    public modalService: ModalService) { }

  ngOnInit(): void {
		/*
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
		*/
  }

  setAvatar(event) {
    if (event.target.files[0].type.indexOf('image') < 0) {
      swal.fire('Error Upload', 'You has not selected a valid image', 'error');
      this.selectedAvatar = null;
    } else {
      this.selectedAvatar = event.target.files[0];
      this.progress = 0;
    }
  }

  public uploadFile() {
    if (!this.selectedAvatar) {
      swal.fire('Error Upload', 'You must to select an image first', 'error');
    } else {
      this._customerService.uploadAvatar(this.selectedAvatar, "" + this.customer.getId())
        .subscribe(event => {
          if (event.type === HttpEventType.UploadProgress) {
            this.progress = Math.round((event.loaded / event.total) * 100);
          } else if (event.type === HttpEventType.Response) {
            let res: any = event.body;
            this.customer = new Customer(res.customer.id, res.customer.name,
              res.customer.surname, res.customer.email, res.customer.createdAt,
              res.customer.avatar);
            swal.fire('Avatar uploaded', res.message, 'success');
          }
        })
    }

  }

  closeModal() {
    this.modalService.closeModal();
    this.selectedAvatar = null;
    this.progress = 0;
  }

}
