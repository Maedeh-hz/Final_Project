package org.example.utility;

import org.example.connnection.SessionFactorySingleton;
import org.example.repository.admin.AdminRepository;
import org.example.repository.admin.AdminRepositoryImpl;
import org.example.repository.customer.CustomerRepository;
import org.example.repository.customer.CustomerRepositoryImpl;
import org.example.repository.expert.ExpertRepository;
import org.example.repository.expert.ExpertRepositoryImpl;
import org.example.repository.order.OrderRepository;
import org.example.repository.order.OrderRepositoryImpl;
import org.example.repository.service.ServiceRepository;
import org.example.repository.service.ServiceRepositoryImpl;
import org.example.repository.subservice.SubserviceRepository;
import org.example.repository.subservice.SubserviceRepositoryImpl;
import org.example.repository.suggestion.SuggestionRepository;
import org.example.repository.suggestion.SuggestionRepositoryImpl;
import org.example.repository.user.UserRepository;
import org.example.repository.user.UserRepositoryImpl;
import org.example.repository.users_subservice.User_SubServiceRepository;
import org.example.repository.users_subservice.User_SubServiceRepositoryImpl;
import org.example.repository.wallet.WalletRepository;
import org.example.repository.wallet.WalletRepositoryImpl;
import org.example.service.admin.AdminService;
import org.example.service.admin.AdminServiceImpl;
import org.example.service.customer.CustomerService;
import org.example.service.customer.CustomerServiceImpl;
import org.example.service.expert.ExpertService;
import org.example.service.expert.ExpertServiceImpl;
import org.example.service.order.OrderService;
import org.example.service.order.OrderServiceImpl;
import org.example.service.service.ServiceService;
import org.example.service.service.ServiceServiceImpl;
import org.example.service.subservice.Sub_serviceService;
import org.example.service.subservice.SubserviceServiceImpl;
import org.example.service.suggestion.SuggestionService;
import org.example.service.suggestion.SuggestionServiceImpl;
import org.example.service.user.UserService;
import org.example.service.user.UserServiceImpl;
import org.example.service.users_subservice.User_SubServiceService;
import org.example.service.users_subservice.User_SubServiceServiceImpl;
import org.example.service.wallet.WalletService;
import org.example.service.wallet.WalletServiceImpl;
import org.hibernate.SessionFactory;

public class ApplicationContext {
    private static final SessionFactory SESSION_FACTORY;
    private static final UserRepository USER_REPOSITORY;
    private static final AdminRepository ADMIN_REPOSITORY;
    private static final CustomerRepository CUSTOMER_REPOSITORY;
    private static final ExpertRepository EXPERT_REPOSITORY;
    private static final OrderRepository ORDER_REPOSITORY;
    private static final ServiceRepository SERVICE_REPOSITORY;
    private static final SubserviceRepository SUBSERVICE_REPOSITORY;
    private static final User_SubServiceRepository USERS_SUB_SERVICE_REPOSITORY;
    private static final SuggestionRepository SUGGESTION_REPOSITORY;
    private static final WalletRepository WALLET_REPOSITORY;
    private static final UserService USER_SERVICE;
    private static final AdminService ADMIN_SERVICE;
    private static final CustomerService CUSTOMER_SERVICE;
    private static final ExpertService EXPERT_SERVICE;
    private static final OrderService ORDER_SERVICE;
    private static final ServiceService SERVICE_SERVICE;
    private static final Sub_serviceService SUBSERVICE_SERVICE;
    private static final User_SubServiceService USERS_SUB_SERVICE_SERVICE;
    private static final SuggestionService SUGGESTION_SERVICE;
    private static final WalletService WALLET_SERVICE;

    static {
        SESSION_FACTORY = SessionFactorySingleton.getInstance();
        USER_REPOSITORY = new UserRepositoryImpl(SESSION_FACTORY);
        ADMIN_REPOSITORY = new AdminRepositoryImpl(SESSION_FACTORY);
        CUSTOMER_REPOSITORY = new CustomerRepositoryImpl(SESSION_FACTORY);
        EXPERT_REPOSITORY = new ExpertRepositoryImpl(SESSION_FACTORY);
        ORDER_REPOSITORY = new OrderRepositoryImpl(SESSION_FACTORY);
        SERVICE_REPOSITORY = new ServiceRepositoryImpl(SESSION_FACTORY);
        SUBSERVICE_REPOSITORY = new SubserviceRepositoryImpl(SESSION_FACTORY);
        USERS_SUB_SERVICE_REPOSITORY = new User_SubServiceRepositoryImpl(SESSION_FACTORY);
        SUGGESTION_REPOSITORY = new SuggestionRepositoryImpl(SESSION_FACTORY);
        WALLET_REPOSITORY = new WalletRepositoryImpl(SESSION_FACTORY);
        USER_SERVICE = new UserServiceImpl(USER_REPOSITORY, SESSION_FACTORY);
        ADMIN_SERVICE = new AdminServiceImpl(ADMIN_REPOSITORY, SESSION_FACTORY);
        CUSTOMER_SERVICE = new CustomerServiceImpl(CUSTOMER_REPOSITORY, SESSION_FACTORY);
        EXPERT_SERVICE = new ExpertServiceImpl(EXPERT_REPOSITORY, SESSION_FACTORY);
        ORDER_SERVICE = new OrderServiceImpl(ORDER_REPOSITORY, SESSION_FACTORY);
        SERVICE_SERVICE = new ServiceServiceImpl(SERVICE_REPOSITORY, SESSION_FACTORY);
        SUBSERVICE_SERVICE = new SubserviceServiceImpl(SUBSERVICE_REPOSITORY, SESSION_FACTORY);
        USERS_SUB_SERVICE_SERVICE = new User_SubServiceServiceImpl(USERS_SUB_SERVICE_REPOSITORY, SESSION_FACTORY);
        SUGGESTION_SERVICE = new SuggestionServiceImpl(SUGGESTION_REPOSITORY, SESSION_FACTORY);
        WALLET_SERVICE = new WalletServiceImpl(WALLET_REPOSITORY, SESSION_FACTORY);
    }

    public static UserService getUserService() {
        return USER_SERVICE;
    }

    public static AdminService getAdminService() {
        return ADMIN_SERVICE;
    }

    public static CustomerService getCustomerService() {
        return CUSTOMER_SERVICE;
    }

    public static ExpertService getExpertService() {
        return EXPERT_SERVICE;
    }

    public static OrderService getOrderService() {
        return ORDER_SERVICE;
    }

    public static ServiceService getServiceService() {
        return SERVICE_SERVICE;
    }

    public static Sub_serviceService getSubserviceService() {
        return SUBSERVICE_SERVICE;
    }

    public static User_SubServiceService getUsersSubServiceService(){
        return USERS_SUB_SERVICE_SERVICE;
    }

    public static SuggestionService getSuggestionService() {
        return SUGGESTION_SERVICE;
    }

    public static WalletService getWalletService() {
        return WALLET_SERVICE;
    }
}
