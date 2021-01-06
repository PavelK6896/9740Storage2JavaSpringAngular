import {Injectable} from '@angular/core';
import {LoginRequestDto} from "../page/login-page/login.request.dto";
import {Observable, of} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    constructor() {
    }

    login(loginRequestDto: LoginRequestDto): Observable<boolean> {
        localStorage.setItem('authenticationToken', 'dddddddddd');
        return of(true);
    }


    logout() {
        localStorage.removeItem('authenticationToken');
        localStorage.removeItem('username');
        localStorage.removeItem('refreshToken');
        localStorage.removeItem('expiresAt');
    }

    getJwtToken() {

        console.log(localStorage.getItem('authenticationToken'))
        console.log(localStorage.getItem('authenticationToken'))
        console.log(localStorage.getItem('authenticationToken'))
        return localStorage.getItem('authenticationToken');
    }

    isLoggedIn(): boolean {
        return this.getJwtToken() != null;
    }

}
