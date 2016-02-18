package pl.kerrex.eagleeyeweb.database.beans;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by tomek on 17.02.16.
 */
@Entity
@Table(name = "Klient")
public class Customer {
    private long id;
    private String name;
    private String REGON;
    private Set<ProductCustomer> productCustomers;
    public Customer() {
    }

    public Customer(long id, String name, String REGON) {
        this.id = id;
        this.name = name;
        this.REGON = REGON;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idKlient", unique = true, nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long customerId) {
        this.id = customerId;
    }

    @Column(name = "Nazwa_klienta", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String customerName) {
        this.name = customerName;
    }

    @Column(name = "REGON", nullable = false)
    public String getREGON() {
        return REGON;
    }

    public void setREGON(String REGON) {
        this.REGON = REGON;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    public Set<ProductCustomer> getProductCustomers() {
        return productCustomers;
    }

    public void setProductCustomers(Set<ProductCustomer> productCustomers) {
        this.productCustomers = productCustomers;
    }
}
