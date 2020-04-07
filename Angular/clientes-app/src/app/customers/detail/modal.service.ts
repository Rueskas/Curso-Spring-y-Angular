import { Injectable, EventEmitter } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ModalService {

  modal: boolean = false;
  private _notifyUpload: EventEmitter<any> = new EventEmitter<any>();
  constructor() { }

  openModal() {
    this.modal = true;
  }
  closeModal() {
    this.modal = false;
  }

  get notifyUpload(): EventEmitter<any> {
    return this._notifyUpload;
  }
}
