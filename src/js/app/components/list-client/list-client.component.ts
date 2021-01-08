import {Component, OnDestroy, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {ClientService} from "../../shared/client.service";
import {Subscription} from "rxjs";
import {AuthService} from "../../shared/auth.service";
import {logUtil} from "../../util/log";
import {Client} from "../../util/interfaces";

@Component({
    selector: 'app-list-client',
    templateUrl: './list-client.component.html',
    styleUrls: ['./list-client.component.scss']
})
export class ListClientComponent implements OnInit, OnDestroy {

    //type
    @ViewChild('readOnlyTemplate', {static: false}) readOnlyTemplate: TemplateRef<any>;
    @ViewChild('editTemplate', {static: false}) editTemplate: TemplateRef<any>;

    client: Client[] = []
    clientSubscription: Subscription
    deleteSubscription: Subscription
    buttonSubjectSubscription: Subscription
    postAddClientSubscription: Subscription
    updateClientSubscription: Subscription
    filterSearchSubscription: Subscription
    addFormSee: boolean = false
    updateFormSee: boolean = false
    filter: boolean = false
    clientUpdate: Client = {name: "", phone: "", title: ""}
    clientFilter: Client = {name: "", phone: "", title: ""}
    loading: boolean = true
    url = ""


    constructor(private clientService: ClientService,
                private authService: AuthService) {
    }

    ngOnInit(): void {
        this.getAll();
        this.buttonSubjectSubscription = this.clientService.buttonSubject$.subscribe(() => this.addButton())
        this.postAddClientSubscription = this.clientService.postAddClient$.subscribe((client) => {
            this.client.push(client)
            this.loading = false
        })
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
        if (this.updateClientSubscription) {
            this.updateClientSubscription.unsubscribe()
        }
        if (this.filterSearchSubscription) {
            this.filterSearchSubscription.unsubscribe()
        }
    }

    getAll() {
        this.clientSubscription = this.clientService.getAll()
            .subscribe(response => {
                logUtil("getAll+ ", response)
                this.client = response.body
            }, response => {
                logUtil("getAll- ", response)
            })
    }

    deleteId(id: string) {
        this.deleteSubscription = this.clientService.deleteId(id)
            .subscribe((response) => {
                logUtil("deleteId+ ", response)
                this.client = this.client.filter((cl) => cl.id !== id)
                // this.alertService.warning('клиент удален')
            }, response => {
                logUtil("deleteId- ", response)
            })
    }

    updateClient() {
        this.updateClientSubscription = this.clientService.updateClient(this.clientUpdate)
            .subscribe(response => {
                logUtil("updateClient+ ", response)
                if (response.status == 201) {

                }
            }, error => {
                logUtil("updateClient- ", error)
                if (error.status == 400) {
                    //   this.alertService.warning(error.error)
                }
            })

        this.cancel()
    }

    addButton() {
        this.addFormSee = !this.addFormSee
    }

    updateButton(client: Client) {
        this.clientUpdate = client
        this.updateFormSee = true
    }

    filterSearch() {
        this.filterSearchSubscription = this.clientService.getFilter(this.clientFilter)
            .subscribe(response => {
                logUtil("filterSearch+ ", response)
                if (response.status == 200) {
                    this.client = response.body
                }
            }, error => {
                logUtil("filterSearch- ", error)
                if (error.status == 400) {
                    //   this.alertService.warning(error.error)
                }
            })
    }

    //cancel update
    cancel() {
        this.updateFormSee = false
        this.clientUpdate = {name: "", phone: "", title: ""}
    }

    //for form update
    loadTemplate(cl: Client) {
        if (this.updateFormSee && cl.id == this.clientUpdate.id) {
            return this.editTemplate;
        } else {
            return this.readOnlyTemplate;
        }
    }

    filterCancel() {
        this.filterButton() //close form
        this.clientFilter = {name: "", phone: "", title: ""}
        this.getAll()
    }

    loadReportFile(format: string) {
        this.clientService.loadReportFile(format)
    }

    filterButton() {
        this.filter = !this.filter
    }

    logout() {
        this.authService.logout()
    }
}
