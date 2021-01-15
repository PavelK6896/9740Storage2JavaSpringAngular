import {ComponentFixture, TestBed} from '@angular/core/testing';
import {MainLayoutComponent} from "./main-layout.component";
import {NO_ERRORS_SCHEMA} from "@angular/core";

describe('MainLayoutComponent 3', () => {
    let component: MainLayoutComponent;
    let fixture: ComponentFixture<MainLayoutComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [MainLayoutComponent],
            schemas: [NO_ERRORS_SCHEMA]
        })
            .compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(MainLayoutComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('1', () => {
        expect(component).toBeTruthy();
        const compiled = fixture.nativeElement;
        expect(compiled.querySelector('router-outlet')).not.toBe(null);
    });
});
