package portfolio.ak.stockapi.services.interfaces;

import portfolio.ak.stockapi.model.Company;

import java.util.Map;

public interface CompanyService {
    Company getCompanyById(Long id);
    Iterable<Company> getAllCompanies();
    Company addCompany(Company newCompany);
    boolean updateCompanyById(Long id, Company updatedCompany);
    boolean partiallyUpdateCompany(Long id, Map<String, Object> updates);
    boolean deleteCompanyById(Long id);
}
