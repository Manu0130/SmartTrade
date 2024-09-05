package dto;

import com.google.gson.annotations.Expose;
import java.io.Serializable;

public class User_DTO implements Serializable{

    @Expose
    private String first_name;
    
    @Expose
    private String last_name;
    
    @Expose
    private String email;
    
    @Expose (deserialize = true, serialize = false)
    private String password;
    
    public User_DTO(){}

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() { 
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
