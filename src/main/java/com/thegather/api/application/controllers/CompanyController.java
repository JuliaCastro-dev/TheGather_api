package com.thegather.api.application.controllers;

import com.google.gson.Gson;
import com.thegather.api.domain.entities.Company;
import com.thegather.api.domain.interfaces.services.ICompanyService;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final ICompanyService companyService;
    private final Gson gson;

    public CompanyController(ICompanyService companyService, Gson gson) {
        this.companyService = companyService;
        this.gson = gson;
    }

    @Operation(summary = "Get all companies")
    @GetMapping
    public ResponseEntity<String> getAllCompanies() {
        List<Company> companies = companyService.getAllCompanies();
        String jsonString = gson.toJson(companies);
        return ResponseEntity.ok(jsonString);
    }

    @Operation(summary = "Create a new company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created company"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/new")
    public ResponseEntity<String> createCompany(@Valid @RequestBody Company company, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Error when trying to create company: ");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(error.getDefaultMessage()).append(" ");
            }
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }

        Company savedCompany = companyService.createCompany(company);
        String jsonString = gson.toJson(savedCompany);
        return ResponseEntity.ok(jsonString);
    }

    @Operation(summary = "Update an existing company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated company"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Company not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCompany(@PathVariable Long id, @Valid @RequestBody Company company, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Error when trying to update company: ");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(error.getDefaultMessage()).append(" ");
            }
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }

        company.setId(id);

        int updated = companyService.updateCompany(company);

        if (updated == 0) {
            return ResponseEntity.notFound().build();
        }

        Company updatedCompany = companyService.getCompanyById(id);
        String jsonString = gson.toJson(updatedCompany);
        return ResponseEntity.ok(jsonString);
    }

    @Operation(summary = "Get a company by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved company"),
            @ApiResponse(responseCode = "404", description = "Company not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<String> getCompanyById(@PathVariable Long id) {
        Company company = companyService.getCompanyById(id);
        if (company == null) {
            return ResponseEntity.notFound().build();
        }
        String jsonString = gson.toJson(company);
        return ResponseEntity.ok(jsonString);
    }

    @Operation(summary = "Delete a company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted company"),
            @ApiResponse(responseCode = "400", description = "Failed to delete company")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCompany(@PathVariable Long id) {
        boolean deleted = companyService.deleteCompany(id);
        return deleted ? ResponseEntity.ok(true) : ResponseEntity.badRequest().body(false);
    }
}
