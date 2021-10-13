package com.Project.Project.AppleCouponShop.Controllers;

import com.Project.Project.AppleCouponShop.enums.ClientType;
import com.Project.Project.AppleCouponShop.Exceptions.MajorException;
import com.Project.Project.AppleCouponShop.Exceptions.TokenExpiredException;
import com.Project.Project.AppleCouponShop.Services.AdminServiceImpl;
import com.Project.Project.AppleCouponShop.Util.JWTutil;
import com.Project.Project.AppleCouponShop.beans.Company;
import com.Project.Project.AppleCouponShop.beans.Customer;
import com.Project.Project.AppleCouponShop.beans.UserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//http://localhost:8080/admin
@RestController
@RequestMapping("ADMIN")
@RequiredArgsConstructor
@CrossOrigin(origins = "Http://localhost:3000", allowedHeaders = "*")
public class AdminController extends ClientController {

    @Autowired
    private AdminServiceImpl adminServiceImpl;

    private final JWTutil jwTutil;

    //http://localhost:8080/admin/login
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody UserDetails userDetails) {
        try {
            if (adminServiceImpl.login(userDetails.getEmail(), userDetails.getPassword())) {
                String myToken = jwTutil.generateToken(new UserDetails(
                        (userDetails.getEmail()),
                        userDetails.getPassword(),
                        ClientType.ADMIN));
                return new ResponseEntity<>(myToken, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        return null;
    }

    //http://localhost:8080/admin/addCompany
    @PostMapping("addCompany")
    public ResponseEntity<?> addCompany(@RequestHeader(name = "Authorization") String token, @RequestBody Company company) throws Exception {
        jwTutil.validateToken(token);
        try {
            return ResponseEntity.ok()
                    .header("Authorization", jwTutil.generateToken(new UserDetails(
                            jwTutil.extractEmail(token),
                            ClientType.ADMIN)))
                    .body(adminServiceImpl.addCompany(company));
        } catch (MajorException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //http://localhost:8080/admin/updateCompany
    @PutMapping("updateCompany")
    public ResponseEntity<?> updateCompany(@RequestHeader(name = "Authorization") String token, @RequestBody Company company) throws Exception {
        if (jwTutil.validateToken(token)) {
            try {
                adminServiceImpl.updateCompany(company);
            } catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }

            return ResponseEntity.ok()
                    .header("Authorization", jwTutil.generateToken(new UserDetails(
                            jwTutil.extractEmail(token),
                            ClientType.ADMIN)))
                    .body(null);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    //http://localhost:8080/admin/deleteCompany
    @DeleteMapping("deleteCompany")
    public ResponseEntity<?> deleteCompany(@RequestHeader(name = "Authorization") String token, @PathVariable Company company) throws Exception {
        if (jwTutil.validateToken(token)) {
            try {
                adminServiceImpl.deleteCustomer(company.getEmail());
            } catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }

            return ResponseEntity.ok()
                    .header("Authorization", jwTutil.generateToken(new UserDetails(
                            jwTutil.extractEmail(token),
                            ClientType.ADMIN)))
                    .body(null);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    //http://localhost:8080/admin/getAllCompanies
    @GetMapping("getAllCompanies")
    public ResponseEntity<?> getAllCompanies(@RequestHeader(name = "Authorization") String token) throws Exception {
        System.out.println("I'm Out");
        if (jwTutil.validateToken(token)) {
            System.out.println("I'm in");
            return ResponseEntity.ok()
                    .header("Authorization", jwTutil.generateToken(new UserDetails(
                            jwTutil.extractEmail(token),
                            ClientType.ADMIN)))
                    .body(adminServiceImpl.getAllCompanies());

        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
//------------------------------------------------------------------------ copied


    //http://localhost:8080/admin/singleCompany/companyId
    @GetMapping("getOneCompany/{companyId}")
    public ResponseEntity<?> getOneCompany(@RequestHeader(name = "Authorization") String token, @PathVariable int companyId) throws Exception {
        if (jwTutil.validateToken(token)) {
            try {
                return ResponseEntity.ok()
                        .header("Authorization", jwTutil.generateToken(new UserDetails(
                                jwTutil.extractEmail(token),
                                ClientType.ADMIN
                        )))
                        .body(adminServiceImpl.getOneCompany(companyId));
            } catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    //http://localhost:8080/ADMIN/adminUpdateCustomer
    @PutMapping("adminUpdateCustomer")
    public ResponseEntity<?> updateCustomer(@RequestHeader(name = "Authorization") String token, @RequestBody Customer customer) throws TokenExpiredException, Exception {
        if (jwTutil.validateToken(token)) {
            adminServiceImpl.updateCustomer(customer);
            return ResponseEntity.ok()
                    .header("Authorization", jwTutil.generateToken(new UserDetails(
                            jwTutil.extractEmail(token),
                            ClientType.ADMIN)))
                    .body(null);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    //http://localHost:8080/admin/deleteCustomer/customerId
    @DeleteMapping("deleteCustomer/{email}")
    public ResponseEntity<?> deleteCustomer(@RequestHeader(name = "Authorization") String token, @PathVariable int customerId) throws Exception {
        if (jwTutil.validateToken(token)) {
            try {
                adminServiceImpl.deleteCustomer(customerId);
            } catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
            return ResponseEntity.ok()
                    .header("Authorization", jwTutil.generateToken(new UserDetails(
                            jwTutil.extractEmail(token),
                            ClientType.ADMIN
                    )))
                    .body(null);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


    //http://localHost:8080/admin/gatAllCustomers
    @GetMapping("getAllCustomers")
    public ResponseEntity<?> getAllCustomers(@RequestHeader(name = "Authorization") String token) throws Exception {
        if (jwTutil.validateToken(token)) {
            return ResponseEntity.ok()
                    .header("Authorization", jwTutil.generateToken(new UserDetails(
                            jwTutil.extractEmail(token),
                            ClientType.ADMIN
                    )))
                    .body(adminServiceImpl.getAllCustomeris());
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    //http://localHost:8080/admin/getOneCustomer/customerId
    @GetMapping("getOneCustomer/{customerId}")
    public ResponseEntity<?> getOneCustomer(@RequestHeader(name = "Authorization") String token, @PathVariable int customerId) throws Exception {
        if (jwTutil.validateToken(token)) {
            try {
                return ResponseEntity.ok()
                        .header("Authorization", jwTutil.generateToken(new UserDetails(
                                jwTutil.extractEmail(token),
                                ClientType.ADMIN
                        )))
                        .body(adminServiceImpl.getOneCustomer(customerId));
            } catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

}