import { Component, OnInit, Input } from '@angular/core';
import { Customer } from '../customer';
import { CustomerService } from '../customer.service';
//import { ActivatedRoute } from '@angular/router';
import swal from 'sweetalert2';
import { HttpEventType } from '@angular/common/http';
import { ModalService } from './modal.service';
import { Global } from '../../../assets/global'
import { AuthService } from 'src/app/users/auth.service';
import { Invoice } from 'src/app/invoices/models/invoice';
import { InvoicesService } from 'src/app/invoices/services/invoices.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'customer-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.css']
})
export class DetailComponent implements OnInit {

  @Input() customer: Customer;
  title: String = "Customer Detail";
  selectedAvatar: File;
  progress: number = 0;
  url = Global.URL;
  constructor(private _customerService: CustomerService,
    private _invoiceService: InvoicesService,
    public modalService: ModalService,
    public authService: AuthService) { }

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
      this._customerService.uploadAvatar(this.selectedAvatar, "" + this.customer.id)
        .subscribe(event => {
          if (event.type === HttpEventType.UploadProgress) {
            this.progress = Math.round((event.loaded / event.total) * 100);
          } else if (event.type === HttpEventType.Response) {
            let res: any = event.body;
            this.customer = res.customer as Customer;
            this.modalService.notifyUpload.emit(this.customer);
            swal.fire('Avatar uploaded', res.message, 'success');
          }
        })
    }

  }

  public deleteInvoice(invoice: Invoice): void {
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
        this._invoiceService.deleteInvoice(invoice.id).
          subscribe(_ => {
            this.customer.invoices = this.customer.invoices.filter(i => i.id != invoice.id);
            swal.fire('Deleted', 'Invoice deleted successfully', 'success');
          });

      } else {
        swalWithBootstrapButtons.fire(
          'Canceled!',
          'Invoice has not been deleted.',
          'error'
        );
      }
    })
  }

  closeModal() {
    this.modalService.closeModal();
    this.selectedAvatar = null;
    this.progress = 0;
  }

}
