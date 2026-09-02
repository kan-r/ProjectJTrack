import { ComponentFixture, TestBed } from '@angular/core/testing';
import { SprintForm } from './sprint-form';

describe('SprintForm', () => {
  let component: SprintForm;
  let fixture: ComponentFixture<SprintForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SprintForm],
    }).compileComponents();

    fixture = TestBed.createComponent(SprintForm);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
