import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EncLineaCanalComponent } from './enc-linea-canal.component';

describe('EncLineaCanalComponent', () => {
  let component: EncLineaCanalComponent;
  let fixture: ComponentFixture<EncLineaCanalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EncLineaCanalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EncLineaCanalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
