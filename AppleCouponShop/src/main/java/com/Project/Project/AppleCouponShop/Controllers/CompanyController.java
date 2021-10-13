package com.Project.Project.AppleCouponShop.Controllers;

import com.Project.Project.AppleCouponShop.enums.Category;
import com.Project.Project.AppleCouponShop.enums.ClientType;
import com.Project.Project.AppleCouponShop.Exceptions.MajorException;
import com.Project.Project.AppleCouponShop.Services.CompanyServiceImpl;
import com.Project.Project.AppleCouponShop.Util.JWTutil;
import com.Project.Project.AppleCouponShop.beans.Coupon;
import com.Project.Project.AppleCouponShop.beans.UserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

//http://localhost:8080/compnay
@RestController
@RequestMapping("Company")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class CompanyController extends ClientController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private CompanyServiceImpl companyService;

    private final JWTutil jwTutil;

    //http://localhost:8080/company/login
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody UserDetails userDetails) {
        try {
            if (companyService.login(userDetails.getEmail(), userDetails.getPassword())) {
                //Go to DATABASE , take user email and password
                String myToken = jwTutil.generateToken(new UserDetails(userDetails.getEmail(), ClientType.COMPANY));
                return new ResponseEntity<>(myToken, HttpStatus.ACCEPTED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return null;
    }
    //http://localhost:8080/company/addCompany
    @PostMapping("addCoupon")
    public ResponseEntity<?> addCoupon(@RequestHeader(name = "Authorization") String token,
                                       @RequestParam Category category,
                                       @RequestParam String title,
                                       @RequestParam String description,
                                       @RequestParam Date startData,
                                       @RequestParam Date endData,
                                       @RequestParam int amount,
                                       @RequestParam double price,
                                       @RequestParam MultipartFile image
    ) throws Exception {
        if (jwTutil.validateToken(token)) {
            Coupon coupon = new Coupon(companyService.getCompanyDetails().getCompanyId(), amount, price, category, title, description, image.getBytes(), startData, endData);
            try {
                return ResponseEntity.ok()
                        .header("Authorization", jwTutil.generateToken(new UserDetails(
                                jwTutil.extractEmail(token),
                                ClientType.COMPANY)))
                        .body(companyService.addCoupon(coupon));
            } catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    //http://localhost:8080/company/updateCompany
    @PutMapping("updateCompany")
    public ResponseEntity<?> updateCoupon(@RequestHeader(name = "Authorization") String token, @RequestBody Coupon coupon) throws MajorException {
        if (jwTutil.validateToken(token)) {
            try {
                companyService.updateCoupon(coupon);
            } catch (MajorException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }

            return ResponseEntity.ok()
                    .header("Authorization", jwTutil.generateToken(new UserDetails(
                            jwTutil.extractEmail(token),
                            ClientType.COMPANY)))
                    .body(null);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    //http://localhost:8080/company/deleteCoupon/couponId
    @DeleteMapping("deleteCoupon/{couponId}")
    public ResponseEntity<?> deleteCoupon(@RequestHeader(name = "Authorization") String token, @RequestBody int couponId) throws MajorException {
        if (jwTutil.validateToken(token)) {
            try {
                companyService.deleteCoupon(couponId);
            } catch (MajorException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }

            return ResponseEntity.ok()
                    .header("Authorization", jwTutil.generateToken(new UserDetails(
                            jwTutil.extractEmail(token),
                            ClientType.COMPANY)))
                    .body(null);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    //http://localhost:8080/company/getCompanyCoupon
    @GetMapping("getCompanyCoupon")
    public ResponseEntity<?> getCompanyCoupon(@RequestHeader(name = "Authorization") String token) throws MajorException {
        if (jwTutil.validateToken(token)) {
            return ResponseEntity.ok()
                    .header("Authorization", jwTutil.generateToken(new UserDetails(
                            jwTutil.extractEmail(token),
                            ClientType.COMPANY)))
                    .body(companyService.getCompanyCoupon());
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    //http://localhost:8080/company/getCompanyCouponsByCategory/category
    @GetMapping("getCompanyCouponsByCatagory/{category}")
    public ResponseEntity<?> getCompanyCoupons(@RequestHeader(name = "Authorization") String token, @PathVariable Category category) throws MajorException {
        if (jwTutil.validateToken(token)) {
            return ResponseEntity.ok()
                    .header("Authorization", jwTutil.generateToken(new UserDetails(
                            jwTutil.extractEmail(token),
                            ClientType.COMPANY)))
                    .body(companyService.getCompanyCoupons(category));
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    //http://localhost:8080/company/getCompanyCouponsByPrice/maxPrice
    @GetMapping("getCompanyCouponsByPrice/{maxPrice}")
    public ResponseEntity<?> getCompanyCoupons(@RequestHeader(name = "Authorization") String token, @PathVariable double maxPrice) throws MajorException {
        if (jwTutil.validateToken(token)) {
            return ResponseEntity.ok()
                    .header("Authorization", jwTutil.generateToken(new UserDetails(
                            jwTutil.extractEmail(token),
                            ClientType.COMPANY)))
                    .body(companyService.getCompanyCoupons(maxPrice));
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    //http://localhost:8080/company/companyDetails
    @GetMapping("getCompanyCoupons")
    public ResponseEntity<?> getCompanyDetails(@RequestHeader(name = "Authorization") String token) throws MajorException {
        if (jwTutil.validateToken(token)) {
            return ResponseEntity.ok()
                    .header("Authorization", jwTutil.generateToken(new UserDetails(
                            jwTutil.extractEmail(token),
                            ClientType.COMPANY)))
                    .body(companyService.getCompanyDetails());
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
