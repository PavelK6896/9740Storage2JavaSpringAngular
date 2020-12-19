import {ComponentFixture, TestBed} from '@angular/core/testing';
import {FormAddComponent} from "./form-add.component";
import {ClientService} from "../../shared/client.service";
import {HttpClientModule} from "@angular/common/http";

describe('FormAddComponent 5', () => {
    let component: FormAddComponent;
    let fixture: ComponentFixture<FormAddComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [FormAddComponent],
            providers: [ClientService],
            imports: [HttpClientModule]
        })
            .compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(FormAddComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('1', () => {
        expect(component).toBeTruthy();
    });
});
