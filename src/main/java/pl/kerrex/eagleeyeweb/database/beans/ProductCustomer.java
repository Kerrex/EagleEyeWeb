package pl.kerrex.eagleeyeweb.database.beans;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by tomek on 18.02.16.
 */
@Entity
@Table(name = "KlientProdukt")
public class ProductCustomer {
    private long id;
    private Date date;
    private Customer customer;
    private Product product;
    private long quantity;

    public ProductCustomer() {
    }

    public ProductCustomer(long id, Date date, Customer customer, Product product, long quantity) {
        this.id = id;
        this.date = date;
        this.customer = customer;
        this.product = product;
        this.quantity = quantity;
    }

    @Id
    @Column(name = "idKlientProdukt")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "Data")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @ManyToOne
    @JoinColumn(name = "idKlient")
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @ManyToOne
    @JoinColumn(name = "EAN")
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Column(name = "Ilosc")
    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    @Transient
    public String getName() {
        return product.getName();
    }

    @Transient
    public String getEAN() {
        return product.getEAN();
    }
}
