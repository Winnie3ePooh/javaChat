package com.mycompany.javachat;

import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

public class Users implements JSONAware {

    private Long id;
    private String login;
    private String pass;
    private String status;

    public Users(Long id, String login, String pass, String stts) {
        this.id = id;
        this.login = login;
        this.pass = pass;
        this.status = stts;
    }
    
    public Long getId() {
        return id;
    }

    public String getLOGIN() {
        return login;
    }
    
    public String getPASS(){
        return pass;
    }
    
    public String getSTTS(){
        return status;
    }

    @Override
    public String toJSONString() {
        StringBuilder sb = new StringBuilder();

        sb.append("{");

        sb.append("\"").append(JSONObject.escape("id")).append("\"");
        sb.append(":");
        sb.append(id);

        sb.append(",");
        
        sb.append("\"").append("login").append("\"");
        sb.append(":");
        sb.append("\"").append(JSONObject.escape(login)).append("\"");
        
        sb.append("}");
        
        return sb.toString();
    }
}


