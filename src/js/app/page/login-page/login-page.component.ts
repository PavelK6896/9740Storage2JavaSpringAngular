import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {LoginRequestDto} from "./login.request.dto";
import {AuthService} from "../../shared/auth.service";

@Component({
    selector: 'app-login-page',
    templateUrl: './login-page.component.html',
    styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent implements OnInit {


    loginForm: FormGroup;
    loginRequestDto: LoginRequestDto;

    constructor(private authService: AuthService) {
    }

    ngOnInit(): void {

        this.loginRequestDto = {
            username: '',
            password: ''
        };

        this.loginForm = new FormGroup({
            username: new FormControl('', Validators.required),
            password: new FormControl('', Validators.required)
        });


    }

    login() {

        this.loginRequestDto.username = this.loginForm.get('username').value;
        this.loginRequestDto.password = this.loginForm.get('password').value;

        console.log(this.loginRequestDto)
        console.log(this.loginRequestDto)

        // this.authService.login(this.loginRequestDto)
    }

}
