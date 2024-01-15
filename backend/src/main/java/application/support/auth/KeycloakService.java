package application.support.auth;

import net.minidev.json.*;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.*;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Service
public class KeycloakService {

    public JSONObject getTokenAsUser(String username,String password){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String,String> body= new LinkedMultiValueMap<>();
        body.add("username",username);
        body.add("password",password);
        body.add("client_id", Constants.CLIENT_ID);
        body.add("client_secret",Constants.CLIENT_SECRET);
        body.add("grant_type","password");
        HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<JSONObject> result = restTemplate.postForEntity(Constants.REQUEST_TOKEN, request, JSONObject.class);
        return result.getBody();
    }

    public JSONObject getToken(){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(Constants.CLIENT_ID,Constants.CLIENT_SECRET);
        MultiValueMap<String,String> body= new LinkedMultiValueMap<>();
        body.add("grant_type","client_credentials");
        HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<JSONObject> result = restTemplate.postForEntity(Constants.REQUEST_TOKEN, request, JSONObject.class);
        return result.getBody();
    }


    @SuppressWarnings("unchecked")
    public String getUserId(JSONObject token, String username) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth((String)token.get("access_token"));
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> request = new HttpEntity<>("",headers);
        ResponseEntity<JSONArray> response= restTemplate.exchange(Constants.GET_USER_ID + username, HttpMethod.GET, request, JSONArray.class);
        LinkedHashMap<String, String> r = (LinkedHashMap<String, String>) response.getBody().get(0);
        return r.get("id");
    }

    @SuppressWarnings("unchecked")
    public JSONObject getClientRoles(JSONObject token, String role){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth((String)token.get("access_token"));
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> request = new HttpEntity<>(null,headers);
        ResponseEntity<JSONArray> response=restTemplate
                .exchange(Constants.GET_ROLES,HttpMethod.GET,request,JSONArray.class);
        for(int i=0; i<response.getBody().size(); i++) {
            LinkedHashMap<String, String> r = (LinkedHashMap<String, String>) response.getBody().get(i);
            if(r.get("name").equals(role)) {
                return new JSONObject(r);
            }
        }
        return null;
    }

    public HttpStatusCode registerUser(JSONObject token,String username,String firstName,String lastName,String email, String password,String role){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth((String)token.get("access_token"));
        JSONObject user= new JSONObject();
        JSONArray cred= new JSONArray();
        JSONObject pass= new JSONObject();
        pass.put("type","password");
        pass.put("value",password);
        System.out.println(password);
        cred.add(pass);
        user.put("username",username);
        user.put("firstName",firstName);
        user.put("lastName",lastName);
        user.put("email",email);
        user.put("credentials",cred);
        user.put("enabled",true);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> request = new HttpEntity<>(user.toString(), headers);
        restTemplate.postForEntity(Constants.REQUEST_USER, request, HttpStatusCode.class);
        return assignRole(token,username,role);
    }

    public HttpStatusCode assignRole(JSONObject token, String username,String role){
        String id_user= getUserId(token,username);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth((String)token.get("access_token"));
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        JSONObject roleInfo= getClientRoles(token,role);
        JSONArray body= new JSONArray();
        body.add(roleInfo);
        HttpEntity<JSONArray> request = new HttpEntity<>(body,headers);
        ResponseEntity<JSONObject> response=restTemplate
                .postForEntity(Constants.REQUEST_USER +"/"+id_user+Constants.END_ROLE_URL,
                        request, JSONObject.class);
        return response.getStatusCode();
    }

    public HttpStatusCode deleteUser(JSONObject token,String username){
        String id_user= getUserId(token,username);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth((String)token.get("access_token"));
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<JSONArray> request = new HttpEntity<>(headers);
        ResponseEntity<JSONObject> response=restTemplate
                .exchange(Constants.REQUEST_USER+"/"+id_user,HttpMethod.DELETE,request,JSONObject.class);
        System.out.println(response.getStatusCode());
        return response.getStatusCode();
    }

    /* NO USAGE METHODS
    public String getRefreshToken(){
        return (String) getToken().get("refresh_token");
    }

    public String getRefreshTokenAsUser(String username, String password){
        return (String) getTokenAsUser(username,password).get("refresh_token");
    }

    public HttpStatusCode deleteRole(JSONObject token, String username,String role){
        String id_user= getUserId(token,username);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth((String)token.get("access_token"));
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        JSONObject roleInfo= getClientRoles(token,role);
        JSONArray body= new JSONArray();
        body.add(roleInfo);
        HttpEntity<JSONArray> request = new HttpEntity<>(body,headers);
        ResponseEntity<JSONObject> response=restTemplate
                .exchange(Constants.REQUEST_USER +"/"+id_user+Constants.END_ROLE_URL,
                        HttpMethod.DELETE,request,JSONObject.class);
        return response.getStatusCode();
    }*/

}
