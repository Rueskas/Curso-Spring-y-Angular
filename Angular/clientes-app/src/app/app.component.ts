import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {
  title: string = 'Welcome to Angular';
  course: string = 'Spring 5 and Angular';
  teacher: string = 'Andrés José Guzmán';
}
