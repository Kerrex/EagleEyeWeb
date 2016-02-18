package pl.kerrex.eagleeyeweb.database.beans;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by tomek on 18.02.16.
 */
@Entity
@Table(name = "Produkt")
public class Product {
    private String name;
    private String EAN;
    private Set<ProductCustomer> productCustomers;

    public Product() {
    }

    public Product(String name, String EAN) {
        this.name = name;
        this.EAN = EAN;
    }

    @Column(name = "Nazwa_produktu", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Id
    @Column(name = "EAN", nullable = false, length = 15)
    public String getEAN() {
        return EAN;
    }

    public void setEAN(String EAN) {
        this.EAN = EAN;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    public Set<ProductCustomer> getProductCustomers() {
        return productCustomers;
    }

    public void setProductCustomers(Set<ProductCustomer> productCustomers) {
        this.productCustomers = productCustomers;
    }
}
