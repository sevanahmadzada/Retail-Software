package com.shopping.service.repository.sql;

import com.shopping.service.entity.Employee;
import com.shopping.service.model.employee.EmployeeProj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(value = "select u.id as id, u.\"name\" as nm, u.surname as surname, u.username as username, d.dep_name as depName, count(*) as summa \n" +
            "\tfrom user_tbl u  \n" +
            "\tinner join employee e on e.id = u.id \n" +
            "\tinner join order_tbl o on e.id = o.employee_id \n" +
            "\tinner join \"transaction\" t on t.order_id  = o.id \n" +
            "\tinner join department d on d.id = e.dep_id \n" +
            "\tinner join cashier c on c.id = u.id \n" +
            "\tWHERE (t.\"date\"\\:\\:date  >= (CURRENT_TIMESTAMP - interval '1 month') \\:\\: date\n" +
            "      )\n" +
            "      and t.status ='1'\n" +
            "\tgroup by u.id, u.\"name\", u.surname, u.username, d.dep_name\n" +
            "order by summa desc, u.id \n" +
            "limit 5;", nativeQuery = true)
    List<EmployeeProj> top5Monthly();

}
