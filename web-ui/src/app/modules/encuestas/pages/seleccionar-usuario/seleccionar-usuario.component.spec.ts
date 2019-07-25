import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SeleccionarUsuarioComponent } from './seleccionar-usuario.component';

describe('SeleccionarUsuarioComponent', () => {
  let component: SeleccionarUsuarioComponent;
  let fixture: ComponentFixture<SeleccionarUsuarioComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SeleccionarUsuarioComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SeleccionarUsuarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
