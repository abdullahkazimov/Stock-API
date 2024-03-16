package portfolio.ak.stockapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import portfolio.ak.stockapi.model.Stock;
import portfolio.ak.stockapi.services.interfaces.StockService;

import java.util.Map;

@RestController
@RequestMapping("/stock")
public class StockController {
    @Autowired
    private final StockService stockService;
    private final ObjectMapper objectMapper;

    public StockController(StockService stockService, ObjectMapper objectMapper) {
        this.stockService = stockService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getStockToString(@PathVariable Long id) {
        Stock stock = stockService.getStockById(id);
        if (stock != null) {
            try {
                String jsonString = objectMapper.writeValueAsString(stock.toString());
                return new ResponseEntity<>(jsonString, HttpStatus.OK);
            } catch (JsonProcessingException e) {
                return new ResponseEntity<>("Error processing JSON", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Stock not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<String> getAllStocks() {
        Iterable<Stock> stocks = stockService.getAllStocks();
        if (stocks != null) {
            try {
                String jsonString = objectMapper.writeValueAsString(stocks.toString());
                return new ResponseEntity<>(jsonString, HttpStatus.OK);
            } catch (JsonProcessingException e) {
                return new ResponseEntity<>("Error processing JSON", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Stock not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addStock(@RequestBody Stock stock) {
        try {
            stockService.addStock(stock);
            return new ResponseEntity<>("Stock added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error adding stock: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStockById(@PathVariable("id") Long id) {
        try {
            boolean deleted = stockService.deleteStockById(id);
            if (deleted) {
                return new ResponseEntity<>("Stock deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Stock not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting stock: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateStockById(@PathVariable Long id, @RequestBody Stock updatedStock) {
        try {
            boolean updated = stockService.updateStockById(id, updatedStock);
            if (updated) {
                return new ResponseEntity<>("Stock updated successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Stock not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating stock: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<String> partiallyUpdateStock(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        try {
            boolean updated = stockService.partiallyUpdateStock(id, updates);
            if (updated) {
                return new ResponseEntity<>("Stock partially updated successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Stock not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error partially updating stock: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}