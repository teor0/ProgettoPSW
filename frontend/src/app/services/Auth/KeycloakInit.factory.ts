import { KeycloakService } from "keycloak-angular";
import { ADDRESS_AUTHENTICATION_SERVER, CLIENT_ID, REALM } from "src/app/helpers/constants";

export function initializeKeycloak(keycloak: KeycloakService){
    return ()=> keycloak.init({
        config: {
          url: ADDRESS_AUTHENTICATION_SERVER,
          realm: REALM,
          clientId: CLIENT_ID,
        },
        initOptions:{
          checkLoginIframe: false
        },
        enableBearerInterceptor: true,
        bearerPrefix: 'Bearer'
      });
  //}
}
