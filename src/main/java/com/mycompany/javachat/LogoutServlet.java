package com.mycompany.javachat;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LogoutServlet
 */

public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
        @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/html");
        UsersFunc uf = new UsersFunc();
    	Cookie loginCookie = null;
    	Cookie[] cookies = request.getCookies();
        System.out.println(cookies[1].getValue());
        uf.setStatus(cookies[1].getValue(), "off");
    	if(cookies != null){
    	for(Cookie cookie : cookies){
    		if(cookie.getName().equals("user")){
    			loginCookie = cookie;
    			break;
    		}
    	}
    	}
    	if(loginCookie != null){
    		loginCookie.setMaxAge(0);
        	response.addCookie(loginCookie);
    	}
    	response.sendRedirect("index.html");
    }

}
