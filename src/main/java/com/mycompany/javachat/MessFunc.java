package com.mycompany.javachat;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class MessFunc {
    
    private static final String dbUrl = "jdbc:h2:~/test7";

    /**
     * Конструктор класса, регистрирует драйвер БД.
     */
    public MessFunc() {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MessFunc.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Вспомогательный метод для закрытия соединений с БД.
     * @param closeable 
     */
    private static void closeQuietly(Connection closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (SQLException ex) {
                // ignore
            }
        }
    }
    
    /**
     * Создать БД.
     * @return в случае успеха возвражает пустой JSON объект, иначе null.
     * st.execute("DROP TABLE PUBLIC.PEOPLE");
     */
    public JSONObject createDB() {
        Connection conn = null;
        JSONObject result = null;
        try {
            conn = DriverManager.getConnection(dbUrl);
            Statement st = conn.createStatement();
            st.execute("create table messages(id INT PRIMARY KEY AUTO_INCREMENT, nick varchar(255),"
                    + "mess varchar(255), dt LONG)");
            
            result = new JSONObject();
        } catch (SQLException ex) {
            Logger.getLogger(MessFunc.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeQuietly(conn);
        }
        return result;
    }

    /**
     * Получить список контактов.
     * @param find
     * @return массив объектов JSON.
     */
    public JSONArray list(Long find) {
        Connection conn = null;
        JSONArray list = null;
        try {
            conn = DriverManager.getConnection(dbUrl);
            String q = "SELECT * FROM messages";
            PreparedStatement st = conn.prepareStatement(q);
            ResultSet rs = st.executeQuery();
         
            list = new JSONArray();
            while (rs.next()) {
                Mess c = new Mess(rs.getLong("ID"),
                        rs.getString("NICK"),
                        rs.getString("MESS"),
                        rs.getLong("DT"));
                list.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MessFunc.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeQuietly(conn);
        }
        return list;
    }

    /**
     * Добавить контакт.
     * @param c контакт, в качестве id любое значение или null.
     * @return в случае успеха возвражает id контакта в формате JSON, иначе null.
     */
    public JSONObject add(Mess c) {
        Connection conn = null;
        JSONObject result = null;
        try {
            conn = DriverManager.getConnection(dbUrl);
            
            String q = "INSERT INTO messages(nick,mess,dt) VALUES(?,?,?)";
            PreparedStatement st = conn.prepareStatement(q);

            st.setString(1, c.getNICK());
            st.setString(2, c.getMESS());
            st.setLong(3,c.getDT());
            st.execute();
            
            long id = -1;
            ResultSet rs = st.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            
            result = new JSONObject();
            result.put("id", id);
        } catch (SQLException ex) {
            Logger.getLogger(MessFunc.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeQuietly(conn);
        }
        return result;
    }
    
}
