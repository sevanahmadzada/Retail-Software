package com.shopping.service.security.controller;

import com.shopping.service.model.sec_dto.JwtRequest;
import com.shopping.service.model.sec_dto.JwtResponse;
import com.shopping.service.model.sec_dto.RefreshJwtRequest;
import com.shopping.service.repository.sql.UserRepository;
import com.shopping.service.security.service.AuthService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.security.auth.message.AuthException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class AuthController {

    private final UserRepository repository;
    private final AuthService authService;
//    private final PasswordService service;

    @PostMapping("login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody JwtRequest authRequest) throws Exception {
        final JwtResponse token = authService.login(authRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("token")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody RefreshJwtRequest request) throws AuthException {
        final JwtResponse token = authService.getAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }


    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("/admin")
    public String forAdmin() {
//        service.getAllAndChangePassword();
        return "ADMIN!";
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping("/user")
    public String forUser() {
        return "USER!!!";
    }


    //    @PostMapping("refresh")
//    @Operation(summary = "new refresh", description = "get refresh-token", tags = {"Auth"}, security = @SecurityRequirement(name = "bearerAuth"))
//    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody RefreshJwtRequest request) {
//        final JwtResponse token = authService.refresh(request.getRefreshToken());
//        return ResponseEntity.ok(token);
//    }
}
