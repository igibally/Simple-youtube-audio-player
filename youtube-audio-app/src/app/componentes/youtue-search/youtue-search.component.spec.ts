import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { YoutueSearchComponent } from './youtue-search.component';

describe('YoutueSearchComponent', () => {
  let component: YoutueSearchComponent;
  let fixture: ComponentFixture<YoutueSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ YoutueSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(YoutueSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
