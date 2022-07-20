import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OfferService {
  private apiUrl = 'http://localhost:8080/api/offer';

  constructor(private http: HttpClient) { }

  getOffers(page: number, size: number): Observable<any> {
    let httpParams = new HttpParams();
    httpParams = httpParams.set("page", page.toString());
    httpParams = httpParams.set("size", size.toString());
    return this.http.get<any>(this.apiUrl, { params: httpParams });
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

