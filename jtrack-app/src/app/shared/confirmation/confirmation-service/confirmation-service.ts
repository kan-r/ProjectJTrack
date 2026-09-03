import { inject, Service } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmationDialog } from '../confirmation-dialog/confirmation-dialog';

@Service()
export class ConfirmationService {
  private dialog = inject(MatDialog);

  confirm(title: string, message: string) {
    const dialogRef = this.dialog.open(ConfirmationDialog, {
      width: '400px',
      disableClose: true,
      data: { title, message },
    });

    return dialogRef.afterClosed();
  }
}
