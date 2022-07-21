import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-player-wallet',
  templateUrl: './player-wallet.component.html',
  styleUrls: ['./player-wallet.component.css']
})
export class PlayerWalletComponent {

  @Input() name = '';
  @Input() walletAmount = 0;
  @Input() id = 0;

  constructor() {
  }

}
