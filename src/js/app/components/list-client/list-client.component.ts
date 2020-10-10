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
  clientSubscription: Subscription
  deleteSubscription: Subscription
  buttonSubjectSubscription: Subscription
  postAddClientSubscription: Subscription
  addFormSee: boolean = false


  constructor(private clientService: ClientService) {
  }

  ngOnInit(): void {
    // загрузка клиентов
    this.clientSubscription = this.clientService.getAll().subscribe(client => {
      this.client = client
    })
    //подписался на событие в компоненте
    this.buttonSubjectSubscription = this.clientService.buttonSubject$.subscribe(() => this.addButton())
    this.postAddClientSubscription = this.clientService.postAddClient$
      .subscribe((client) => this.client.push(client))
  }



  ngOnDestroy(): void {
    if (this.clientSubscription) {
      this.clientSubscription.unsubscribe()
    }
    if (this.deleteSubscription) {
      this.deleteSubscription.unsubscribe()
    }
    if (this.buttonSubjectSubscription) {
      this.buttonSubjectSubscription.unsubscribe()
    }
    if (this.postAddClientSubscription) {
      this.postAddClientSubscription.unsubscribe()
    }
  }


  deleteId(id: string) {
    this.deleteSubscription = this.clientService.deleteId(id).subscribe(() => {
      this.client = this.client.filter((cl) => cl.id !== id      )
      // this.alertService.warning('клиент удален')
    })
  }


  addButton() {
    this.addFormSee = !this.addFormSee
  }


}
