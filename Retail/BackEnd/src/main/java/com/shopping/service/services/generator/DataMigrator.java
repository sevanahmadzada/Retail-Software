package com.shopping.service.services.generator;

import com.shopping.service.document.*;
import com.shopping.service.entity.*;
import com.shopping.service.repository.nosql.*;
import com.shopping.service.repository.sql.*;
import com.shopping.service.services.nosql.product.ProductDocService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DataMigrator {


    private final MongoOperations mongoOperations;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TransactionRepository transactionRepository;
    private final ProductRepository productRepository;
    private final CashierRepository cashierRepository;
    private final CustomerRepository customerRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;

    private final RoleDocRepository roleDocRepository;
    private final UserDocRepository userDocRepository;
    private final TransactionDocRepository transactionDocRepository;
    private final ProductDocRepository productDocRepository;
    private final CashierDocRepository cashierDocRepository;
    private final CustomerDocRepository customerDocRepository;
    private final DepartmentDocRepository departmentDocRepository;
    private final EmployeeDocRepository employeeDocRepository;
    private final OrderDetailsDocRepository orderDetailsDocRepository;
    private final OrderDocRepository orderDocRepository;

    public void deleteAll(){

        roleDocRepository.deleteAll();
        userDocRepository.deleteAll();
        transactionDocRepository.deleteAll();
        productDocRepository.deleteAll();
        cashierDocRepository.deleteAll();
        customerDocRepository.deleteAll();
        departmentDocRepository.deleteAll();
        employeeDocRepository.deleteAll();
        orderDetailsDocRepository.deleteAll();
        orderDocRepository.deleteAll();
    }


    public void transfer() {

        deleteAll();

//        mongoRepository.deleteAll();

        toNoSqlUser();
        toNoSqlRole();
        toNoSqlTransaction();
        toNoSqlProduct();
        toNoSqlCashier();
        toNoSqlCustomer();
        toNoSqlDepartment();
        toNoSqlEmployee();
        toNoSqlOrderDetail();
        toNoSqlOrder();
    }

    public void toNoSqlUser() {

        List<User> users = userRepository.findAll();
        List<UserDocument> userDocuments = new ArrayList<>();

//        UserDocument userDocument = new UserDocument();

        for (User u : users) {
            UserDocument userDocument = new UserDocument();

            userDocument.setId(u.getId());
            userDocument.setCreatedAt(u.getCreatedAt());
            userDocument.setName(u.getName());
            userDocument.setSurname(u.getSurname());
            userDocument.setUsername(u.getUsername());
            userDocument.setPassword(u.getPassword());
            userDocument.setRoles(transRole(u));

            userDocuments.add(userDocument);
        }

        userDocRepository.saveAll(userDocuments);

    }

    public void toNoSqlRole() {
        List<Role> roles = roleRepository.findAll();
        List<RoleDocument> roleDocuments = new ArrayList<>();

        for (Role r : roles) {
            RoleDocument roleDocument = new RoleDocument();

            roleDocument.setId(r.getId());
            roleDocument.setRollName(r.getRollName());
            roleDocument.setRollDesc(r.getRollDesc());
            roleDocument.setCreatedAt(r.getCreatedAt());
            roleDocument.setUpdatedAt(r.getModifiedAt());
//            roleDocument.setUserDocumentList(transUser(r));
            roleDocuments.add(roleDocument);
        }

        roleDocRepository.saveAll(roleDocuments);
    }

    public void toNoSqlTransaction() {
        List<Transaction> transactions = transactionRepository.findAll();
        List<TransactionDocument> transactionDocuments = new ArrayList<>();

        for (Transaction t : transactions) {
            TransactionDocument transactionDocument = new TransactionDocument();

            transactionDocument.setId(t.getId());
            transactionDocument.setCustomer(t.getCustomer().getId());
            transactionDocument.setCreatedAt(t.getCreatedAt());
            transactionDocument.setOrder(t.getOrder().getId());
            transactionDocument.setDate(t.getDate());
            transactionDocument.setUpdatedAt(t.getModifiedAt());
            transactionDocument.setStatus(t.getStatus());
            transactionDocument.setTotal(t.getTotal());
            transactionDocument.setPaymentType(t.getPaymentType().toString());

            transactionDocuments.add(transactionDocument);
        }

        transactionDocRepository.saveAll(transactionDocuments);
    }

    public void toNoSqlProduct() {

        List<Product> products = productRepository.findAll();
        List<ProductDocument> productDocuments = new ArrayList<>();

        for (Product p : products) {
            ProductDocument productDocument = new ProductDocument();

            productDocument.setId(p.getId());
            productDocument.setCreatedAt(p.getCreatedAt());
            productDocument.setModifiedAt(p.getModifiedAt());
            productDocument.setStatus(p.getStatus());
            productDocument.setName(p.getName());
            productDocument.setDescription(p.getDescription());
            productDocument.setPrice(p.getPrice());
            productDocument.setAddedBy(p.getAddedBy().getId());
            productDocument.setDiscount(p.getDiscount());
            productDocument.setQuantity(p.getQuantity());

            productDocuments.add(productDocument);
        }

        List<Product> last = productRepository.findAll(PageRequest.of(0,1, Sort.by("modifiedAt").descending())).getContent();
        Query query = new Query(Criteria.where("_id").is("product_seq"));
        Update update = new Update().set("seq", last.get(0).getId());

        mongoOperations.upsert(query, update, Sequence.class);
        productDocRepository.saveAll(productDocuments);

    }

    public void toNoSqlCashier() {
        List<Cashier> cashiers = cashierRepository.findAll();
        List<CashierDocument> cashierDocuments = new ArrayList<>();

        for (Cashier c : cashiers) {
            CashierDocument cashierDocument = new CashierDocument();
            cashierDocument.setId(c.getId());
            cashierDocument.setCreatedAt(c.getCreatedAt());
            cashierDocument.setUpdatedAt(c.getModifiedAt());
            cashierDocument.setNumberTimesLackMoney(c.getNumberTimesLackMoney());

            cashierDocuments.add(cashierDocument);
        }

        cashierDocRepository.saveAll(cashierDocuments);

    }

    public void toNoSqlCustomer() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDocument> customerDocuments = new ArrayList<>();

        for (Customer c : customers) {
            CustomerDocument customerDocument = new CustomerDocument();

            customerDocument.setId(c.getId());
            customerDocument.setCreatedAt(c.getCreatedAt());
            customerDocument.setUpdatedAt(c.getModifiedAt());
            customerDocument.setAddress(c.getAddress());
            customerDocument.setCashier(c.getCashier().getId());
            customerDocument.setEmail(c.getEmail());
            customerDocument.setRegisterAt(c.getRegisterAt());
            customerDocument.setCustomerContactNum(c.getCustomerContactNum());
            customerDocument.setTransactions(transTranscation(c));

            customerDocuments.add(customerDocument);
        }

        customerDocRepository.saveAll(customerDocuments);

    }

    public void toNoSqlDepartment() {
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentDocument> departmentDocuments = new ArrayList<>();

        for (Department d : departments) {
            DepartmentDocument departmentDocument = new DepartmentDocument();

            departmentDocument.setId(d.getId());
            departmentDocument.setAddress(d.getAddress());
            departmentDocument.setCreatedAt(d.getCreatedAt());
            departmentDocument.setUpdatedAt(d.getModifiedAt());
            departmentDocument.setName(d.getName());
//            departmentDocument.setEmployees(d.getEmployees());

            departmentDocuments.add(departmentDocument);

        }

        departmentDocRepository.saveAll(departmentDocuments);

    }

    public void toNoSqlEmployee() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDocument> employeeDocuments = new ArrayList<>();

        for (Employee e : employees) {
            EmployeeDocument employeeDocument = new EmployeeDocument();
            employeeDocument.setId(e.getId());
            employeeDocument.setAddress(e.getAddress());
            employeeDocument.setCreatedAt(e.getCreatedAt());
            employeeDocument.setUpdatedAt(e.getModifiedAt());
            employeeDocument.setDepartment(e.getDepartment().getId());
            employeeDocument.setJobBeginDate(e.getJobBeginDate());
            employeeDocument.setJobEndDate(e.getJobEndDate());
            employeeDocument.setJobDescription(e.getJobDescription());
            employeeDocument.setSalary(e.getSalary());
            employeeDocument.setParent(e.getParent() != null ? e.getParent().getId() : null);

            employeeDocuments.add(employeeDocument);
        }

        employeeDocRepository.saveAll(employeeDocuments);
    }

    public void toNoSqlOrderDetail() {
        List<OrderDetail> orderDetails = orderDetailRepository.findAll();
        List<OrderDetailDocument> orderDetailDocuments = new ArrayList<>();

        for (OrderDetail o : orderDetails) {
            OrderDetailDocument orderDetailDocument = new OrderDetailDocument();

            orderDetailDocument.setId(o.getId());
            orderDetailDocument.setProduct(o.getProduct().getId());
            orderDetailDocument.setOrder(o.getOrder().getId());
            orderDetailDocument.setDiscount(o.getDiscount());
            orderDetailDocument.setPrice(o.getPrice());
            orderDetailDocument.setQuantity(o.getQuantity());
            orderDetailDocument.setCreatedAt(o.getCreatedAt());
            orderDetailDocument.setUpdatedAt(o.getModifiedAt());

            orderDetailDocuments.add(orderDetailDocument);
        }

        orderDetailsDocRepository.saveAll(orderDetailDocuments);

    }

    public void toNoSqlOrder() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDocument> orderDocuments = new ArrayList<>();

        for (Order o : orders) {
            OrderDocument orderDocument = new OrderDocument();

            orderDocument.setId(o.getId());
            orderDocument.setCreatedAt(o.getCreatedAt());
            orderDocument.setUpdatedAt(o.getModifiedAt());
            orderDocument.setCustomer(o.getCustomer().getId());
            orderDocument.setDiscount(o.getDiscount());
            orderDocument.setTotal(o.getTotal());
            orderDocument.setContent(o.getContent());
            orderDocument.setEmployee(o.getEmployee().getId());
            orderDocument.setOrderDetails(transOrderDetail(o));

            orderDocuments.add(orderDocument);
        }

        orderDocRepository.saveAll(orderDocuments);
    }

    public List<OrderDetailDocument> transOrderDetail(Order order) {

        List<OrderDetailDocument> orderDetailDocuments = new ArrayList<>();

        for (OrderDetail o : order.getOrderDetails()) {
            OrderDetailDocument orderDetailDocument = new OrderDetailDocument();

            orderDetailDocument.setId(o.getId());
            orderDetailDocument.setOrder(o.getOrder().getId());
            orderDetailDocument.setDiscount(o.getDiscount());
            orderDetailDocument.setPrice(o.getPrice());
            orderDetailDocument.setQuantity(o.getQuantity());
            orderDetailDocument.setCreatedAt(o.getCreatedAt());
            orderDetailDocument.setUpdatedAt(o.getModifiedAt());

            orderDetailDocuments.add(orderDetailDocument);
        }

        return orderDetailDocuments;
    }

    public List<TransactionDocument> transTranscation(Customer customer) {
        List<TransactionDocument> transactionDocuments = new ArrayList<>();

        for (Transaction t : customer.getTransactions()) {
            TransactionDocument transactionDocument = new TransactionDocument();

            transactionDocument.setId(t.getId());
            transactionDocument.setCustomer(t.getCustomer().getId());
            transactionDocument.setCreatedAt(t.getCreatedAt());
            transactionDocument.setOrder(t.getOrder().getId());
            transactionDocument.setUpdatedAt(t.getModifiedAt());
            transactionDocument.setStatus(t.getStatus());
            transactionDocument.setTotal(t.getTotal());
            transactionDocument.setPaymentType(t.getPaymentType().toString());

            transactionDocuments.add(transactionDocument);
        }
        return transactionDocuments;
    }

    public List<UserDocument> transUser(Role role) {
        List<UserDocument> userDocuments = new ArrayList<>();

        for (User u : role.getUsers()) {
            UserDocument userDocument = new UserDocument();

            userDocument.setId(u.getId());
            userDocument.setCreatedAt(u.getCreatedAt());
            userDocument.setUpdatedAt(u.getModifiedAt());
            userDocument.setName(u.getName());
            userDocument.setUsername(u.getUsername());
            userDocument.setPassword(u.getPassword());
            userDocument.setSurname(u.getSurname());
            userDocuments.add(userDocument);
        }

        return userDocuments;
    }

    public List<RoleDocument> transRole(User user) {
        List<RoleDocument> roleDocuments = new ArrayList<>();
        RoleDocument roleDocument = new RoleDocument();

        for (Role u : user.getRoles()) {
            roleDocument.setId(u.getId());
            roleDocument.setCreatedAt(u.getCreatedAt());
            roleDocument.setUpdatedAt(u.getModifiedAt());
            roleDocument.setRollName(u.getRollName());
            roleDocument.setRollDesc(u.getRollDesc());
            roleDocuments.add(roleDocument);
        }

        return roleDocuments;
    }


}
