import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { OfferService, PlacedBetModel } from 'src/app/services/offer.service';

@Component({
  selector: 'app-played-bets',
  templateUrl: './played-bets.component.html',
  styleUrls: ['./played-bets.component.css']
})
export class PlayedBetsComponent implements OnInit, OnDestroy {

  placedBets: PlacedBetModel[] = [];
  odds: number = 0;
  gain: number = 0;
  stake: number = 5;

  private placedBetsListener: Subscription | null = null;

  constructor(private offerService: OfferService) { }

  ngOnInit(): void {
    this.placedBetsListener = this.offerService.placedBetSubject$
      .subscribe(res => {
        this.placedBets = [...res];
        this.odds = res.map(it => it.oddValue).reduce((prevVal, currVal) => prevVal + currVal, 0);
        this.gain = this.stake * this.odds;
      });
  }

  ngOnDestroy(): void {
    if (this.placedBetsListener != null) {
      this.placedBetsListener.unsubscribe();
    }
  }

  submitBet() {
    const bets = this.placedBets.map(bet => ({ offerId: bet.offerId, oddId: bet.oddId }));
    const obj = {
      stake: this.stake,
      gain: this.gain,
      bets
    }
    console.log(obj);
  }

  onChange(event: number) {
    this.gain = event * this.odds;
  }

}
