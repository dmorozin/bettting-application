import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { BetPlacingUiService } from 'src/app/services/bet-placing-ui.service';
import { OddModel, OfferModel, OfferService, OfferTableModel } from 'src/app/services/offer.service';


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
  loading = true;

  pageSizeOptions = [3, 5, 10];
  length = 10;
  pageSize = 5;
  pageIndex = 0;

  @ViewChild(MatPaginator) paginator: MatPaginator | null = null;

  constructor(private offerService: OfferService,
    private betPlacingUiService: BetPlacingUiService) { }

  ngOnInit(): void {
    this.createTable(0, this.pageSize);
  }

  private createTable(page: number, size: number) {
    this.loading = true;
    this.offerService.getOffers(page, size).subscribe((res) => {
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
      this.length = res.totalElements;
      this.loading = false;
    });
  }

  ngAfterViewInit() {
    if (this.dataSource != null) {
      this.dataSource.paginator = this.paginator;
    }
  }

  onPageChange(event: any) {
    //  console.log(event);
    this.pageSize = event.pageSize;
    this.pageIndex = event.pageIndex;
    this.createTable(event.pageIndex, this.pageSize);
  }

  isOddSelected(oddId: number) {
    return this.betPlacingUiService.getPlacedBets().some(bet => bet.oddId === oddId);
  }

  onBet(element: OfferTableModel, odd: OddModel) {
    const bet = `${element.homeTeam} - ${element.awayTeam} ${odd.outcome} - ${odd.oddValue}`;
    this.betPlacingUiService.setPlacedBet({
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

