
import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {map} from "rxjs/operators";
import {Client} from "./interfaces";


@Injectable({providedIn: 'root'})
export class ClientService {
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

}
