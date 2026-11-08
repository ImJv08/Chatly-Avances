import {ChangeDetectorRef, Component, inject, OnInit} from '@angular/core';
import { Router } from '@angular/router';


@Component({
  selector: 'app-estados',
  standalone: false,
  templateUrl: './estados.html',
  styleUrl: './estados.css'
})
export class Estados {
  modal: 'estados' | null = null;
}
