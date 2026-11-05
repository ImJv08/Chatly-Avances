import { ApplicationConfig, importProvidersFrom, provideZoneChangeDetection } from '@angular/core';
import { AppModule } from './app.module';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    importProvidersFrom(AppModule)
  ]
};

