package portfolio.ak.stockapi.services.interfaces;

import portfolio.ak.stockapi.model.Company;

public interface CompanyService {
    Company getCompanyById(Long id);
    Iterable<Company> getAllCompanies();
    Company addCompany(Company newCompany);
    boolean updateCompanyById(Long id, Company updatedCompany);
    boolean deleteCompanyById(Long id);
}
