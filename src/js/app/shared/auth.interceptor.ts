import {Injectable} from "@angular/core";
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable, throwError} from "rxjs";

import {Router} from "@angular/router";
import {catchError} from "rxjs/operators";
import {AuthService} from "./auth.service";
import {logUtil} from "../util/log";


@Injectable()
export class AuthInterceptor implements HttpInterceptor {
    constructor(
        private authService: AuthService,
        private router: Router) {
    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const jwtToken = this.authService.getJwtToken();

        if (jwtToken) {
            return next.handle(this.addToken(req, jwtToken)).pipe(
                catchError((error) => {
                    logUtil("intercept ", error)
                    if (error.status === 401 || error.status === 403) {
                        this.authService.logout("просрочен токен")
                        return
                    }
                    return throwError(error)
                }))
        }

        return next.handle(req);
    }

    addToken(req: HttpRequest<any>, jwtToken: any) {
        let httpRequest = req.clone({
            headers: req.headers.set('Authorization', 'Bearer ' + jwtToken)
        });

        logUtil("addToken ", httpRequest.headers.get('Authorization'))
        return httpRequest;
    }

}
