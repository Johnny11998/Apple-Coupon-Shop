package com.Project.Project.AppleCouponShop.Login;



import com.Project.Project.AppleCouponShop.Exceptions.MajorException;
import com.Project.Project.AppleCouponShop.Services.AdminServiceImpl;
import com.Project.Project.AppleCouponShop.Services.ClientService;
import com.Project.Project.AppleCouponShop.Services.CompanyServiceImpl;
import com.Project.Project.AppleCouponShop.Services.CustomerServiceImpl;
import com.Project.Project.AppleCouponShop.enums.ClientType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginManager {
    private final AdminServiceImpl adminServiceImpl;
    private final CompanyServiceImpl companyServiceImpl;
    private final CustomerServiceImpl customerServiceImpl;

    public ClientService login(String email, String password, ClientType clientType) throws MajorException {
        switch (clientType) {
            case ADMIN:
                if (!email.equals("admin@admin.com") || (!password.equals("admin"))) {
                    throw new MajorException("Bad");
                }
                return adminServiceImpl;

            case COMPANY:
                companyServiceImpl.login(email, password);
                return  companyServiceImpl;

            case CUSTOMER:
                customerServiceImpl.login(email, password);
                return  customerServiceImpl;

            case GUEST:
//                return new GuestService();
                break;
        }
        return adminServiceImpl;
    }
}

