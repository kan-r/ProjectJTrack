import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { TestBed } from '@angular/core/testing';
import { ConfirmationDialog } from './confirmation-dialog';

describe('ConfirmationDialog', () => {
  let component: ConfirmationDialog;
  let mockDialogRef: { afterClosed: ReturnType<typeof vi.fn> };
  let data: { title: string; message: string };

  beforeEach(async () => {
    mockDialogRef = {
      afterClosed: vi.fn(),
    };

    data = {
      title: 'Delete item',
      message: 'Are you sure you want to delete this item?',
    };

    await TestBed.configureTestingModule({
      imports: [ConfirmationDialog],
      providers: [
        { provide: MatDialogRef, useValue: mockDialogRef },
        { provide: MAT_DIALOG_DATA, useValue: data },
      ],
    }).compileComponents();

    const fixture = TestBed.createComponent(ConfirmationDialog);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should store the injected dialog reference and data', () => {
    expect(component['dialogRef']).toBe(mockDialogRef);
    expect(component['data']).toEqual(data);
  });

  it('should expose the confirmation title and message from the injected data', () => {
    expect(component['data'].title).toBe('Delete item');
    expect(component['data'].message).toBe('Are you sure you want to delete this item?');
  });
});
