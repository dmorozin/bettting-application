import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { OddModel, OfferModel, OfferService, OfferTableModel, PlacedBetModel } from 'src/app/services/offer.service';


@Component({
  selector: 'app-offers',
  templateUrl: './offers.component.html',
  styleUrls: ['./offers.component.css']
})
export class OffersComponent implements OnInit, AfterViewInit {
  displayedColumns: string[] = [
    'id',
    'homeTeam',
    'awayTeam',
    'homeWinOdd',
    'drawOdd',
    'awayWinOdd'
  ];
  dataSource: MatTableDataSource<OfferTableModel> = new MatTableDataSource<OfferTableModel>();

  @ViewChild(MatPaginator) paginator: MatPaginator | null = null;

  constructor(private offerService: OfferService) { }

  ngOnInit(): void {
    this.offerService.getOffers().subscribe((res) => {
      const tableData: OfferTableModel[] = res['content'].map((it: OfferModel) => (
        {
          id: it.offerId,
          homeTeam: it.homeTeam,
          awayTeam: it.awayTeam,
          homeWinOdd: this.findOutcome(it.odds, '1'),
          drawOdd: this.findOutcome(it.odds, 'X'),
          awayWinOdd: this.findOutcome(it.odds, '2'),
        }
      ));

      this.dataSource = new MatTableDataSource<OfferTableModel>(tableData);
    });
  }

  ngAfterViewInit() {
    if (this.dataSource != null) {
      this.dataSource.paginator = this.paginator;
    }
  }

  isOddSelected(oddId: number) {
   return this.offerService.getPlacedBets().some(bet => bet.oddId === oddId);
  }

  onBet(element: OfferTableModel, odd: OddModel) {
    const bet = `${element.homeTeam} - ${element.awayTeam} ${odd.outcome} - ${odd.oddValue}`;
    this.offerService.setPlacedBet({
      offerId: element.id,
      oddId: odd.oddId,
      bet,
      oddValue: odd.oddValue
    });
  }

  private findOutcome(odds: OddModel[], outcome: string): OddModel {
    return odds.filter(odd => odd.outcome === outcome)[0];
  }
}

