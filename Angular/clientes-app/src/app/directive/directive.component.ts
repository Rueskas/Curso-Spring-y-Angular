import { Component } from '@angular/core';

@Component({
  selector: 'app-directive',
  templateUrl: './directive.component.html'
})
export class DirectiveComponent {

  coursesList: string[] = ['Typescript', 'Java', 'C#', 'PHP', 'Kotlin'];
  hidden: boolean = false;
  constructor() { }

  setHidden(): void {
    this.hidden = !this.hidden;
  }

}
