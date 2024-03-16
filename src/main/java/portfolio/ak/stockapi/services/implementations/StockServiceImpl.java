package portfolio.ak.stockapi.services.implementations;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import portfolio.ak.stockapi.model.Stock;
import portfolio.ak.stockapi.repositories.StockRepository;
import portfolio.ak.stockapi.services.interfaces.StockService;

@Primary
@Profile({"StockService1", "default"})
@Service
public class StockServiceImpl implements StockService {

    private StockRepository stockRepository;

    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public Stock getStockById(Long id) {
        return stockRepository.findById(id).orElse(null);
    }

    @Override
    public Iterable<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    @Override
    public Stock addStock(Stock newStock) {
        return stockRepository.save(newStock);
    }

    @Override
    public boolean updateStockById(Long id, Stock updatedStock) {
        try {
            stockRepository.deleteById(id);
            updatedStock.setId(id);
            stockRepository.save(updatedStock);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteStockById(Long id) {
        try {
            stockRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
