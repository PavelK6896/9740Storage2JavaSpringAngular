import {Injectable} from '@angular/core';
import {url1} from "../util/url";
import {logUtil} from "../util/log";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class UploadFileService {

  constructor(private http: HttpClient) {
  }

  loadFile(format: string): void {
    this.http.get(url1.loadReportFile + '/' + format,
        {
          observe: 'response',
          responseType: 'blob'
        })
        .subscribe(response => {
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
          }
        }, error => {
          logUtil("loadReportFile- ", error)
          if (error.status == 400) {
            //   this.alertService.warning(error.error)
          }
        })
  }

}
