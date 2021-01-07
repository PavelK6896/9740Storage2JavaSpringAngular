import {Component, OnInit} from '@angular/core';
import {ClientService} from "../../shared/client.service";
import {Client} from "../../util/interfaces";

@Component({
    selector: 'tr[app-form-add]',
    templateUrl: './form-add.component.html',
    styleUrls: ['./form-add.component.scss']
})
export class FormAddComponent implements OnInit {

    client: Client = {name: "", phone: "", title: ""}

    constructor(private clientService: ClientService) {
    }

    ngOnInit(): void {
    }

    cancel() {
        this.client = {name: "", phone: "", title: ""}
        this.clientService.buttonSubject$.next() //close form
    }

    addClient() {
        this.clientService.addClient(this.client);
    }
}
