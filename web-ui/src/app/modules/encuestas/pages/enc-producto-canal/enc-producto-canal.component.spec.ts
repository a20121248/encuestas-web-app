import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EncProductoCanalComponent } from './enc-producto-canal.component';

describe('EncProductoCanalComponent', () => {
  let component: EncProductoCanalComponent;
  let fixture: ComponentFixture<EncProductoCanalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EncProductoCanalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EncProductoCanalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
