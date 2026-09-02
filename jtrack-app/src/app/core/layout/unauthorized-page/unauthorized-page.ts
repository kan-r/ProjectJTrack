import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { RouterModule } from '@angular/router';

@Component({
  imports: [CommonModule, MatCardModule, RouterModule],
  selector: 'app-unauthorized-page',
  styleUrl: './unauthorized-page.css',
  templateUrl: './unauthorized-page.html',
})
export class UnauthorizedPage {}
