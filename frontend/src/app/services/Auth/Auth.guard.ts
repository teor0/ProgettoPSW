import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { KeycloakAuthGuard, KeycloakService } from 'keycloak-angular';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard extends KeycloakAuthGuard {

  constructor(
    protected override readonly router: Router,
    protected readonly keycloak: KeycloakService
  ) {
    super(router, keycloak);
  }

  async isAccessAllowed(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Promise<boolean | UrlTree> {

    if (!this.authenticated) {
      await this.keycloak.login({
        redirectUri: 'http://localhost:4200'
      });
      //await this.keycloak.register({redirectUri: 'https://localhost:4200'});
    }

     // Get the roles required from the route.
     const requiredRoles = route.data['roles'];

     // Allow the user to proceed if no additional roles are required to access the route.
     if (!Array.isArray(requiredRoles) || requiredRoles.length === 0) {
       return true;
     }

     // Allow the user to proceed if all the required roles are present.
     return requiredRoles.every((role) => this.roles.includes(role));
  }
}
