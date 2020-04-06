import { Component, OnChanges, Input, OnInit, SimpleChanges } from '@angular/core';

@Component({
  selector: 'paginator-nav',
  templateUrl: './paginator.component.html'
})
export class PaginatorComponent implements OnChanges, OnInit {

  @Input() paginator: any;
  pages: number[]
  rangePages: number = 4;
  min: number;
  max: number;
  constructor() { }

  ngOnInit() {
    this.getRanges();
  }

  ngOnChanges(changes: SimpleChanges): void {
    let paginatorUpdated = changes['paginator'];
    if (paginatorUpdated.previousValue) {
      this.getRanges();
    }
  }

  private getRanges() {

    this.min = Math.max(Math.max(1, this.paginator.number - this.rangePages + 1),
      1);
    this.max = Math.min(Math.min(this.paginator.number + this.rangePages, this.paginator.totalPages),
      this.paginator.totalPages);

    this.pages = new Array(this.max - this.min + 1).fill(0).map((_, index) => {
      return index + this.min;
    });
  }

}
