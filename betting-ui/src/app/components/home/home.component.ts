import { Component, OnInit } from '@angular/core';
import { PlayerInfoModel, PlayerService } from 'src/app/services/player.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  playerInfo: PlayerInfoModel = new PlayerInfoModel();
  loading = true;

  private dummyPlayerId = 1;

  constructor(private playerSerice: PlayerService) {
  }

  ngOnInit(): void {
    this.playerSerice.getPlayerInfo(this.dummyPlayerId).subscribe((res: PlayerInfoModel) => {
      this.playerInfo = res;
      this.loading = false;
    });
  }

}
