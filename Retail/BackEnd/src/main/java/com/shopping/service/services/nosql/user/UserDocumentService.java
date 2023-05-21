package com.shopping.service.services.nosql.user;

import com.shopping.service.document.UserDocument;
import com.shopping.service.repository.nosql.UserDocRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDocumentService {

    private final UserDocRepository repository;

    public List<UserDocument> getAll(){
        return repository.findAll();
    }

    public List<UserDocument> getByNameLike(String username){
        return repository.findByUsername(username);
    }

}
