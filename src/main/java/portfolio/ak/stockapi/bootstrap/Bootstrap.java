package portfolio.ak.stockapi.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import portfolio.ak.stockapi.model.Company;
import portfolio.ak.stockapi.model.Stock;
import portfolio.ak.stockapi.repositories.CompanyRepository;
import portfolio.ak.stockapi.repositories.StockRepository;

import java.util.Date;

@Component
public class Bootstrap implements CommandLineRunner {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public void run(String... args) throws Exception {
        Company company = new Company(1L,"Apple", "Technology");
        Stock stock = new Stock(1L,"A",1,1,1,new Date());
        company.setStock(stock);
        stock.setCompany(company);

        stockRepository.save(stock);
        companyRepository.save(company);
    }
}
