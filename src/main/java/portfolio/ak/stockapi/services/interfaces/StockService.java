package portfolio.ak.stockapi.services.interfaces;

import portfolio.ak.stockapi.model.Stock;

import java.util.Map;

public interface StockService {
    Stock getStockById(Long id);
    Iterable<Stock> getAllStocks();
    Stock addStock(Stock newStock);
    boolean updateStockById(Long id, Stock updatedStock);
    boolean deleteStockById(Long id);

    boolean partiallyUpdateStock(Long id, Map<String, Object> updates);
}
