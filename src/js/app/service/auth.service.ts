import {Injectable} from '@angular/core';

import {Observable, throwError} from "rxjs";
import {HttpClient} from "@angular/common/http";

import {map} from "rxjs/operators";
import {Router} from "@angular/router";
import {logUtil} from "../util/log";
import {LoginRequestDto, LoginResponseDto} from "../util/interfaces";
import {url1} from "../util/url";

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    message: string

    constructor(private httpClient: HttpClient, private router: Router) {
    }

    login(loginRequestDto: LoginRequestDto): Observable<boolean> {
        return this.httpClient.post<LoginResponseDto>(url1.login, loginRequestDto)
            .pipe(map(data => {
                logUtil("login map", data)
                localStorage.setItem('authenticationToken', data.authenticationToken);
                localStorage.setItem('username', data.username);
                localStorage.setItem('expiresAt', data.expiresAt.toString());
                return true;
            }));
    }

    logout(message?: string) {
        this.message = message
        this.httpClient.post(url1.logout, {}, {responseType: 'text'})
            .subscribe(data => {
                logUtil("logout+ ", data)
            }, error => {
                logUtil("logout- TODO error??", error)
                throwError(error);
            })

        this.router.navigateByUrl('/login');
        localStorage.removeItem('authenticationToken');
        localStorage.removeItem('username');
        localStorage.removeItem('expiresAt');
    }

    getJwtToken() {
        return localStorage.getItem('authenticationToken');
    }

    isLoggedIn(): boolean {
        return this.getJwtToken() != null;
    }

    getMessage(): string {
        return this.message
    }

}
