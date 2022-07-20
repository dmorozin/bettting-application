import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
  }),
};

@Injectable({
  providedIn: 'root'
})
export class PlayerService {
  private apiUrl = 'http://localhost:8080/api/player';

  constructor(private http: HttpClient) { }

  getPlayerInfo(playerId: number): Observable<PlayerInfoModel> {
    return this.http.get<any>(`${this.apiUrl}/${playerId}`);
  }

  addNewBetslip(playerId: number, newBetslip: NewBetslipModel): Observable<number> {
    return this.http.post<number>(`${this.apiUrl}/${playerId}/betslip`, newBetslip, httpOptions);
  }

  getPlayerBets(playerId: number): Observable<PlayerInfoModel> {
    return this.http.get<any>(`${this.apiUrl}/${playerId}/betslip`);
  }
}

export class PlayerInfoModel {
  id!: number;
  name!: string;
  amount!: number;
}

export interface NewBetslipModel {
  stake: number;
  gain: number;
  bets: BetModel[];
}

export interface BetModel {
  offerId: number;
  oddId: number;
}

export interface PlayerBetslipsModel {
  betslipId: number;
  createdOn: string;
  stake: number;
  gain: number;
  placedBets: PlacedBetsDTO[];
}

export interface PlacedBetsDTO {
  homeTeam: string;
  awayTeam: string;
  outcome: string;
  odd: number;
}