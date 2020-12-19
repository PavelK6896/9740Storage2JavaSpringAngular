import {TestBed} from '@angular/core/testing';
import {AppComponent} from "./app.component";
import {RouterTestingModule} from "@angular/router/testing";

describe('AppComponent 1', () => {

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [
                RouterTestingModule
            ],
            declarations: [
                AppComponent
            ],
        }).compileComponents();
    });

    it('1', () => {
        const fixture = TestBed.createComponent(AppComponent);
        const app = fixture.componentInstance;
        expect(app).toBeTruthy();
    });

    it(`2`, () => {
        const fixture = TestBed.createComponent(AppComponent);
        const app = fixture.componentInstance;
        expect(app.title).toEqual('Storage 2');
    });

    it('3', () => {
        const fixture = TestBed.createComponent(AppComponent);
        fixture.detectChanges();
        const compiled = fixture.nativeElement;
        expect(compiled.querySelector('router-outlet')).not.toBe(null);
    });

});
