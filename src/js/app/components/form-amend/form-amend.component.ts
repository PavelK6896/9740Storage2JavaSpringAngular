import { Component, OnInit } from '@angular/core';
import {ClientService} from "../../shared/client.service";
import {Subscription} from "rxjs";
import {Client} from "../../shared/interfaces";

@Component({
  selector: 'app-form-amend',
  templateUrl: './form-amend.component.html',
  styleUrls: ['./form-amend.component.scss']
})
export class FormAmendComponent implements OnInit {

  constructor(private clientService: ClientService) { }

  buttonSubjectSubscription: Subscription
  clientUpdate: Client = {name: "", phone: "", title: ""}

  ngOnInit(): void {
    this.buttonSubjectSubscription = this.clientService
      .updateFormClient$.subscribe((client) => this.clientUpdate = client)
  }

  add() {
    console.log("update ", this.clientUpdate)
    this.clientService.updateClient(this.clientUpdate)
  }

  cancel() {
    this.clientUpdate = {name: "", phone: "", title: ""}
  }


}
