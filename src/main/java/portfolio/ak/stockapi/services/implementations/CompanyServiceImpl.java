package portfolio.ak.stockapi.services.implementations;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import portfolio.ak.stockapi.model.Company;
import portfolio.ak.stockapi.model.Stock;
import portfolio.ak.stockapi.repositories.CompanyRepository;
import portfolio.ak.stockapi.services.interfaces.CompanyService;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

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
    public boolean partiallyUpdateCompany(Long id, Map<String, Object> updates) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isPresent()) {
            Company company = optionalCompany.get();
            applyPartialUpdates(company, updates);
            companyRepository.save(company);
            return true;
        }
        return false;
    }

    private void applyPartialUpdates(Company company, Map<String, Object> updates) {
        for (Map.Entry<String, Object> entry : updates.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            switch (key) {
                case "name":
                    if (value instanceof String) {
                        company.setName((String) value);
                    } else {
                        throw new IllegalArgumentException("Invalid value type for 'name' field");
                    }
                    break;
                case "industry":
                    if (value instanceof String) {
                        company.setIndustry((String) value);
                    } else {
                        throw new IllegalArgumentException("Invalid value type for 'industry' field");
                    }
                    break;
                case "stock":
                    if (value instanceof Map) {
                        Map<String, Object> stockUpdates = (Map<String, Object>) value;
                        Stock stock = company.getStock();
                        applyStockPartialUpdates(stock, stockUpdates);
                    } else {
                        throw new IllegalArgumentException("Invalid value type for 'stock' field");
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unknown field: " + key);
            }
        }
    }

    private void applyStockPartialUpdates(Stock stock, Map<String, Object> updates) {
        for (Map.Entry<String, Object> entry : updates.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            switch (key) {
                case "symbol":
                    if (value instanceof String) {
                        stock.setSymbol((String) value);
                    } else {
                        throw new IllegalArgumentException("Invalid value type for 'symbol' field");
                    }
                    break;
                case "price":
                    if (value instanceof Double) {
                        stock.setPrice((Double) value);
                    } else {
                        throw new IllegalArgumentException("Invalid value type for 'price' field");
                    }
                    break;
                case "change":
                    if (value instanceof Double) {
                        stock.setChange((Double) value);
                    } else {
                        throw new IllegalArgumentException("Invalid value type for 'change' field");
                    }
                    break;
                case "changePercentage":
                    if (value instanceof Double) {
                        stock.setChangePercentage((Double) value);
                    } else {
                        throw new IllegalArgumentException("Invalid value type for 'changePercentage' field");
                    }
                    break;
                case "lastUpdated":
                    if (value instanceof Date) {
                        stock.setLastUpdated((Date) value);
                    } else {
                        throw new IllegalArgumentException("Invalid value type for 'lastUpdated' field");
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unknown field: " + key);
            }
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
