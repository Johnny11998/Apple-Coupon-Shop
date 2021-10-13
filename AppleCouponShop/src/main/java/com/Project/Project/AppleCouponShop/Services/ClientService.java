package com.Project.Project.AppleCouponShop.Services;

import com.Project.Project.AppleCouponShop.Exceptions.MajorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class ClientService {

    @Autowired
    protected AdminServiceImpl adminService;

    @Autowired
    protected CompanyServiceImpl companyService;

    @Autowired
    protected CustomerServiceImpl customerServiceImpl;

    public boolean login(String email, String password) throws  MajorException { //update 19/09/21
        if (this instanceof AdminServiceImpl) {
            return adminService.login(email, password);
        } else if (this instanceof CompanyServiceImpl) {
            CompanyServiceImpl companyServiceImpl = new CompanyServiceImpl();
            return companyServiceImpl.login(email, password);
        } else if (this instanceof CustomerServiceImpl) {
            CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl();
            return customerServiceImpl.login(email, password);
        }
        return true;
    }
}


