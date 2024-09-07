/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dto.Response_DTO;
import entity.Model;
import entity.Product;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.HibernateUtil;
import model.Validations;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Manujaya
 */
@WebServlet(name = "LoadSingleProduct", urlPatterns = {"/LoadSingleProduct"})
public class LoadSingleProduct extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        Gson gson = new Gson();
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        try {

            String productId = request.getParameter("id");

            if (Validations.isInteger(productId)) {

                Product product = (Product) session.get(ProductListing.class, Integer.parseInt(productId)); //get product's object

                product.getUser().setPassword(null);
                product.getUser().setVerification(null);
                product.getUser().setEmail(null);
                
                Criteria criteria1 = session.createCriteria(Model.class); //get model class
                criteria1.add(Restrictions.eq("category", product.getModel().getCategory())); //අපි අරන් තියෙන model එකට අදාල category එක ගන්නවා.
                List<Model> modelList = criteria1.list();

                Criteria criteria2 = session.createCriteria(Product.class);
                criteria2.add(Restrictions.in("model", modelList));
                criteria2.setMaxResults(4);

                List<Product> productList = criteria2.list();

                for (Product product1 : productList) {
                    product1.getUser().setPassword(null);
                    product1.getUser().setVerification(null);
                    product1.getUser().setEmail(null);                   
                }
                
                JsonObject jsonObject = new JsonObject();
                jsonObject.add("product", gson.toJsonTree(product));
                jsonObject.add("productList", gson.toJsonTree(productList));
                
                response.setContentType("application/json");
                response.getWriter().write(gson.toJson(productList));

            } else {

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
