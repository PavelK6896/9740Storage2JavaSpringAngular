import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../shared/auth.service";
import {InfoMessage, LoginRequestDto} from "../../shared/interfaces";
import {Subscription, throwError} from "rxjs";
import {Router} from "@angular/router";

@Component({
    selector: 'app-login-page',
    templateUrl: './login-page.component.html',
    styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent implements OnInit, OnDestroy {

    infoMessage: InfoMessage;
    loginForm: FormGroup;
    loginRequestDto: LoginRequestDto;
    loginSubscription: Subscription

    constructor(private authService: AuthService, private router: Router) {
    }

    ngOnInit(): void {

        console.log("11111111111", this.authService.getMessage())
        console.log(this.authService.getMessage())
        console.log(this.authService.getMessage())
        console.log(this.authService.getMessage())

        if (this.authService.getMessage() === undefined) {
            this.infoMessage = {
                flag: false,
                message: ''
            };
        } else {
            this.infoMessage = {
                flag: true,
                message: this.authService.getMessage()
            };
        }

        if (this.authService.isLoggedIn()) {
            this.router.navigateByUrl('/');
        }

        this.loginRequestDto = {
            username: '',
            password: ''
        };

        this.loginForm = new FormGroup({
            username: new FormControl('', Validators.required),
            password: new FormControl('', Validators.required)
        });
    }

    ngOnDestroy(): void {
        if (this.loginSubscription) {
            this.loginSubscription.unsubscribe()
        }
    }

    login() {
        this.loginRequestDto.username = this.loginForm.get('username').value;
        this.loginRequestDto.password = this.loginForm.get('password').value;

        this.loginSubscription = this.authService.login(this.loginRequestDto)
            .subscribe(data => {

                console.log("login")
                console.log(data)
                this.router.navigateByUrl('/');

            }, error => {

                this.infoMessage.flag = true
                this.infoMessage.message = error.error
                console.log("login")
                console.log(error)

                throwError(error);
            });

    }
}
