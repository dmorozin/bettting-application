import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OfferService {
  private apiUrl = 'http://localhost:8080/api/offer';
  private placedBetSubject = new BehaviorSubject<PlacedBetModel[]>([]);
  public placedBetSubject$ = this.placedBetSubject.asObservable();

  constructor(private http: HttpClient) { }

  getOffers(): Observable<any> {
    return this.http.get<any>(this.apiUrl);
  }

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

export interface OfferModel {
  offerId: number;
  homeTeam: string;
  awayTeam: string;
  odds: OddModel[];
}

export interface OddModel {
  oddId: number;
  outcome: string;
  oddValue: number;
}

export interface OfferTableModel {
  id: number;
  homeTeam: string;
  awayTeam: string;
  homeWinOdd: OddModel;
  drawOdd: OddModel;
  awayWinOdd: OddModel;
}

export interface PlacedBetModel {
  offerId: number;
  oddId: number;
  bet: string;
  oddValue: number;
}