package com.Project.Project.AppleCouponShop.Controllers;

import com.Project.Project.AppleCouponShop.beans.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins =  "http://localhost:3000", allowedHeaders = "*")
public abstract class ClientController {

    @Autowired
    private AdminController adminController;

    @Autowired
    private CompanyController companyController;

//    @Autowired
//    private CustomerController customerController;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody UserDetails userDetails) {
        if(this instanceof AdminController) {
            adminController.login(userDetails);
        } else if (this instanceof CompanyController) {
            companyController.login(userDetails);
        }
        else if (this instanceof CustomerController) {
            companyController.login(userDetails);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
