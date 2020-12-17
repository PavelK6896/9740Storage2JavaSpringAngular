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
  url = `${environment.DbUrl}/storage2/api/v1/client`;

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<Client[]> {
    return this.http.get(this.url)
      //{[key: string]: any} - тип объекта
      .pipe(map((response: { [key: string]: any }) => {
        return Object.keys(response).map(key => ({...response[key]}))
      }))
  }

  deleteId(id: string): Observable<void> {
    return this.http.delete<void>(this.url+`/${id}`)
  }

  addClient(client: Client) {
    return fetch(this.url + '/add', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(client)
    }).then(response => {
      if (response.status == 204) {

        console.log("204")
        return null
      } else if (response.status == 200) {
        return response.json();
      }
    }).then(response => {
      if (response === null) return
      this.postAddClient$.next({
        id: response.id,
        name: response.name,
        phone: response.phone,
        title: response.title,
      })
      return response
    })
  }

  updateClient(client: any) {
    return fetch(this.url + '/update', {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(client)
    }).then(response => {
      if (response.status == 204) {
        console.log("204")
        return null
      } else if (response.status == 200) {
        return response.json();
      }
    }).then(response => {
      if (response === null) return;
      return response;
    })
  }

  getFilter(clientFilter) {
    return fetch(this.url + '/filter', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(clientFilter)
    }).then(response => {
      if (response.status == 204) {

        console.log("204")
        return null
      } else if (response.status == 200) {
        return response.json();
      }
    }).then(response => {
      if (response === null) return
      return response;
    })
  }

  loadReportFile = (format) => {
    let fileName = format
    fetch(this.url + '/' + format)
      .then(resp => {
        fileName = resp.headers.get("filename")
        return resp.blob()
      })
      .then(blob => {

        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.style.display = 'none';
        a.href = url;
        a.download = fileName
        document.body.appendChild(a);
        a.click();

        a.remove();
        window.URL.revokeObjectURL(url); //не сохроняеть сылку на файл
      })
      .catch(() => alert('error file!'));
  }

}


//неработет http post
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
