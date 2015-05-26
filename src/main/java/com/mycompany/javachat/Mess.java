package com.mycompany.javachat;

import java.util.Date;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

public class Mess implements JSONAware {

    private Long id;
    private String nick;
    private String mess;
    private Long dt;

    /**
     * Конструктор контакта.
     * @param id идентификатор контакта.
     * @param fio ФИО.
     */
    public Mess(Long id, String nick, String mess, Long dt) {
        this.id = id;
        this.nick = nick;
        this.mess = mess;
        this.dt = dt;
    }
    
    public Long getId() {
        return id;
    }

    public String getNICK() {
        return nick;
    }
    
    public String getMESS(){
        return mess;
    }
    
    public Long getDT(){
        return dt;
    }

    @Override
    public String toJSONString() {
        StringBuilder sb = new StringBuilder();

        sb.append("{");

        sb.append("\"").append(JSONObject.escape("id")).append("\"");
        sb.append(":");
        sb.append(id);

        sb.append(",");
        
        sb.append("\"").append("nick").append("\"");
        sb.append(":");
        sb.append("\"").append(JSONObject.escape(nick)).append("\"");

        sb.append(",");
        
        sb.append("\"").append("mess").append("\"");
        sb.append(":");
        sb.append("\"").append(JSONObject.escape(mess)).append("\"");
        
        sb.append(",");
        
        sb.append("\"").append("dt").append("\"");
        sb.append(":");
        sb.append(dt);
        
        sb.append("}");

        return sb.toString();
    }
}
