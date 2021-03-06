import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders, HttpResponse} from "@angular/common/http";
import {Observable, Subject} from "rxjs";


import {logUtil} from "../util/log";
import {Client} from "../util/interfaces";
import {url1} from "../util/url";

@Injectable({providedIn: 'root'})
export class ClientService {

    buttonSubject$ = new Subject<void>();
    postAddClient$ = new Subject<Client>();

    constructor(private http: HttpClient) {
    }

    getAll(): Observable<HttpResponse<Client[]>> {
        return this.http.get<Client[]>(url1.getAll, {
            headers: new HttpHeaders({'Content-Type': 'application/json'}),
            observe: 'response'
        });
    }

    deleteId(id: string): Observable<void> {
        return this.http.delete<void>(url1.deleteId + `/${id}`)
    }

    updateClient(client: Client): Observable<any> {
        return this.http.put<Client>(url1.update,
            client,
            {
                headers: new HttpHeaders({'Content-Type': 'application/json'}),
                observe: 'response'
            });
    }

    getFilter(clientFilter: Client): Observable<any> {
        return this.http.post<Client[]>(url1.filter,
            clientFilter,
            {
                headers: new HttpHeaders({'Content-Type': 'application/json'}),
                observe: 'response'
            });
    }

    addClient(client: Client): void {
        this.http.post<Client>(url1.add,
            client,
            {
                headers: new HttpHeaders({'Content-Type': 'application/json'}),
                observe: 'response'
            })
            .subscribe(response => {
                logUtil("addClient+ ", response)
                if (response.status == 201) {
                    this.postAddClient$.next(response.body)
                }
            }, error => {
                logUtil("addClient- ", error)
                if (error.status == 400) {
                    //   this.alertService.warning(error.error)

                }
            })
    }

}
