import {Injectable} from '@angular/core';

import {Observable, throwError} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {LoginRequestDto, LoginResponseDto} from "./interfaces";
import {map} from "rxjs/operators";
import {Router} from "@angular/router";

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    message: string

    constructor(private httpClient: HttpClient, private router: Router) {
    }

    login(loginRequestDto: LoginRequestDto): Observable<boolean> {
        return this.httpClient.post<LoginResponseDto>(environment.DbUrl + '/storage2/login', loginRequestDto)
            .pipe(map(data => {
                console.log(data)
                localStorage.setItem('authenticationToken', data.authenticationToken);
                localStorage.setItem('username', data.username);
                localStorage.setItem('expiresAt', data.expiresAt.toString());
                return true;
            }));
    }

    logout(message?: string) {
        this.message = message
        this.httpClient.post(environment.DbUrl + '/storage2/logout', {responseType: 'text'})
            .subscribe(data => {

                console.log(data)
                console.log('logout data!!!!!!!!!!!!11')

            }, error => {
                console.log(error)
                console.log('TODO error logout')

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
