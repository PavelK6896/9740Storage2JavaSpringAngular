import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MainLayoutComponent } from './main-layout/main-layout.component';
import { ClientPageComponent } from './page/client-page/client-page.component';
import { ListClientComponent } from './components/list-client/list-client.component';
import { FormAddComponent } from './components/form-add/form-add.component';
import { FormAmendComponent } from './components/form-amend/form-amend.component';
import {SharedModule} from "./shared/shared.module";
import {FormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    AppComponent,
    MainLayoutComponent,
    ClientPageComponent,
    ListClientComponent,
    FormAddComponent,
    FormAmendComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        SharedModule,
        FormsModule
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
