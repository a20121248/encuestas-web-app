import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EncLineaComponent } from './enc-linea.component';

describe('EncLineaComponent', () => {
  let component: EncLineaComponent;
  let fixture: ComponentFixture<EncLineaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EncLineaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EncLineaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
