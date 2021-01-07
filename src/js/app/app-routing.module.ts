import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MainLayoutComponent} from "./main-layout/main-layout.component";
import {ClientPageComponent} from "./page/client-page/client-page.component";
import {LoginPageComponent} from "./page/login-page/login-page.component";
import {AuthGuard} from "./shared/auth.guard";

const routes: Routes = [
    {
        path: '', component: MainLayoutComponent, children: [
            {path: 'login', component: LoginPageComponent},
            {path: '', redirectTo: '/', pathMatch: 'full', canActivate: [AuthGuard]},
            {path: '', component: ClientPageComponent, canActivate: [AuthGuard]},
        ]
    }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})

export class AppRoutingModule {
}
