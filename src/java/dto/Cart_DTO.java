package dto;

import entity.Product;
import java.io.Serializable;

public class Cart_DTO implements Serializable{
    
    private Product product;
    
    private int qty;
    
    public Cart_DTO(){ }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
    
}
