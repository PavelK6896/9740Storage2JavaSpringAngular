import {Component, OnDestroy, OnInit} from '@angular/core';
import {ClientService} from "../../shared/client.service";
import {Subscription} from "rxjs";
import {Client} from "../../shared/interfaces";

@Component({
  selector: 'app-list-client',
  templateUrl: './list-client.component.html',
  styleUrls: ['./list-client.component.scss']
})
export class ListClientComponent implements OnInit, OnDestroy {


  client: Client[] = []
  cSub: Subscription


  constructor(private clientService: ClientService) {
  }

  ngOnInit(): void {
    // загрузка клиентов
    this.cSub = this.clientService.getAll().subscribe(client => {
      this.client = client
    })
  }

  ngOnDestroy(): void {
    if (this.cSub) {
      this.cSub.unsubscribe()
    }
  }


}
