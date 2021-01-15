import {ComponentFixture, TestBed} from '@angular/core/testing';
import {ListClientComponent} from "./list-client.component";
import {HttpClientModule} from "@angular/common/http";
import {NO_ERRORS_SCHEMA} from "@angular/core";
import {ClientService} from "../../service/client.service";

describe('ListClientComponent 4', () => {
    let component: ListClientComponent;
    let fixture: ComponentFixture<ListClientComponent>;
    let clientService: ClientService;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [ListClientComponent],
            providers: [ClientService],
            imports: [HttpClientModule],
            schemas: [NO_ERRORS_SCHEMA]
        })
            .compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(ListClientComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('1', () => {
        expect(component).toBeTruthy();

    });
});
