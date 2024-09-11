/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entity.Category;
import entity.Color;
import entity.Product;
import entity.Product_Condition;
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
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

/**
 *
 * @author Manujaya
 */
@WebServlet(name = "LoadData", urlPatterns = {"/LoadData"})
public class LoadData extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("success", false);
        
        Gson gson = new Gson();
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        //main code
        
        //Get Category List from DB
        Criteria criteria1 = session.createCriteria(Category.class);
        List<Category> categoryList = criteria1.list();
        jsonObject.add("categoryList", gson.toJsonTree(categoryList));

        //Get Condition List from DB
        Criteria criteria2 = session.createCriteria(Product_Condition.class);
        List<Product_Condition> conditionList = criteria2.list();
        jsonObject.add("conditionList", gson.toJsonTree(conditionList));
        
        //Get Color List from DB
        Criteria criteria3 = session.createCriteria(Color.class);
        List<Color> colorList = criteria3.list();
        jsonObject.add("colorList", gson.toJsonTree(colorList));

        //Get Storage List from DB
        Criteria criteria4 = session.createCriteria(Storage.class);
        List<Storage> storageList = criteria4.list();
        jsonObject.add("storageList", gson.toJsonTree(storageList));

        //Get Product List from DB
        Criteria criteria5 = session.createCriteria(Product.class);
        
        //get latest products
        criteria5.addOrder(Order.desc("id"));
        jsonObject.addProperty("allProductCount", criteria5.list().size());
        
        //set product range
        criteria5.setFirstResult(1);
        criteria5.setMaxResults(6);
        
        List<Product> productList = criteria5.list();
        
        //Remove user from product
        for(Product product : productList){
            product.setUser(null);
        }
        
        jsonObject.add("productList", gson.toJsonTree(productList));
                jsonObject.addProperty("success", true);

        
        
        //main code
        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(jsonObject));

    }

 
    
}
