import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LineasEpsComponent } from './lineas-eps.component';

describe('LineasEpsComponent', () => {
  let component: LineasEpsComponent;
  let fixture: ComponentFixture<LineasEpsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LineasEpsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LineasEpsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
