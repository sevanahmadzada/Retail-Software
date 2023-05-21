package com.shopping.service.services.generator;

import com.github.javafaker.Address;
import com.github.javafaker.Faker;
import com.github.javafaker.Job;
import com.github.javafaker.Name;
import com.shopping.service.entity.*;
import com.shopping.service.repository.sql.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DataGenerator {

    private final DepartmentRepository departmentRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final CashierRepository cashierRepository;
    private final RoleRepository roleRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;
    private Random r = new Random();
    private Faker faker = new Faker();
    private final EntityManager em;


    @Transactional
    public void generateData() {


        Query tbNames = em.createNativeQuery("SELECT table_name\n" +
                "  FROM information_schema.tables\n" +
                " WHERE table_schema='public'\n" +
                "   AND table_type='BASE TABLE'");
        List<String> tableNames = tbNames.getResultList();

        tableNames.forEach(arg -> {
            Query truncate = em.createNativeQuery("TRUNCATE TABLE " + arg + " CASCADE");
            truncate.executeUpdate();
        });

        Address address = faker.address();
        Job job = faker.job();
        Name name = faker.name();

        List<Cashier> cashiers = new ArrayList<>();
        List<Employee> managers = new ArrayList<>();
        List<Customer> customers = new ArrayList<>();
        List<Role> roles = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        List<Order> orders = new ArrayList<>();

        List<Department> departments = new ArrayList<>(List.of(new Department("BRAVO", address.fullAddress()),
                new Department("BOLMART", address.fullAddress()),
                new Department("ARAZ", address.fullAddress())));

        LocalDate beginDate = LocalDate.of(2022, Month.APRIL, 5);

        roles.add(new Role("CASHIER", "Cashiers", new ArrayList<>()));
        roles.add(new Role("MANAGER", "Managers", new ArrayList<>()));
        roles.add(new Role("CUSTOMER", "Simple Customers", new ArrayList<>()));

        //generation managers
        for (int i = 0; i < 5; i++) {
            Department d = departments.get(randomNum(0, departments.size() - 1));
            Employee employee = new Employee();
            d.addEmployee(employee);

            employee.setDepartment(d);
            employee.setAddress(address.fullAddress());
            employee.setJobDescription("Management");
            employee.setSalary(1500);
            employee.setJobBeginDate(beginDate.plusDays(randomNum(1, 200)));
            employee.setCashier(null);

            User u = new User();
            String fullName = name.name();
            u.setName(fullName.split(" ")[0]);
            u.setSurname(fullName.split(" ")[1]);
            u.setUsername(name.username());
            u.setPassword(passwordEncoder.encode("1234"));
            u.setEmployee(employee);
            u.addRole(roles.get(1));
            employee.setUser(u);


            managers.add(employee);
        }

        //generation cashiers
        for (int i = 0; i < 40; i++) {
            Cashier cashier = new Cashier();
            cashier.setNumberTimesLackMoney(randomNum(0, 3));
            Department d = departments.get(randomNum(0, departments.size() - 1));
            Employee employee = new Employee();
            d.addEmployee(employee);

            Employee parent = managers.get(randomNum(0, managers.size() - 1));
            parent.addChild(employee);

            employee.setDepartment(parent.getDepartment());
            employee.setAddress(address.fullAddress());
            employee.setJobDescription(job.keySkills());
            employee.setSalary(600);
            employee.setJobBeginDate(beginDate.plusDays(randomNum(1, 300)));
            employee.setCashier(cashier);

            cashier.setEmployee(employee);

            User u = new User();
            String fullName = name.name();
            u.setName(fullName.split(" ")[0]);
            u.setSurname(fullName.split(" ")[1]);
            u.setUsername(name.username());
            u.setPassword(passwordEncoder.encode("1234"));
            u.setEmployee(employee);
            employee.setUser(u);
            u.addRole(roles.get(0));
            u.setEmployee(employee);
            employee.setUser(u);

            cashiers.add(cashier);
        }

        customers.add(generateGuestCustomer(roles.get(randomNum(0, roles.size() - 1)), cashiers.get(randomNum(0, cashiers.size() - 1))));
        //generation customers
        for (int i = 0; i < 200; i++) {
            User u = new User();
            String fullName = name.name();
            u.setName(fullName.split(" ")[0]);
            u.setSurname(fullName.split(" ")[1]);
            u.setUsername(name.username());
            u.setPassword(passwordEncoder.encode("1234"));
            u.addRole(roles.get(2));

            Cashier cashier = cashiers.get(randomNum(0, cashiers.size() - 1));

            Customer customer = new Customer();
            customer.setEmail(faker.internet().emailAddress());
            customer.setAddress(address.fullAddress());
            customer.setCustomerContactNum(faker.phoneNumber().cellPhone());
            customer.setRegisterAt(beginDate.plusDays(randomNum(1, 300)));
            cashier.addCustomer(customer);

            customer.setUser(u);

            customers.add(customer);
        }

        //getting data from file which was generated in cobbl.io
        List<String> nameOfProd = getProductsFromFile("products.txt");

        //generating products
        for (int i = 0; i < 200; i++) {
            String[] arr = nameOfProd.get(i).split(",");
            Employee manager = managers.get(randomNum(0, managers.size() - 1));
            Product product = new Product();
            product.setName(arr[0]);
            product.setQuantity(randomNum(80, 120));
            product.setDescription(arr[1]);
            product.setPrice(normalPriceValue(Float.parseFloat(arr[2])));

            manager.addProduct(product);
            products.add(product);
        }

        //sales simulation
        for (int i = 0; i < 1000; i++) {
            Order order = new Order();

            Cashier cashier = cashiers.get(randomNum(0, cashiers.size() - 1));
            Customer customer = customers.get(randomNum(0, customers.size() - 1));

            for (int j = 0; j < randomNum(1, 15); j++) {
                OrderDetail orderDetail = new OrderDetail();
                int quantity = randomNum(1, 5);
                Product product = products.get(randomNum(0, products.size() - 1));
                if (product.getQuantity() >= quantity) {
                    product.setQuantity(product.getQuantity() - quantity);
                    product.addOrderDetail(orderDetail);

                    orderDetail.setPrice(product.getPrice());
                    orderDetail.setQuantity(quantity);
                    orderDetail.setDiscount(0f);
                    order.addOrderDetail(orderDetail);
                }
            }

            if (!order.getOrderDetails().isEmpty()) {
                Double resPrice = order.getOrderDetails().stream()
                        .reduce(0d,
                                (arg, arg2) -> arg + (arg2.getQuantity() * arg2.getPrice()),
                                Double::sum);

                order.setTotal(normalPriceValue(resPrice));
                order.setEmployee(cashier.getEmployee());
                order.setContent("Selling product");
                order.setCustomer(customer);
                order.setDiscount(0f);

                Transaction transaction = new Transaction();
                transaction.setCustomer(customer);
                transaction.setTotal(order.getTotal());
                transaction.setStatus('1');
                PaymentType paymentType = r.nextBoolean() ? PaymentType.CASH : PaymentType.CART;
                transaction.setPaymentType(paymentType);
                transaction.setDate(beginDate.plusDays(randomNum(1, (int) beginDate.until(LocalDateTime.now(), ChronoUnit.DAYS))).atTime(randomNum(8, 20), randomNum(0, 59), randomNum(0, 59)));

                order.addTransaction(transaction);

                orders.add(order);
            }
        }

        departmentRepository.saveAll(departments);
        roleRepository.saveAll(roles);
        employeeRepository.saveAll(managers);
        cashierRepository.saveAll(cashiers);
        customerRepository.saveAll(customers);
        productRepository.saveAll(products);
        orderRepository.saveAll(orders);

    }

    private int randomNum(int min, int max) {
        return r.nextInt(max - min) + min;
    }

    private double normalPriceValue(double price) {
        return Math.round(price * 10.0) / 10.0;
    }

    //reading generated data from file
    private List<String> getProductsFromFile(String filePath) {
        List<String> list = new ArrayList<>();
        // the date was generated by an online generator, since our product name is unique in the database, we must check
        Map<String, String> checkUnique = new HashMap<>();
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            String[] arr;
            while ((line = bufferedReader.readLine()) != null) {
                arr = line.split(",");
                if (!checkUnique.containsKey(arr[0])) {
                    checkUnique.put(arr[0], "");
                    list.add(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private Customer generateGuestCustomer(Role role, Cashier cashier) {
        User u = new User();
        u.setName("GUEST");
        u.setSurname("GUEST");
        u.setUsername("guest");
        u.setPassword(passwordEncoder.encode("1234"));
        u.addRole(role);

        Customer customer = new Customer();
        customer.setEmail(faker.internet().emailAddress());
        customer.setAddress("postgresql schema public");
        customer.setCustomerContactNum("000-000-000");
        customer.setRegisterAt(null);
        cashier.addCustomer(customer);

        customer.setUser(u);
        return customer;
    }

//    @Bean
//    public CommandLineRunner runner() {
//        return args -> {
//            generateData();
//        };
//    }

}
