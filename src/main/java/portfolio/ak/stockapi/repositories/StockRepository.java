package portfolio.ak.stockapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import portfolio.ak.stockapi.model.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {
}
