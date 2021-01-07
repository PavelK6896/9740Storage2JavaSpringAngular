import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders, HttpResponse} from "@angular/common/http";
import {Observable, Subject, Subscription} from "rxjs";
import {environment} from "../../environments/environment";


import {logUtil} from "../util/log";
import {Client} from "../util/interfaces";

@Injectable({providedIn: 'root'})
export class ClientService {

    buttonSubject$ = new Subject<void>();
    postAddClient$ = new Subject<Client>();
    url = `${environment.DbUrl}/storage2/api/v1/client`;

    constructor(private http: HttpClient) {
    }

    getAll(): Observable<HttpResponse<Client[]>> {
        return this.http.get<Client[]>(this.url, {
            headers: new HttpHeaders({'Content-Type': 'application/json'}),
            // withCredentials: true, //not cros *!!!
            observe: 'response'
        });
    }

    deleteId(id: string): Observable<void> {
        return this.http.delete<void>(this.url + `/${id}`)
    }

    updateClient(client: Client): Observable<any> {
        return this.http.put<Client>(this.url + '/update',
            client,
            {
                headers: new HttpHeaders({'Content-Type': 'application/json'}),
                observe: 'response'
            });
    }

    getFilter(clientFilter: Client): Observable<any> {
        return this.http.post<Client[]>(this.url + '/filter',
            clientFilter,
            {
                headers: new HttpHeaders({'Content-Type': 'application/json'}),
                observe: 'response'
            });
    }

    addClient(client: Client): void {
        this.http.post<Client>(this.url + '/add',
            client,
            {
                headers: new HttpHeaders({'Content-Type': 'application/json'}),
                observe: 'response'
            })
            .subscribe(response => {
                logUtil("addClient+ ", response )
                if (response.status == 201) {
                    this.postAddClient$.next(response.body)
                }
            }, error => {
                logUtil("addClient- ", error )
                if (error.status == 400) {
                    //   this.alertService.warning(error.error)

                }
            })
    }

    loadReportFile(format: string): void {
        this.http.get(this.url + '/' + format,
            {
                observe: 'response',
                responseType: 'blob'
            })
            .subscribe(response => {
                logUtil("loadReportFile+ ", response )
                if (response.status == 200) {
                    const url = window.URL.createObjectURL(new Blob(Array.of(response.body)));
                    const a = document.createElement('a');
                    a.style.display = 'none';
                    a.href = url;
                    a.download = response.headers.get("filename")
                    document.body.appendChild(a);
                    a.click();
                    a.remove();
                    window.URL.revokeObjectURL(url);
                }
            }, error => {
                logUtil("loadReportFile- ", error )
                if (error.status == 400) {
                    //   this.alertService.warning(error.error)
                }
            })
    }
}
