package com.shopping.service.services.nosql.employee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.shopping.service.model.employee.EmployeeDocReport;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeDocService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<EmployeeDocReport> top5Cashiers() {
        try (MongoClient client = MongoClients.create("mongodb://mongo:password@mongo:27017/?authSource=admin")) {

            MongoDatabase database = client.getDatabase("mongo");
            MongoCollection<Document> collection = database.getCollection("User");
            LocalDateTime dateTime = LocalDateTime.now().minusMonths(1);

            List<? extends Bson> pipeline = Arrays.asList(
                    new Document()
                            .append("$project", new Document()
                                    .append("_id", 0)
                                    .append("User", "$$ROOT")
                            ),
                    new Document()
                            .append("$lookup", new Document()
                                    .append("localField", "User._id")
                                    .append("from", "Employee")
                                    .append("foreignField", "_id")
                                    .append("as", "Employee")
                            ),
                    new Document()
                            .append("$unwind", new Document()
                                    .append("path", "$Employee")
                                    .append("preserveNullAndEmptyArrays", false)
                            ),
                    new Document()
                            .append("$lookup", new Document()
                                    .append("localField", "Employee._id")
                                    .append("from", "Order")
                                    .append("foreignField", "employee")
                                    .append("as", "Order")
                            ),
                    new Document()
                            .append("$unwind", new Document()
                                    .append("path", "$Order")
                                    .append("preserveNullAndEmptyArrays", false)
                            ),
                    new Document()
                            .append("$lookup", new Document()
                                    .append("localField", "Order._id")
                                    .append("from", "Transaction")
                                    .append("foreignField", "order")
                                    .append("as", "Transaction")
                            ),
                    new Document()
                            .append("$unwind", new Document()
                                    .append("path", "$Transaction")
                                    .append("preserveNullAndEmptyArrays", false)
                            ),
                    new Document()
                            .append("$lookup", new Document()
                                    .append("localField", "Employee.department")
                                    .append("from", "Department")
                                    .append("foreignField", "_id")
                                    .append("as", "Department")
                            ),
                    new Document()
                            .append("$unwind", new Document()
                                    .append("path", "$Department")
                                    .append("preserveNullAndEmptyArrays", false)
                            ),
                    new Document()
                            .append("$match", new Document()
                                    .append("$and", List.of(
                                                    new Document()
                                                            .append("Transaction.status", "1")
                                                            .append("Transaction.date", new Document()
                                                                    .append("$gte",dateTime.toLocalDate())
                                                            )
                                            )
                                    )
                            ),
                    new Document()
                            .append("$group", new Document()
                                    .append("_id", new Document()
                                            .append("User\u1390_id", "$User._id")
                                            .append("User\u1390surname", "$User.surname")
                                            .append("User\u1390username", "$User.username")
                                            .append("Department\u1390name", "$Department.name")
                                            .append("User\u1390name", "$User.name")
                                    )
                                    .append("COUNT(User\u1390_id)", new Document()
                                            .append("$sum", 1)
                                    )
                            ),
                    new Document()
                            .append("$project", new Document()
                                    .append("User.id", "$_id.User\u1390_id")
                                    .append("User.name", "$_id.User\u1390name")
                                    .append("User.surname", "$_id.User\u1390surname")
                                    .append("User.username", "$_id.User\u1390username")
                                    .append("Department.name", "$_id.Department\u1390name")
                                    .append("COUNT(User\u1390_id)", "$COUNT(User᎐_id)")
                                    .append("_id", 0)
                            ),
                    new Document()
                            .append("$sort", new Document()
                                    .append("COUNT(User\u1390_id)", -1)
                                    .append("User.id", 1)
                            ),
                    new Document()
                            .append("$project", new Document()
                                    .append("id", "$User.id")
                                    .append("name", "$User.name")
                                    .append("surname", "$User.surname")
                                    .append("username", "$User.username")
                                    .append("departmentName", "$Department.name")
                                    .append("summa", "$COUNT(User\u1390_id)")
                                    .append("_id", 0)
                            ),
                    new Document()
                            .append("$limit", 5)
            );

            List<EmployeeDocReport> result = new ArrayList<>();

            collection.aggregate(pipeline)
                    .allowDiskUse(true)
                    .forEach(arg -> {
                        String json = arg.toJson();
                        EmployeeDocReport report = null;
                        try {
                            report = objectMapper.readValue(json, EmployeeDocReport.class);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                        result.add(report);
                    });
            return result;
        } catch (MongoException e) {
            System.out.println("Exception in parsing Cashier json!!!");
        }
        return null;
    }
}
