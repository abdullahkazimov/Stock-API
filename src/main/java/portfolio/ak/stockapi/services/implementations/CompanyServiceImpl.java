package portfolio.ak.stockapi.services.implementations;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import portfolio.ak.stockapi.model.Company;
import portfolio.ak.stockapi.repositories.CompanyRepository;
import portfolio.ak.stockapi.services.interfaces.CompanyService;

@Primary
@Profile({"CompanyService1", "default"})
@Service
public class CompanyServiceImpl implements CompanyService {

    private CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

    @Override
    public Iterable<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company addCompany(Company newCompany) {
        return companyRepository.save(newCompany);
    }

    @Override
    public boolean updateCompanyById(Long id, Company updatedCompany) {
        try {
            companyRepository.deleteById(id);
            updatedCompany.setId(id);
            companyRepository.save(updatedCompany);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteCompanyById(Long id) {
        try {
            companyRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
