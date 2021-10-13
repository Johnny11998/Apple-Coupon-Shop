package com.Project.Project.AppleCouponShop.Controllers;

import com.Project.Project.AppleCouponShop.enums.Category;
import com.Project.Project.AppleCouponShop.enums.ClientType;
import com.Project.Project.AppleCouponShop.Exceptions.MajorException;
import com.Project.Project.AppleCouponShop.Services.CustomerServiceImpl;
import com.Project.Project.AppleCouponShop.Util.JWTutil;
import com.Project.Project.AppleCouponShop.beans.Coupon;
import com.Project.Project.AppleCouponShop.beans.Customer;
import com.Project.Project.AppleCouponShop.beans.UserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//http://localhost:8080/customer
@RestController
@RequestMapping("CUSTOMER")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class CustomerController extends ClientController {

    @Autowired
    private CustomerServiceImpl customerServiceImpl;

    private final JWTutil jwTutil;

    //http://localhost:8080/customer/login
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody UserDetails userDetails) {
        try {
            if (customerServiceImpl.login(userDetails.getEmail(), userDetails.getPassword())) {
                //go to database , get user by it's email and password
                String myToken = jwTutil.generateToken(new UserDetails(userDetails.getEmail(), ClientType.CUSTOMER));
                return new ResponseEntity<>(myToken, HttpStatus.OK);
            }
        } catch (MajorException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    //http://localhost:8080/customer/updateCustomer
    @PutMapping("updateCustomer")
    public ResponseEntity<?> updateCustomer(@RequestHeader(name = "Authorization") String token, @RequestBody Customer customer) throws MajorException {
        if (jwTutil.validateToken(token)) {
            try {
                customerServiceImpl.updateCustomer(customer);
            } catch (MajorException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
            return ResponseEntity.ok()
                    .header("Authorization", jwTutil.generateToken(new UserDetails(
                            jwTutil.extractEmail(token),
                            ClientType.CUSTOMER)))
                    .body(null);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    //http://localhost:8080/customer/addCouponPurchase
    @PostMapping("addCouponPurchase")
    public ResponseEntity<?> addCouponPurchase(@RequestHeader(name = "Authorization") String token, @RequestBody Coupon coupon) throws MajorException {
        if (jwTutil.validateToken(token)) {
            try {
                customerServiceImpl.addCouponPurchase(coupon);
            } catch (MajorException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
            return ResponseEntity.ok()
                    .header("Authorization", jwTutil.generateToken(new UserDetails(
                            jwTutil.extractEmail(token),
                            ClientType.CUSTOMER)))
                    .body(null);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    //http://localhost:8080/customer/getCustomerCoupons
    @GetMapping("getCustomerCoupons")
    public ResponseEntity<?> getCustomerCoupons(@RequestHeader("Authorization") String token) throws MajorException {
        if (jwTutil.validateToken(token)){
            return ResponseEntity.ok()
                    .header("Authorization", jwTutil.generateToken(new UserDetails(
                            jwTutil.extractEmail(token),
                            ClientType.CUSTOMER)))
                            .body(null);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


    //http://localhost:8080/customer/getCustomerCouponsByCategory/category
    @GetMapping("getCustomerCouponsByCategory/{category}")
    public ResponseEntity<?> getCustomerCoupons(@RequestHeader (name = "Authorization")String token, @PathVariable Category category) throws MajorException {
        if (jwTutil.validateToken(token)) {
            return  ResponseEntity.ok()
                    .header("Authorization", jwTutil.generateToken(new UserDetails(
                            jwTutil.extractEmail(token),
                            ClientType.CUSTOMER)))
                    .body(customerServiceImpl.getCustomerCoupons(category));
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    //http://localhost:8080/customer/getCustomerCouponsByPrice/maxPrice
    @GetMapping("getCustomerCouponsByPrice/{maxPrice}")
    public ResponseEntity<?> getCustomerCoupons(@RequestHeader (name = "Authorization") String token, @PathVariable double maxPrice) throws MajorException {
        if (jwTutil.validateToken(token)) {
            return ResponseEntity.ok()
                    .header("Authorization" , jwTutil.generateToken(new UserDetails(
                            jwTutil.extractEmail(token),
                            ClientType.CUSTOMER)))
                    .body(customerServiceImpl.getCustomerCoupons(maxPrice));
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    //http://localhost:8080/customer/getDetails
    @GetMapping("getDetails")
    public ResponseEntity<?> getCustomerDetails(@RequestHeader (name = "Authorization") String token) throws MajorException {
        if (jwTutil.validateToken(token)) {
            return ResponseEntity.ok()
                    .header("Authorization" , jwTutil.generateToken(new UserDetails(
                            jwTutil.extractEmail(token),
                            ClientType.CUSTOMER)))
                    .body(customerServiceImpl.getCustomerDetails());
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


}
