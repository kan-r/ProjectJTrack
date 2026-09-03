import { Component, Inject } from '@angular/core';
import { MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';

interface ConfirmationData {
  title: string;
  message: string;
}

@Component({
  imports: [MatDialogModule, MatButtonModule],
  selector: 'app-confirmation-dialog',
  styleUrl: './confirmation-dialog.css',
  templateUrl: './confirmation-dialog.html',
})
export class ConfirmationDialog {
  protected dialogRef: MatDialogRef<ConfirmationDialog>;
  protected data: ConfirmationData;

  constructor(
    dialogRef: MatDialogRef<ConfirmationDialog>,
    @Inject(MAT_DIALOG_DATA) data: ConfirmationData,
  ) {
    this.dialogRef = dialogRef;
    this.data = data;
  }
}
