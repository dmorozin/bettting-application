import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { BetPlacingUiService, PlacedBetModel } from 'src/app/services/bet-placing-ui.service';
import { NewBetslipModel, PlayerService } from 'src/app/services/player.service';

@Component({
  selector: 'app-played-bets',
  templateUrl: './played-bets.component.html',
  styleUrls: ['./played-bets.component.css']
})
export class PlayedBetsComponent implements OnInit, OnDestroy {
  @Input() walletAmount = 0;
  @Input() playerId = 0;
  placedBets: PlacedBetModel[] = [];
  odds: number = 0;
  gain: number = 0;
  stakeFormControl = new FormControl(5, [Validators.required, Validators.min(5), Validators.max(10000)]);

  private placedBetsListener: Subscription | null = null;
  private stakeValueChangeListener: Subscription | null = null;

  constructor(private betPlacingUiService: BetPlacingUiService,
    private playerService: PlayerService) { }

  ngOnInit(): void {
    this.stakeFormControl.addValidators(Validators.max(this.walletAmount));
    this.initializePlacedBetsListener();
    this.initializeStakeValueChange();
  }

  ngOnDestroy(): void {
    if (this.placedBetsListener != null) {
      this.placedBetsListener.unsubscribe();
    }
    if (this.stakeValueChangeListener != null) {
      this.stakeValueChangeListener.unsubscribe();
    }
  }

  submitBet() {
    const stake = this.stakeFormControl.value;
    if (stake != null) {
      const bets = this.placedBets.map(bet => ({ offerId: bet.offerId, oddId: bet.oddId }));
      const newBetslip: NewBetslipModel = {
        stake,
        gain: this.gain,
        bets
      }
      this.playerService.addNewBetslip(this.playerId, newBetslip).subscribe(res => {
        console.log(res);
      });
    }
  }

  isButtonDisabled() {
    return this.placedBets.length === 0 || this.stakeFormControl.errors != null;
  }

  private initializePlacedBetsListener() {
    this.placedBetsListener = this.betPlacingUiService.placedBet$
      .subscribe(res => {
        this.placedBets = [...res];
        this.odds = res.map(it => it.oddValue).reduce((prevVal, currVal) => prevVal + currVal, 0);
        if (this.stakeFormControl.value)
          this.gain = this.stakeFormControl.value * this.odds;
      });
  }

  private initializeStakeValueChange() {
    this.stakeValueChangeListener = this.stakeFormControl.valueChanges.subscribe(value => {
      if (value != null) {
        console.log(this.stakeFormControl.errors);
        this.gain = value * this.odds;
      }
    });
  }

}
