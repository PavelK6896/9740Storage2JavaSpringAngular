import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable, Subject} from "rxjs";
import {environment} from "../../environments/environment";
import {map} from "rxjs/operators";
import {Client} from "./interfaces";


@Injectable({providedIn: 'root'})
export class ClientService {

  buttonSubject$ = new Subject<void>();
  postAddClient$ = new Subject<Client>();

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<Client[]> {
    return this.http.get(`${environment.DbUrl}/api/v1/client`)
      //{[key: string]: any} - тип объекта
      .pipe(map((response: { [key: string]: any }) => {
        console.log("!!!!!!!!!!!!!!!response", response)
        return Object.keys(response).map(key => ({...response[key]}))
      }))
  }

  deleteId(id: string): Observable<void> {
    return this.http.delete<void>(`${environment.DbUrl}/api/v1/client/${id}`)
  }

  //неработет http post
  addClient(client: Client): Observable<Client> {
    let url = `${environment.DbUrl}/api/v1/client`;
    fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(client)
    }).then(response => {
      if (response.status == 204) {
        return null
      } else if (response.status == 200) {
        return response.json();
      }
    }).then(response => {
      if (response === null) return
      this.postAddClient$.next( {
        id: response.id,
        name: response.name,
        phone: response.phone,
        title: response.title,
      })
    })

//------------------
//     const headers = new HttpHeaders();
//     headers.append('Content-Type', 'application/json');
//     headers.append('Accept', 'application/json');
//
//     const formData: FormData = new FormData();
//     formData.append("reqData", JSON.stringify(client));
//
//     console.log(formData)
//
//     this.http.post(url, formData, {headers: headers})
//       //парсим ответ
//       .pipe(map((response) => {
//         console.log(" addClient ", response)
//         return client
//       }))
//-----------
//      this.http.post(url, client);
//--------
    return
  }
}
