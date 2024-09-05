/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.Response_DTO;
import dto.User_DTO;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Manujaya
 */
@WebServlet(name = "SignIn", urlPatterns = {"/SignIn"})
public class SignIn extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        Response_DTO response_DTO = new Response_DTO();
        
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        
        User_DTO user_DTO = gson.fromJson(request.getReader(), User_DTO.class);
        
        if (user_DTO.getEmail().isEmpty()) {
            response_DTO.setContent("Please Enter Your Email");
        } else if (user_DTO.getPassword().isEmpty()) {
            response_DTO.setContent("Please Enter Your Password");
        } else {
            Session session = HibernateUtil.getSessionFactory().openSession();
            
            Criteria criteria1 = session.createCriteria(User.class);
            criteria1.add(Restrictions.eq("email", user_DTO.getEmail()));
            criteria1.add(Restrictions.eq("password", user_DTO.getPassword()));
            
            if (!criteria1.list().isEmpty()) {
                User user = (User) criteria1.list().get(0);
                
                if (!user.getVerification().equals("Verified")) {
                    //not verified
                    request.getSession().setAttribute("email", user_DTO.getEmail());
                    response_DTO.setContent("Unverified");
                } else {
                    //verified
                    user_DTO.setFirst_name(user.getFirst_name());
                    user_DTO.setLast_name(user.getLast_name());
                    user_DTO.setPassword(null);
                    request.getSession().setAttribute("user", user_DTO);
                    
                    response_DTO.setSuccess(true);
                    response_DTO.setContent("Sign In Success");
                }
            } else {
                response_DTO.setContent("Invalid Details ! Please Tye Again");
                
            }
            
        }
        
        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(response_DTO));
        System.out.println(gson.toJson(response_DTO));
        
    }
    
}
