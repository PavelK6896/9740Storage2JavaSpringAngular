import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {ClientPageComponent} from './page/client-page/client-page.component';
import {ListClientComponent} from './components/list-client/list-client.component';
import {FormAddComponent} from './components/form-add/form-add.component';

import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {LoginPageComponent} from './page/login-page/login-page.component';
import {AuthInterceptor} from "./security/auth.interceptor";
import {SharedModule} from "./shared.module";
import {MainLayoutComponent} from "./layout/main-layout/main-layout.component";

@NgModule({
    declarations: [
        AppComponent,
        MainLayoutComponent,
        ClientPageComponent,
        ListClientComponent,
        FormAddComponent,
        LoginPageComponent,
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        SharedModule,
        FormsModule,
        HttpClientModule,
        ReactiveFormsModule,
    ],
    providers: [
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthInterceptor,
            multi: true
        }
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
