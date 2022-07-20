import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BetPlacingUiService {
  private placedBetSubject = new BehaviorSubject<PlacedBetModel[]>([]);
  public placedBet$ = this.placedBetSubject.asObservable();

  constructor() { }

  public setPlacedBet(placedBet: PlacedBetModel) {
    const placedBets = this.placedBetSubject.getValue();

    const foundBet = placedBets.find(bet => bet.offerId === placedBet.offerId);

    if (foundBet !== undefined) {
      const foundIndex = placedBets.indexOf(foundBet);
      if (foundIndex >= 0) {
        placedBets.splice(foundIndex, 1);
      }

      if (placedBet.oddId !== foundBet.oddId) {
        placedBets.push(placedBet);
      }
      this.placedBetSubject.next(placedBets);
      return;
    }

    placedBets.push(placedBet);
    this.placedBetSubject.next(placedBets);
  }

  public getPlacedBets() {
    return this.placedBetSubject.getValue();
  }

}

export interface PlacedBetModel {
  offerId: number;
  oddId: number;
  bet: string;
  oddValue: number;
}