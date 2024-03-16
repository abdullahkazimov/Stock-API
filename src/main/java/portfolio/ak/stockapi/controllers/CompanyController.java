package portfolio.ak.stockapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import portfolio.ak.stockapi.model.Company;
import portfolio.ak.stockapi.services.interfaces.CompanyService;

import java.util.Map;

@RestController
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    private final CompanyService companyService;
    private final ObjectMapper objectMapper;

    public CompanyController(CompanyService companyService, ObjectMapper objectMapper) {
        this.companyService = companyService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getCompanyToString(@PathVariable Long id) {
        Company company = companyService.getCompanyById(id);
        if (company != null) {
            try {
                String jsonString = objectMapper.writeValueAsString(company.toString());
                return new ResponseEntity<>(jsonString, HttpStatus.OK);
            } catch (JsonProcessingException e) {
                return new ResponseEntity<>("Error processing JSON", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Company not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<String> getAllCompanies() {
        Iterable<Company> companies = companyService.getAllCompanies();
        if (companies != null) {
            try {
                String jsonString = objectMapper.writeValueAsString(companies.toString());
                return new ResponseEntity<>(jsonString, HttpStatus.OK);
            } catch (JsonProcessingException e) {
                return new ResponseEntity<>("Error processing JSON", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Company not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addCompany(@RequestBody Company company) {
        try {
            companyService.addCompany(company);
            return new ResponseEntity<>("Company added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error adding company: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCompanyById(@PathVariable("id") Long id) {
        try {
            boolean deleted = companyService.deleteCompanyById(id);
            if (deleted) {
                return new ResponseEntity<>("Company deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Company not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting company: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCompanyById(@PathVariable Long id, @RequestBody Company updatedCompany) {
        try {
            boolean updated = companyService.updateCompanyById(id, updatedCompany);
            if (updated) {
                return new ResponseEntity<>("Company updated successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Company not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating company: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<String> partiallyUpdateCompany(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        try {
            boolean updated = companyService.partiallyUpdateCompany(id, updates);
            if (updated) {
                return new ResponseEntity<>("Company partially updated successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Company not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error partially updating company: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
