import { Component } from '@angular/core';

@Component({
  selector: 'app-chats-principal',
  standalone: false,
  templateUrl: './chats-principal.html',
  styleUrl: './chats-principal.css'
})
export class ChatsPrincipal {
  modal: 'contacto' | 'grupo' | 'bloqueados' | null = null;
  verBloqueados = false;
}

