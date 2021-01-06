import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {MainLayoutComponent} from './main-layout/main-layout.component';
import {ClientPageComponent} from './page/client-page/client-page.component';
import {ListClientComponent} from './components/list-client/list-client.component';
import {FormAddComponent} from './components/form-add/form-add.component';

import {SharedModule} from "./shared/shared.module";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {LoginPageComponent} from './page/login-page/login-page.component';

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
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule {
}
