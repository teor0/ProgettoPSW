//utility

//authentication
export const REALM ="ProjectRealm";
export const ID_CLIENT="90d4e5dc-b9ef-4311-b263-c0de6f93cb89";
export const CLIENT_SECRET ="CDq6yNvEQZyKoTtTUgizCqNaYknacEK7";
export const CLIENT_ID ="Project";

//addresses
export const ADDRESS_SERVER = "https://localhost:8082";
export const POST_LOGOUT_REDIRECT = "http://localhost:4200/Home";
export const ADDRESS_AUTHENTICATION_SERVER = "http://localhost:8081";
export const ADMIN_REALMS= "/admin/realms/";
export const DELETE_USER= ADDRESS_AUTHENTICATION_SERVER+ADMIN_REALMS+REALM+'/users/';
export const GET_TOKEN = ADDRESS_AUTHENTICATION_SERVER +"/realms/" + REALM + "/protocol/openid-connect/token";
export const GET_ROLES= ADDRESS_AUTHENTICATION_SERVER+ ADMIN_REALMS +REALM+"/clients/"+ID_CLIENT+"/roles/";
export const ADDRESS_REGISTRATION= ADDRESS_AUTHENTICATION_SERVER + ADMIN_REALMS+ REALM+ "/users";
export const ADDRESS_ID_CLIENT= ADDRESS_AUTHENTICATION_SERVER + ADMIN_REALMS+REALM+"/clients?clientId="+CLIENT_ID;
export const ADDRESS_ROLE= ADDRESS_AUTHENTICATION_SERVER+ADMIN_REALMS+REALM+"/users/";
export const END_ROLE_URL="/role-mappings/clients/"+ID_CLIENT;
export const GET_USER_ID = ADDRESS_AUTHENTICATION_SERVER+ ADMIN_REALMS+REALM+"/users/?username=";
export const END_SESSION = ADDRESS_AUTHENTICATION_SERVER+"/realms/"+REALM+"/protocol/openid-connect/logout";

//requests
export const REQUEST_USER = "/user";
export const REQUEST_LOGIN ="/Login";
export const REQUEST_SEARCH ="/search";
export const REQUEST_PRODUCT = "/products";
export const REQUEST_ORDER = "/order";
export const REQUEST_ORDERPRODUCTS = "/order-product";
export const REQUEST_CART = "/cart";
