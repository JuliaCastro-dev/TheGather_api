package com.thegather.api.application.controllers;

import com.google.gson.Gson;
import com.thegather.api.domain.entities.Company;
import com.thegather.api.domain.interfaces.services.ICompanyService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("companies")
public class CompanyController {

    private final ICompanyService companyService;

    public CompanyController(ICompanyService companyService) {
        this.companyService = companyService;
    }

    Gson gson = new Gson();

    @GetMapping(value = "/companies")
    public ResponseEntity<String> getAllCompanies() {
        List<Company> companies = companyService.getAllCompanies();
        String jsonString = gson.toJson(companies);
        return ResponseEntity.ok(jsonString);
    }

    @PostMapping(value = "/newCompany")
    public ResponseEntity<String> createCompany(@Valid Company company, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Error when trying to create company, invalid company:  ");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(error.getDefaultMessage()).append(" ");
            }
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }

        Company savedCompany = companyService.createCompany(company);
        String jsonString = gson.toJson(savedCompany);
        return ResponseEntity.ok(jsonString);
    }

    @PutMapping(value = "/company/{id}")
    public ResponseEntity<String> updateCompany(@PathVariable Long id, @Valid @RequestBody Company company, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Error when trying to update company:  ");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(error.getDefaultMessage()).append(" ");
            }
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }

        company.setId(id);

        int updateCompany = companyService.updateCompany(company);

        if (updateCompany == 0) return ResponseEntity.notFound().build();

        Company companyUpdated = companyService.getCompanyById(id);
        String jsonString = gson.toJson(companyUpdated);
        return ResponseEntity.ok(jsonString);
    }

    @GetMapping(value = "/company/{id}")
    public ResponseEntity<String> getCompanyById(@PathVariable Long id) {
        Company company = companyService.getCompanyById(id);
        if (company == null) {
            return ResponseEntity.notFound().build();
        }
        String jsonString = gson.toJson(company);
        return ResponseEntity.ok(jsonString);
    }

    @DeleteMapping("/deleteCompany/{id}")
    public ResponseEntity<Boolean> deleteCompany(@PathVariable Long id) {
        boolean ret = companyService.deleteCompany(id);
        if (!ret) {
            return ResponseEntity.badRequest().body(false);
        } else {
            return ResponseEntity.ok(true);
        }
    }
}
