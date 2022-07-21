import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-bet-list',
  templateUrl: './bet-list.component.html',
  styleUrls: ['./bet-list.component.css']
})
export class BetListComponent implements OnInit {
  @Input() bets: string[] = [];
  constructor() { }

  ngOnInit(): void {
  }

}
