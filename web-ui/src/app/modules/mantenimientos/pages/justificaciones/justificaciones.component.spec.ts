import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { JustificacionesComponent } from './justificaciones.component';

describe('JustificacionesComponent', () => {
  let component: JustificacionesComponent;
  let fixture: ComponentFixture<JustificacionesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ JustificacionesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(JustificacionesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
