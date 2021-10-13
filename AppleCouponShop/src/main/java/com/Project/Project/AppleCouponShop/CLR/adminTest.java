package com.Project.Project.AppleCouponShop.CLR;


import com.Project.Project.AppleCouponShop.Exceptions.MajorException;
import com.Project.Project.AppleCouponShop.Login.LoginManager;

import com.Project.Project.AppleCouponShop.Services.AdminServiceImpl;
import com.Project.Project.AppleCouponShop.Services.CompanyServiceImpl;
import com.Project.Project.AppleCouponShop.Services.CustomerServiceImpl;
import com.Project.Project.AppleCouponShop.Util.Art;
import com.Project.Project.AppleCouponShop.beans.Company;
import com.Project.Project.AppleCouponShop.beans.Customer;

import com.Project.Project.AppleCouponShop.enums.ClientType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class adminTest implements CommandLineRunner {

    private AdminServiceImpl adminServiceImpl;

    @Autowired
    private CompanyServiceImpl companyServiceImpl;

    @Autowired
    private CustomerServiceImpl customerServiceImpl;

    @Autowired
    LoginManager loginManager;



    @Override
    public void run(String... args) throws MajorException {

        //----------------------- CREATING COMPANIES -----------------------

        Company company = Company.builder()
                .companyName("Aberman Industries")
                .email("Aberman@Industries.com")
                .password("12345")
                .build();

        Company company1 = Company.builder()
                .companyName("Kader Industries")
                .email("Kader@Industries.com")
                .password("12345")
                .build();

        Company company2 = Company.builder()
                .companyName("Sela Industries")
                .email("Sela@Industries.com")
                .password("12345")
                .build();

        //----------------------- CREATING CUSTOMER'S -----------------------

        Customer customer = Customer.builder()
                .firstName("Jonathan")
                .lastName("Aberman")
                .email("aberman@aberman.com")
                .password("12345")
                .build();

        Customer customer1 = Customer.builder()
                .firstName("Segev")
                .lastName("Sela")
                .email("segev@sela.com")
                .password("54321")
                .build();

        Customer customer2 = Customer.builder()
                .firstName("Raziel")
                .lastName("Kader")
                .email("raziel@kader.com")
                .password("13579")
                .build();

        //----------------------------- LOGIN ----------------------------
        try {

            System.out.println("Checking Email and Password");
            try {
                adminServiceImpl = (AdminServiceImpl) loginManager.login("admin@admin.com", "admin",ClientType.ADMIN);
                System.out.println("Login successfully !");
            } catch (MajorException error) {
                System.out.println(error.getMessage());
            }

            //------------------------ ADDING COMPANY TO THE SYSTEM ----------------------

            System.out.println("==========================================");
            System.out.println("Adding company");
            try {
                adminServiceImpl.addCompany(company);
                System.out.println("Great");
            } catch (Exception error) {
                System.out.println(error.getMessage());
            }
            System.out.println("==========================================");

            //------------------------ UPDATE COMPANY TO THE SYSTEM ----------------------

            System.out.println("Updating Company");
            try {
                adminServiceImpl.updateCompany(company2);
                System.out.println("Great");
            } catch (Exception error) {
                System.out.println(error.getMessage());
            }
            System.out.println("==========================================");
            //------------------------ DELETE COMPANY TO THE SYSTEM ----------------------

            System.out.println("Deleting company");
            try {
                adminServiceImpl.deleteCompany(company.getEmail());
                System.out.println("Great");
            } catch (Exception error) {
                System.out.println(error.getMessage());
            }
            System.out.println("==========================================");

            //------------------------ GETTING ONE COMPANY FROM THE SYSTEM ----------------------

            System.out.println("Getting company");
            try {
                adminServiceImpl.getOneCompany(company.getCompanyId());
                System.out.println("Great");
            } catch (Exception error) {
                System.out.println(error.getMessage());
            }
            System.out.println("==========================================");

            //------------------------ GETTING ALL COMPANY FROM THE SYSTEM ----------------------

            System.out.println("Getting list of companies");
            try {
                adminServiceImpl.getAllCompanies();
                System.out.println("Great");
            } catch (Exception error) {
                System.out.println(error.getMessage());
            }

            //-----------------------------------------------------COMPANY--------------------------------------------------------

            System.out.println("==========================================");
            System.out.println(Art.adminTestArt);
            System.out.println("==========================================");

            //-----------------------------------------------------CUSTOMER-------------------------------------------------------

            //------------------------ ADD CUSTOMER TO THE SYSTEM ----------------------

            System.out.println("Adding customer");
            try {
                adminServiceImpl.addCustomer(customer);
                System.out.println("Great");
            } catch (Exception error) {
                System.out.println(error.getMessage());
            }
            System.out.println("==========================================");

            //------------------------ UPDATING CUSTOMER TO THE SYSTEM ----------------------

            System.out.println("Updating customer");
            try {
                adminServiceImpl.updateCustomer(customer2);
                System.out.println("Great");
            } catch (Exception error) {
                System.out.println(error.getMessage());
            }
            System.out.println("==========================================");

            //------------------------ DELETE CUSTOMER TO THE SYSTEM ----------------------
            System.out.println("Deleting customer");
            try {
                adminServiceImpl.deleteCustomer(customer.getCustomerId()); /// TODO: 19/08/2021 falling here
                System.out.println("Great");
            } catch (Exception error) {
                System.out.println(error.getMessage());
            }
            System.out.println("==========================================");

            //------------------------ GETTING ONE CUSTOMER FROM THE SYSTEM ----------------------

            System.out.println("Getting customer");
            try {
                adminServiceImpl.getOneCustomer(customer.getCustomerId());
                System.out.println("Great");
            } catch (Exception error) {
                System.out.println(error.getMessage());
            }
            System.out.println("==========================================");

            //------------------------ GETTING ALL CUSTOMER FROM THE SYSTEM ----------------------
            System.out.println("Getting list of customers");
            try {
                adminServiceImpl.getAllCustomeris();

                System.out.println("Great");
            } catch (Exception error) {
                System.out.println(error.getMessage());
            }
            System.out.println("==========================================");

            //--------------------- DON'T TOUCH -------------------------

        } catch (Exception error) {
            System.out.println("Something somewhere went terrible wrong -- admin test");
            System.out.println(error.getMessage());
        }

    }
}
