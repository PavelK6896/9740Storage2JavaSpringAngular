import {ComponentFixture, TestBed} from '@angular/core/testing';
import {ClientPageComponent} from "./client-page.component";
import {NO_ERRORS_SCHEMA} from "@angular/core";

describe('ClientPageComponent 2', () => {
    let component: ClientPageComponent;
    let fixture: ComponentFixture<ClientPageComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [ClientPageComponent],
            schemas: [NO_ERRORS_SCHEMA]

        })
            .compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(ClientPageComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('1', () => {
        expect(component).toBeTruthy();
        const compiled = fixture.nativeElement;
        expect(compiled.querySelector('app-list-client')).not.toBe(null);
    });
});
