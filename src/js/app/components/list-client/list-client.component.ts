import {Component, OnDestroy, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {Subscription} from "rxjs";
import {logUtil} from "../../util/log";
import {Client} from "../../util/interfaces";
import {ClientService} from "../../service/client.service";
import {AuthService} from "../../service/auth.service";
import {UploadFileService} from "../../service/upload-file.service";
import {url2} from "../../util/url";
import {ToastrService} from "ngx-toastr";

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
    loading: boolean = false
    loadingFile: boolean = true
    urlPK = url2.urlPK;


    constructor(private clientService: ClientService,
                private authService: AuthService,
                private uploadFileService: UploadFileService,
                private toastrService: ToastrService
    ) {
    }

    ngOnInit(): void {
        this.loading = false
        this.getAll();
        this.buttonSubjectSubscription = this.clientService.buttonSubject$.subscribe(() => this.addButton())
        this.postAddClientSubscription = this.clientService.postAddClient$.subscribe((client) => {
            this.client.push(client)

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
                this.loading = true
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
        this.loadingFile = false
        this.uploadFileService.loadFile(format)
            .subscribe(response => {
                this.loadingFile = true
                logUtil("loadReportFile+ ", response)
                if (response.status == 200) {
                    const url = window.URL.createObjectURL(new Blob(Array.of(response.body)));
                    const a = document.createElement('a');
                    a.style.display = 'none';
                    a.href = url;
                    let fileName = decodeURI(response.headers.get('content-disposition')).split("filename=");
                    a.download = fileName[1]
                    document.body.appendChild(a);
                    a.click();
                    a.remove();
                    window.URL.revokeObjectURL(url);
                    this.toastrService.success("Успешно", new Date().toLocaleTimeString(), {
                        timeOut: 500,
                    });
                }
            }, error => {
                this.loadingFile = true
                logUtil("loadReportFile- ", error)
                new Response(error.error).text().then((response) => {
                    // let r: MessageErrorDto = JSON.parse(response);
                    this.toastrService.error(response, new Date().toLocaleTimeString(), {
                        timeOut: 500,
                    });
                })
            })
    }

    filterButton() {
        this.filter = !this.filter
    }

    logout() {
        this.authService.logout()
    }
}
