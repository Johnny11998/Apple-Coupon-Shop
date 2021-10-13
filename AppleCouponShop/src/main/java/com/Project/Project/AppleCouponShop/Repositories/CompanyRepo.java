package com.Project.Project.AppleCouponShop.Repositories;

import com.Project.Project.AppleCouponShop.beans.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepo extends JpaRepository<Company,Integer> {
    Company findByEmailAndPassword(String email, String password);
    Company findByEmail(String email);
    Company findByCompanyId(int companyId);
    //----------------------------------------------------
}
