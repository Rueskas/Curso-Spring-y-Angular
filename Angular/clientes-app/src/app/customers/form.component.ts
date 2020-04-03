import { Component, OnInit } from '@angular/core';
import { Customer } from './customer';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html'
})
export class FormComponent implements OnInit {
  title: string = "Create Customer";
  customer: Customer = new Customer();
  constructor() { }

  ngOnInit(): void {
  }

  public create(): void {
    console.log("Clicked!");
    console.log(this.customer);
  }

}
