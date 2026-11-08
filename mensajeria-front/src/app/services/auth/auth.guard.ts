import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const authGuard: CanActivateFn = () => {
  const router = inject(Router);
  if (sessionStorage.getItem('token')) {
    return true;
  }
  return router.createUrlTree(['']);
};

export const noAuthGuard: CanActivateFn = () => {
  const router = inject(Router);
  return sessionStorage.getItem('token') ? router.createUrlTree(['/chats']) : true;
};
