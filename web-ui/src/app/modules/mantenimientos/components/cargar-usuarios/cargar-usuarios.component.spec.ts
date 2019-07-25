import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CargarUsuariosComponent } from './cargar-usuarios.component';

describe('CargarUsuariosComponent', () => {
  let component: CargarUsuariosComponent;
  let fixture: ComponentFixture<CargarUsuariosComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CargarUsuariosComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CargarUsuariosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
