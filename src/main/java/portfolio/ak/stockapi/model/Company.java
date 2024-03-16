package portfolio.ak.stockapi.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String industry;

    @OneToOne(mappedBy = "company")
    private Stock stock;

    public Company(Long id, String name, String industry) {
        this.id = id;
        this.name = name;
        this.industry = industry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        return id.equals(company.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
