package com.shopping.service.controller.nosql;

import com.shopping.service.services.nosql.user.UserDocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nosql/users")
public class UserDocController {

    private final UserDocumentService service;

    public UserDocController(UserDocumentService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/like")
    public ResponseEntity<?> like(@RequestParam String username){
        return ResponseEntity.ok(service.getByNameLike(username));
    }

}
