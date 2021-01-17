import {Injectable} from '@angular/core';
import {url1} from "../util/url";
import {HttpClient} from "@angular/common/http";

@Injectable({
    providedIn: 'root'
})
export class UploadFileService {

    constructor(private http: HttpClient) {
    }

    loadFile(format: string) {
        return this.http.get(url1.loadReportFile + '/' + format,
            {
                observe: 'response',
                responseType: 'blob'
            })
    }

}
