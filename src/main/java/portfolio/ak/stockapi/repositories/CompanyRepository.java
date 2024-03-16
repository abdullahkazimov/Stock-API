package portfolio.ak.stockapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import portfolio.ak.stockapi.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
