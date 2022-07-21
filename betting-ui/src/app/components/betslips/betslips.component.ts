import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PlacedBetsDTO, PlayerBetslipsModel, PlayerService } from 'src/app/services/player.service';

@Component({
  selector: 'app-betslips',
  templateUrl: './betslips.component.html',
  styleUrls: ['./betslips.component.css']
})
export class BetslipsComponent implements OnInit {

  betslips: PlayerBetslipsModel[] = [];
  bets: { [key: number]: string[] } = {};
  loading = true;

  constructor(private playerService: PlayerService,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
    const playerId = this.route.snapshot.paramMap.get('id');
    if (playerId != null) {
      this.playerService.getPlayerBetslips(parseInt(playerId)).subscribe({
        next: res => {
          this.betslips = [...res];
          this.loading = false;
        },
        error: err => console.log(err)
      });
    }
  }

  onExpand(betslipId: number) {
    if (this.bets[betslipId] == null) {
      this.playerService.getBetslipBets(betslipId).subscribe({
        next: (res: PlacedBetsDTO[]) => {
          const bets = res.map(bet => ` ${bet.homeTeam} : ${bet.awayTeam}      ${bet.outcome} - ${bet.oddValue}`);
          this.bets[betslipId] = bets;
        },
        error: err => console.log(err)
      });
    }
  }

  getBets(betslipId: number) {
    return this.bets[betslipId];
  }

  convertDateStringToLocaleString(date: string) {
    return new Date(date).toLocaleString();
  }
}
