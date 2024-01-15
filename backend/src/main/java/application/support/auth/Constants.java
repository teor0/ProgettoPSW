package application.support.auth;

public class Constants{

    public static String ID_CLIENT="";
    public static String CLIENT_NAME="";
    public static String ADDRESS_AUTHENTICATION_SERVER= "http://localhost:8081/";
    public static String REALM="";
    public static String CLIENT_ID = CLIENT_NAME;
    public static String CLIENT_SECRET="";
    static final String ADMIN_REALMS= "/admin/realms/";


    static final String REQUEST_TOKEN = ADDRESS_AUTHENTICATION_SERVER +"/realms/" + REALM + "/protocol/openid-connect/token";
    static final String GET_ROLES= ADDRESS_AUTHENTICATION_SERVER+ ADMIN_REALMS +REALM+"/clients/"+ID_CLIENT+"/roles/";
    static final String REQUEST_USER = ADDRESS_AUTHENTICATION_SERVER+ADMIN_REALMS+REALM+"/users";
    static final String END_ROLE_URL="/role-mappings/clients/"+ID_CLIENT;
    static final String GET_USER_ID = REQUEST_USER+"/?username=";



}
