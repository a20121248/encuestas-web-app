import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MantenimientosComponent } from './mantenimientos.component';

describe('MantenimientosComponent', () => {
  let component: MantenimientosComponent;
  let fixture: ComponentFixture<MantenimientosComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MantenimientosComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MantenimientosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
