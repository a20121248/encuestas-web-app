import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EncCanalComponent } from './enc-canal.component';

describe('EncCanalComponent', () => {
  let component: EncCanalComponent;
  let fixture: ComponentFixture<EncCanalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EncCanalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EncCanalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
