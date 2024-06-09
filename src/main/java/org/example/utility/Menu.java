package org.example.utility;

import jakarta.validation.*;
import org.example.exception.NoResultException;
import org.example.model.*;
import org.example.model.enums.ExpertsLevel;
import org.example.record.UpdatingDescription;
import org.example.record.UpdatingWage;
import org.hibernate.exception.ConstraintViolationException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Menu {

    private static final Scanner scanner = new Scanner(System.in);
    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    public Menu(){}

    public void menu(){
        System.out.println("---- Welcome ----");
        int choice = 3;
        while (choice!=0) {
            System.out.println("""
                    0, Exit
                    1, Sign up (register for an account)
                    2, Log in (i already have an account)""");
            choice = getInt();
            switch (choice) {
                case 0 -> System.out.println("Exit.");
                case 1 -> signUp();
                case 2 -> logIn();
                default -> System.out.println("Wrong input, enter again.");
            }
        }
    }

    private void expertsMenu(){
        if (SecurityContext.expert.getExpertsLevel().equals(ExpertsLevel.VERIFIED)) {
            System.out.println("---- Expert Menu ----");
        } else
            System.out.println("Sorry, you are not verified yet.");
    }

    private void customersMenu(){
        System.out.println("---- Customer Menu ----");
        System.out.println("What do you want to do?");
    }

    private void adminsMenu(){
        System.out.println("---- Admin Menu ----");
        System.out.println("""
                What do you want to do?
                1, Service CRUD
                2, Subservice CRUD
                3, Verifying Experts.
                4, Add an Expert to a Subservice.
                5, Remove an Expert from a Subservice.
                """);
        int choice = getInt();
        switch (choice){
            case 1 -> serviceCRUD();
            case 2 -> subserviceCRUD();
            case 3 -> verifyingExpert();
            case 4 -> addExpertToSubservice();
            case 5 -> removeExpertFromSubservice();
            default -> System.out.println("Entrance out of options!");
        }

    }

    private void addExpertToSubservice(){
        System.out.println("---- Adding Expert to a Subservice ----");
        System.out.println();
        List<Long> subservice = loadAllSubservice();
        System.out.println("Enter the id of subservice: ");
        long id = getLong();
        while (!subservice.contains(id)){
            System.out.println("Please enter a valid integer!");
            id = getLong();
        }
        Subservice byId = ApplicationContext.getSubserviceService().findById(id);
        List<Long> experts = printAllVerifiedExperts();
        System.out.println("Now enter the id of the expert you wanna add: ");
        long expertId = getLong();
        while (!experts.contains(expertId)){
            System.out.println("Please enter a valid integer!");
            expertId = getLong();
        }
        Expert expert = ApplicationContext.getExpertService().findById(expertId);
        User_SubService userSubService = User_SubService.builder()
                .expert(expert)
                .subservice(byId)
                .build();
        User_SubService byExpertAndSubservice = null;
        try {
             byExpertAndSubservice = ApplicationContext
                    .getUsersSubServiceService().findByExpertAndSubservice(expert, byId);
        } catch (NoResultException e){
            System.out.println(e.getMessage());
        }
        if (byExpertAndSubservice==null){
            User_SubService saved = ApplicationContext.getUsersSubServiceService().saveOrUpdate(userSubService);
            if (saved!=null){
                System.out.println("Done!");
            } else {
                System.out.println("Something went wrong!");
            }
        } else
            System.out.println("The expert is already under service of this subservice.");

    }

    private void removeExpertFromSubservice(){
        System.out.println("---- Removing Expert from a Subservice ----");
        System.out.println();
        List<Long> subservice = loadAllSubservice();
        System.out.println("Enter the id of subservice: ");
        long id = getLong();
        while (!subservice.contains(id)){
            System.out.println("Please enter a valid integer!");
            id = getLong();
        }
        Subservice byId = ApplicationContext.getSubserviceService().findById(id);
        List<Long> expertsId = printAllExpertsOfSubservice(byId);
        System.out.println("Enter the id of expert: ");
        Long expertId = getLong();
        while (!expertsId.contains(expertId)){
            System.out.println("Please enter a valid integer!");
            expertId = getLong();
        }
        Expert expert = ApplicationContext.getExpertService().findById(expertId);
        User_SubService byExpertAndSubservice = ApplicationContext
                .getUsersSubServiceService().findByExpertAndSubservice(expert, byId);
        ApplicationContext.getUsersSubServiceService().delete(byExpertAndSubservice);
        System.out.println("Done!");
    }

    private void verifyingExpert(){
        printAllWaitingForVerificationExperts();

        System.out.println("Enter the id of the expert u wanna change level: ");
        long id = getLong();
        Expert byId = ApplicationContext.getExpertService().findById(id);
        System.out.println("""
                wanna set their level to:
                1, Verified.
                2, Unverified.""");
        int choice = getInt();
        switch (choice){
            case 1 -> byId.setExpertsLevel(ExpertsLevel.VERIFIED);
            case 2 -> byId.setExpertsLevel(ExpertsLevel.UNVERIFIED);

            default -> System.out.println("Entrance out of options!");
        }
        Expert expert = ApplicationContext.getExpertService().saveOrUpdate(byId);
        System.out.println("Changed: " + expert.getExpertsLevel());
    }

    private List<Long> printAllExpertsOfSubservice(Subservice subservice){
        List<User_SubService> list = ApplicationContext.getUsersSubServiceService().findBySubservice(subservice);
        List<Long> longs = new ArrayList<>();
        list.forEach(sub -> {
            System.out.println("id: " + sub.getExpert().getId() +
                    "   | username: " + sub.getExpert().getUsername() +
                    "   | situation: " + sub.getExpert().getExpertsLevel());
            longs.add(sub.getExpert().getId());
        });
        return longs;
    }

    private List<Long> printAllVerifiedExperts(){
        List<Expert> experts = ApplicationContext.getExpertService()
                        .loadAllVerifiedExperts();
        List<Long> expertsId = new ArrayList<>();
        experts.forEach(expert -> {
            System.out.println("id: " + expert.getId() +
                    "   | username: " + expert.getUsername() +
                    "   | situation: " + expert.getExpertsLevel());
            expertsId.add(expert.getId());
        });
        return expertsId;
    }

    private void printAllWaitingForVerificationExperts(){
        List<Expert> experts = ApplicationContext.getExpertService()
                .loadAllWaitingForVerificationExperts();

        experts.forEach(expert -> {
            System.out.println("id: " + expert.getId() +
                    "   | username: " + expert.getUsername() +
                    "   | situation: " + expert.getExpertsLevel());
        });
    }

    private void serviceCRUD(){
        System.out.println("""
                1, Load all services
                2, Register a new Service""");
        int choice = getInt();
        switch (choice){
            case 1 -> loadAllServices();
            case 2 -> registerNewService();

            default -> System.out.println("Entrance out of options!");
        }
    }

    private void subserviceCRUD(){
        System.out.println("""
                ---- Subservice ----
                1, create a new Subservice
                2, edit a Subservice""");
        int choice = getInt();
        switch (choice){
            case 1 -> registerNewSubservice(true);
            case 2 -> updatingSubservice();
        }
    }

    private void updatingSubservice(){
        System.out.println("---- Updating Subservice ----");
        System.out.println();
        loadAllSubservice();
        System.out.println("warning: You can't update the name!");
        System.out.println("Enter the id of the subservice u wanna update: ");
        Long id = getLong();
        Subservice byId = ApplicationContext.getSubserviceService().findById(id);
        System.out.println("Enter new description: (enter for skip)");
        UpdatingDescription updatingDescription = UpdatingMethods.getDescription();
        System.out.println("New wage: (0 for skip)");
        UpdatingWage updatingWage = UpdatingMethods.getWage(byId.getBasePrice());
        if (updatingWage.flag())
            byId.setBasePrice(updatingWage.wage());
        if (updatingDescription.flag())
            byId.setDescription(updatingDescription.description());
        Subservice updated = ApplicationContext.getSubserviceService().saveOrUpdate(byId);
        System.out.println(updated);

    }

    private List<Long> loadAllSubservice(){
        List<Subservice> subservice = ApplicationContext.getSubserviceService().loadAll();

        List<Long> subserviceId = new ArrayList<>();
        subservice.forEach(sub -> {
            System.out.println("Id: " + sub.getId() +
                    "   | name: " + sub.getName() +
                    "   | description: " + sub.getDescription() +
                    "   | wage: " + sub.getBasePrice());
            subserviceId.add(sub.getId());
        } );
        return subserviceId;
    }

    private void signUp(){
        System.out.println("""
                Wanna sign up as:
                1, Expert
                2, Customer""");
        int choice = getInt();
        switch (choice){
           case 1 -> signUpExpert();
           case 2 -> signUpCustomer();
        }
    }

    private void signUpExpert(){
        System.out.println("---- Signing up an Expert ----");
        User user = getUserInfo();
        System.out.println("Image path: ");
        String path = getString();
        byte[] bytes = getBytesForExpert(path);
        Expert expert = Expert.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .username(user.getUsername())
                .password(user.getPassword())
                .wallet(user.getWallet())
                .expertsLevel(ExpertsLevel.WAITING_FOR_VERIFYING)
                .score(0D)
                .image(bytes)
                .registrationDate(user.getRegistrationDate())
                .build();
        Set<ConstraintViolation<Expert>> validate = validator.validate(expert);
        if (validate.isEmpty())
            ApplicationContext.getExpertService().saveOrUpdate(expert);
        else
            System.out.println(validate);

    }

    private static byte[] getBytesForExpert(String path) {
        byte[] array;
        try {
            array = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return array;
    }

    private void signUpCustomer(){
        System.out.println("---- Signing up a Customer ----");
        User user = getUserInfo();
        Customer customer = Customer.builder().firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .username(user.getUsername())
                .password(user.getPassword())
                .registrationDate(user.getRegistrationDate())
                .wallet(user.getWallet())
                .build();
        Set<ConstraintViolation<Customer>> validate = validator.validate(customer);
        if (validate.isEmpty())
            ApplicationContext.getCustomerService().saveOrUpdate(customer);
        else
            System.out.println(validate);
    }

    private User getUserInfo(){
        System.out.println("---- please enter your info: ");
        System.out.println("First name: ");
        String firstName = getString();
        System.out.println("Last name: ");
        String lastName = getString();
        System.out.println("Email: ");
        String email = getString();
        while (!validateEmail(email)){
            System.out.println("Enter a valid email!");
            email = getString();
        }
        System.out.println("Username: ");
        String username = getString();
        System.out.println("Password: ");
        printPasswordPolicy();
        String password = getString();
        while (!validatePass(password)){
            System.out.println("Enter a valid password!");
            password = getString();
        }
        return User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .username(username)
                .password(password)
                .wallet(Wallet.builder().build())
                .registrationDate(LocalDate.now())
                .build();
    }

    private void printPasswordPolicy(){
        System.out.println("""
                -At least 8 chars.
                -Contains at least one digit.
                -Contains at least one lower alpha char and one upper alpha char.
                -Contains at least one char within a set of special chars. (@#%$^ etc.)
                -Does not contain space, tab, etc.""");
    }

    private void logIn(){
        System.out.println("---- Log in ----");
        Result result = getResult();
        if (result.byUsername() != null){
            switch (result.byUsername()) {
                case "Expert" -> {
                    Expert expert = ApplicationContext.getExpertService().findByUsername(result.username());
                    if (expert.getPassword().equals(result.password())) {
                        SecurityContext.user = expert;
                        SecurityContext.expert = expert;
                        expertsMenu();
                    } else
                        System.out.println("Wrong info, try again.");
                }
                case "Customer" -> {
                    Customer customer = ApplicationContext.getCustomerService().findByUsername(result.username());
                    if (customer.getPassword().equals(result.password())) {
                        SecurityContext.user = customer;
                        SecurityContext.customer = customer;
                        customersMenu();
                    } else
                        System.out.println("Wrong info, try again.");
                }
                case "Admin" -> {
                    Admin admin = ApplicationContext.getAdminService().findByUsername(result.username());
                    if (admin.getPassword().equals(result.password())) {
                        SecurityContext.admin = admin;
                        SecurityContext.user = admin;
                        adminsMenu();
                    } else
                        System.out.println("Wrong info, try again.");
                }
                default -> System.out.println("User doesn't exist at all!!!");
            }
        } else
            System.out.println("User doesn't exist at all!!!");
    }

    private void registerNewService(){
        System.out.println("---- Service Registration ----");
        System.out.println("Service name: ");
        String serviceName = getString();
        Service service = Service.builder()
                .serviceName(serviceName)
                .registeredAdmin(SecurityContext.admin)
                .build();
        Service saved = ApplicationContext.getServiceService().saveOrUpdate(service);
        if (saved!=null){
            System.out.println("""
                    Service registered successfully.
                    now, the Service you have just registered, should at least
                    have a Subservice.
                    So now we're leading you to Subservice registration.""");
            registerNewSubserviceStraightAfterService(saved);
        } else
            System.out.println("Something went wrong.");
    }

    private void registerNewSubserviceStraightAfterService(Service service){
        System.out.println("---- Subservice Registration ----");
        serviceResult result = getServiceResult();
            Subservice subservice = Subservice.builder()
                    .name(result.name)
                    .basePrice(result.basePrice)
                    .description(result.description)
                    .service(service)
                    .build();
            saveSubservice(subservice);
        anotherSubservice();
    }

    private void registerNewSubservice(boolean flag) {
        if (flag) {
            List<Long> services = loadAllServices();
            Long serviceId;
            System.out.println("---- Subservice Registration ----");
            do {
                System.out.println("Choose a Service: (Enter id)");
                serviceId = getLong();
            } while (!services.contains(serviceId));
            Service service = ApplicationContext.getServiceService().findById(serviceId);
            if (service != null) {
                serviceResult result = getServiceResult();
                Subservice subservice = Subservice.builder()
                        .name(result.name)
                        .basePrice(result.basePrice())
                        .description(result.description())
                        .service(service)
                        .build();
                saveSubservice(subservice);
                anotherSubservice();
            } else
                System.out.println("Something went wrong!");
        }

    }

    private void anotherSubservice() {
        System.out.println("Wanna register another? (y/n)");
        boolean yesOrNo = yesOrNo();
        registerNewSubservice(yesOrNo);
    }

    private void saveSubservice(Subservice subservice){
        try {
            Subservice saved = ApplicationContext.getSubserviceService().saveOrUpdate(subservice);
                if (saved != null)
                    System.out.println("Subservice Registered.");
        } catch (ConstraintViolationException e){
            System.out.println(e.getMessage());
        }
    }

    private serviceResult getServiceResult() {
        System.out.println("Name for subservice: ");
        String name = getString();
        System.out.println("Base price:  ");
        Double basePrice = getDouble();
        System.out.println("And some description: ");
        String description = getString();
        return new serviceResult(name, basePrice, description);
    }

    private record serviceResult(String name,Double basePrice, String description) {
    }

    private List<Long> loadAllServices() {
        List<Long> serviceIds = new ArrayList<>();
        List<Service> serviceList = new ArrayList<>();
        try{
            serviceList = ApplicationContext.getServiceService().loadAll();
        } catch (NoResultException e){
            System.out.println(e.getMessage());
        }
        System.out.println("---- Services ----");
        serviceList.forEach(service -> {
            serviceIds.add(service.getId());
            System.out.println("id: " + service.getId() +
                    "   | service name: " + service.getServiceName());
        });
        return serviceIds;
    }

    private Result getResult() {
        System.out.println("Please enter your username: ");
        String username = getString();
        System.out.println("And Password: ");
        String password = getString();
        String byUsername = ApplicationContext.getUserService().findTypeByUsername(username);
        return new Result(username, password, byUsername);
    }

    private record Result(String username, String password, String byUsername) {
    }

    private boolean validateEmail(String email) {
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.matches();
    }

    private boolean validatePass(String pass){
        Pattern validPass = Pattern.compile("^.*(?=.{8,})(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = validPass.matcher(pass);
        return matcher.matches();
    }

    private int getInt(){
        int number;
        while (true){
            String input = scanner.nextLine();
            try {
                number = Integer.parseInt(input);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
        return number;
    }

    public static String getString(){
        String string = "";
        try {
            string = scanner.nextLine();
        } catch (InputMismatchException e){
            System.out.println("Wrong input! enter a valid string.");
        }
        return string;
    }

    private Long getLong(){
        long intLong;
        while (true){
            String aLong = scanner.nextLine();
            try {
                intLong = Long.parseLong(aLong);
                break;
            } catch (NumberFormatException e){
                System.out.println("Wrong input! enter a valid long number.");
            }
        }
        return intLong;
    }

    public static Double getDouble(){
        double idouble ;
        while (true){
            String doubleString = scanner.nextLine();
            try {
                idouble = Double.parseDouble(doubleString);
                break;
            } catch (NumberFormatException e){
                System.out.println("Wrong input! enter a valid double number.");
            }
        }
        return idouble;
    }

    private void setUpAdmin(){
        Admin admin = Admin.builder()
                .firstName("Radvin")
                .lastName("Mohammadi")
                .email("Radi@gmail.com")
                .registrationDate(LocalDate.now())
                .username("radirad")
                .password("Radi@123")
                .build();
        ApplicationContext.getAdminService().saveOrUpdate(admin);
    }

    private boolean yesOrNo(){
        boolean flag = true;
        String choice;
        do {
            choice = getString();
            if (choice.equals("n"))
                flag = false;
        } while (!choice.equals("y") && !choice.equals("n"));
        return flag;
    }
}
