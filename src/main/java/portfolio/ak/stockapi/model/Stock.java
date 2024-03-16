package portfolio.ak.stockapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "stocks")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String symbol;
    private double price;
    private double change;
    private double changePercentage;
    private Date lastUpdated;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "stock_company",
            joinColumns = @JoinColumn(name = "stock_id"),
            inverseJoinColumns = @JoinColumn(name = "company_id")
    )
    private Company company;

    public Stock(Long id, String symbol, double price, double change, double changePercentage, Date lastUpdated) {
        this.id = id;
        this.symbol = symbol;
        this.price = price;
        this.change = change;
        this.changePercentage = changePercentage;
        this.lastUpdated = lastUpdated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stock stock = (Stock) o;

        return id.equals(stock.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
