import {Component, OnDestroy, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {ClientService} from "../../shared/client.service";
import {Subscription} from "rxjs";
import {Client} from "../../shared/interfaces";

@Component({
  selector: 'app-list-client',
  templateUrl: './list-client.component.html',
  styleUrls: ['./list-client.component.scss']
})
export class ListClientComponent implements OnInit, OnDestroy {

  //типы шаблонов
  @ViewChild('readOnlyTemplate', {static: false}) readOnlyTemplate: TemplateRef<any>;
  @ViewChild('editTemplate', {static: false}) editTemplate: TemplateRef<any>;

  client: Client[] = []
  clientSubscription: Subscription
  deleteSubscription: Subscription
  buttonSubjectSubscription: Subscription
  postAddClientSubscription: Subscription
  addFormSee: boolean = false
  updateFormSee: boolean = false
  clientUpdate: Client = {name: "", phone: "", title: ""}


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

  updateButton(client: Client) {
    this.clientUpdate = client
    this.updateFormSee = true
  }

  cancel() {
    this.updateFormSee = false
    this.clientUpdate = {name: "", phone: "", title: ""}
  }


  loadTemplate(cl: Client) {
    if (this.updateFormSee && cl.id == this.clientUpdate.id) {
      return this.editTemplate;
    } else {
      return this.readOnlyTemplate;
    }
  }

  saveButton() {
    this.clientService.updateClient(this.clientUpdate)
    this.cancel()
  }
}
