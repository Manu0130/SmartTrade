/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dto.Response_DTO;
import entity.Category;
import entity.Color;
import entity.Product_Condition;
import entity.Model;
import entity.Storage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

/**
 *
 * @author Manujaya
 */
@WebServlet(name = "LoadFeatures", urlPatterns = {"/LoadFeatures"})
public class LoadFeatures extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Gson gson = new Gson();

        Session session = HibernateUtil.getSessionFactory().openSession();
        
        Criteria criteria1 = session.createCriteria(Category.class); //select *
        criteria1.addOrder(Order.asc("name")); //set as ascending order
        List<Category> categoryList = criteria1.list();

        Criteria criteria2 = session.createCriteria(Model.class); //select *
        criteria2.addOrder(Order.asc("name")); //set as ascending order
        List<Model> modelList = criteria2.list();

        Criteria criteria3 = session.createCriteria(Color.class); //select *
        criteria3.addOrder(Order.asc("name")); //set as ascending order
        List<Color> colorList = criteria3.list();

        Criteria criteria4 = session.createCriteria(Storage.class); //select *
        criteria4.addOrder(Order.asc("id")); //set as ascending order
        List<Storage> storageList = criteria4.list();

        Criteria criteria5 = session.createCriteria(Product_Condition.class); //select *
        criteria5.addOrder(Order.asc("name")); //set as ascending order
        List<Product_Condition> conditionList = criteria5.list();
        
        JsonObject jsonObject =  new JsonObject();
        jsonObject.add("categoryList", gson.toJsonTree(categoryList));
        jsonObject.add("modelList", gson.toJsonTree(modelList));
        jsonObject.add("colorList", gson.toJsonTree(colorList));
        jsonObject.add("storageList", gson.toJsonTree(storageList));
        jsonObject.add("conditionList", gson.toJsonTree(conditionList));
        
        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(jsonObject));
        
        session.close();
        
    }

}
