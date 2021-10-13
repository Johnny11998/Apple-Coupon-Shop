package com.Project.Project.AppleCouponShop.Services;

import com.Project.Project.AppleCouponShop.Exceptions.MajorException;
import com.Project.Project.AppleCouponShop.beans.Company;
import com.Project.Project.AppleCouponShop.beans.Customer;

import java.util.List;

public interface AdminService {

    boolean login(String email, String password) throws MajorException;

    Company addCompany(Company company) throws MajorException;

    Company updateCompany(Company company) throws MajorException;

    void deleteCompany(String email) throws MajorException;

    List<Company> getAllCompanies();

    Company findByCompanyId(int companyId) throws MajorException;

    void updateCustomer(Customer customer) throws MajorException;

    void deleteCustomer(String email) throws MajorException;

    List<Customer> getAllCustomers();

    Customer findByCustomerId(int customerId) throws MajorException;

}
