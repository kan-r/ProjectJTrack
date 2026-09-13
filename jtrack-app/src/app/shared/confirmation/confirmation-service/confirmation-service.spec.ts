import { TestBed } from '@angular/core/testing';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmationService } from './confirmation-service';
import { ConfirmationDialog } from '../confirmation-dialog/confirmation-dialog';

describe('ConfirmationService', () => {
  let service: ConfirmationService;
  let dialog: { open: ReturnType<typeof vi.fn> };

  beforeEach(() => {
    dialog = {
      open: vi.fn(() => ({
        afterClosed: vi.fn(() => 'result'),
      })),
    };

    TestBed.configureTestingModule({
      providers: [{ provide: MatDialog, useValue: dialog }],
    });

    service = TestBed.inject(ConfirmationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should open the confirmation dialog with the supplied title and message', () => {
    const result = service.confirm('Delete item', 'Are you sure?');

    expect(dialog.open).toHaveBeenCalledWith(ConfirmationDialog, {
      width: '400px',
      disableClose: true,
      data: { title: 'Delete item', message: 'Are you sure?' },
    });
    expect(result).toBe('result');
  });

  it('should return the dialog result from afterClosed()', () => {
    const afterClosed = vi.fn(() => true);
    dialog.open = vi.fn(() => ({ afterClosed }));

    const result = service.confirm('Confirm', 'Proceed?');

    expect(afterClosed).toHaveBeenCalledTimes(1);
    expect(result).toBe(true);
  });
});
