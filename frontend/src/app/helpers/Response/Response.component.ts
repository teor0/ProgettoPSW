import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-Response',
  templateUrl: './Response.component.html',
  styleUrls: ['./Response.component.css']
})
export class ResponseComponent{

  title: string='Response';
  msg!: string;

  constructor(@Inject(MAT_DIALOG_DATA) private data: {title: string; msg: string;},
                private dialogRef: MatDialogRef<ResponseComponent>) {
    if (data?.title)
      this.title = data.title;
    if (data?.msg)
      this.msg = data.msg;
  }

  closeDialog() {
    this.dialogRef.close();
  }

}
