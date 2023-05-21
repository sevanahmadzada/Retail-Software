package com.shopping.service.services.nosql.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.shopping.service.document.ProductDocument;
import com.shopping.service.model.PaginationResponse;
import com.shopping.service.model.product.ProductDocReport;
import com.shopping.service.repository.nosql.ProductDocRepository;
import com.shopping.service.repository.nosql.SequenceRepository;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductDocService {

    private final ProductDocRepository repository;
    private final ProductDocMapper mapper;
    private final SequenceRepository sequence;
    private final String SEQ_NAME = "product_seq";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ProductDocument findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NullPointerException("Product not find"));
    }

    public PaginationResponse<ProductDocument> getAll(int page, int size, String sorting, String direction) {
        Sort sort = direction.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sorting).ascending() :
                Sort.by(sorting).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ProductDocument> products = repository.findAllByStatus('1', pageable);
        List<ProductDocument> content = products.getContent();

        PaginationResponse<ProductDocument> response = new PaginationResponse<>();
        response.setContent(content);
        response.setPageNumber(products.getNumber());
        response.setPageSize(products.getSize());
        response.setTotalElements(products.getTotalElements());
        response.setTotalPages(products.getTotalPages());
        response.setLast(products.isLast());
        return response;
    }

    public ProductDocument save(ProductDocument document) {
        document.setId(sequence.getNextSequenceId(SEQ_NAME, "product"));
        document.setStatus('1');
        document.setCreatedAt(LocalDateTime.now());
        document.setModifiedAt(LocalDateTime.now());
        return repository.save(document);
    }

    public ProductDocument update(Long id, ProductDocument document) {
        ProductDocument org = repository.findById(id).orElseThrow(() -> new NullPointerException("Product not find"));
        mapper.update(org, document);
        org.setModifiedAt(LocalDateTime.now());
        return repository.save(org);
    }

    public ProductDocument delete(Long id) {
        ProductDocument productDocument = repository.findById(id).orElseThrow(() -> new NullPointerException("Product not find"));
        productDocument.setStatus('0');
        return repository.save(productDocument);
    }

    public double totalSumOfProducts() {
        List<ProductDocument> products = repository.findAll();
        double result = products.stream().reduce(0.0, (x, y) -> x + y.getQuantity() * y.getPrice(), Double::sum);
        return Math.round(result * 10.0) / 10.0;
    }

    public List<ProductDocReport> report() {

        try (MongoClient client = MongoClients.create("mongodb://mongo:password@mongo:27017/?authSource=admin")) {

            MongoDatabase database = client.getDatabase("mongo");
            MongoCollection<Document> collection = database.getCollection("Product");
            LocalDateTime dateTime = LocalDateTime.now().minusWeeks(1);
            List<? extends Bson> pipeline = Arrays.asList(
                    new Document()
                            .append("$project", new Document()
                                    .append("_id", 0)
                                    .append("Product", "$$ROOT")
                            ),
                    new Document()
                            .append("$lookup", new Document()
                                    .append("localField", "Product._id")
                                    .append("from", "OrderDetail")
                                    .append("foreignField", "product")
                                    .append("as", "OrderDetail")
                            ),
                    new Document()
                            .append("$unwind", new Document()
                                    .append("path", "$OrderDetail")
                                    .append("preserveNullAndEmptyArrays", false)
                            ),
                    new Document()
                            .append("$lookup", new Document()
                                    .append("localField", "OrderDetail.order")
                                    .append("from", "Order")
                                    .append("foreignField", "_id")
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
                            .append("$match", new Document()
                                    .append("$and", List.of(
                                                    new Document()
                                                            .append("Transaction.status", "1")
                                                            .append("Transaction.date", new Document()
                                                                    .append("$gte", dateTime.toLocalDate())
                                                            )
                                            )
                                    )
                            ),
                    new Document()
                            .append("$group", new Document()
                                    .append("_id", new Document()
                                            .append("Product\u1390_id", "$Product._id")
                                            .append("Product\u1390name", "$Product.name")
                                            .append("Product\u1390description", "$Product.description")
                                            .append("Product\u1390price", "$Product.price")
                                    )
                                    .append("SUM(OrderDetail\u1390quantity)", new Document()
                                            .append("$sum", "$OrderDetail.quantity")
                                    )
                            ),
                    new Document()
                            .append("$project", new Document()
                                    .append("Product._id", "$_id.Product\u1390_id")
                                    .append("Product.name", "$_id.Product\u1390name")
                                    .append("Product.description", "$_id.Product\u1390description")
                                    .append("Product.price", "$_id.Product\u1390price")
                                    .append("SUM(OrderDetail.quantity)", "$SUM(OrderDetail\u1390quantity)")
                                    .append("_id", 0)
                            ),
                    new Document()
                            .append("$sort", new Document()
                                    .append("SUM(OrderDetail.quantity)", -1)
                                    .append("Product._id", 1)
                            ),
                    new Document()
                            .append("$project", new Document()
                                    .append("_id", 0)
                                    .append("id", "$Product._id")
                                    .append("productName", "$Product.name")
                                    .append("description", "$Product.description")
                                    .append("price", "$Product.price")
                                    .append("quantity", "$SUM(OrderDetail.quantity)")
                            ),
                    new Document()
                            .append("$limit", 10),
                    new Document()
                            .append("$project", new Document()
                                    .append("_id", 0)
                                    .append("id", 1.0)
                                    .append("productName", 1.0)
                                    .append("description", 1.0)
                                    .append("price", 1.0)
                                    .append("quantity", 1.0)
                            )
            );

            List<ProductDocReport> result = new ArrayList<>();
            collection.aggregate(pipeline)
                    .allowDiskUse(true)
                    .forEach(arg -> {
                        String json = arg.toJson();
                        ProductDocReport report = null;
                        try {
                            report = objectMapper.readValue(json, ProductDocReport.class);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                        result.add(report);
                    });
            return result;
        } catch (MongoException e) {
            System.out.println("Exception in parsing Product json!!!");
        }

        return null;
    }

    public List<ProductDocument> search(String name) {
        return repository.findAllByNameLike(name);
    }

}
