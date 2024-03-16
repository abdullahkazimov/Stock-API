package portfolio.ak.stockapi.services.implementations;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import portfolio.ak.stockapi.model.Company;
import portfolio.ak.stockapi.model.Stock;
import portfolio.ak.stockapi.repositories.StockRepository;
import portfolio.ak.stockapi.services.interfaces.StockService;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

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
    public boolean partiallyUpdateStock(Long id, Map<String, Object> updates) {
        Optional<Stock> optionalStock = stockRepository.findById(id);
        if (optionalStock.isPresent()) {
            Stock stock = optionalStock.get();
            applyStockPartialUpdates(stock, updates);
            stockRepository.save(stock);
            return true;
        }
        return false;
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
    public boolean deleteStockById(Long id) {
        try {
            stockRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
