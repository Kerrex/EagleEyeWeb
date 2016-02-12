package pl.kerrex.eagleeyeweb.database;

import pl.kerrex.eagleeyeweb.logic.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tomek on 12.02.16.
 */
public class ProductListService {
    DBConnector connector;

    public ProductListService() {
        this.connector = DBConnector.getInstance();
    }

    public List<Product> createProductList() {
        ArrayList<Product> products = new ArrayList<>();
        ResultSet rs = null;
        try {
            rs = connector.getProducts();
            rs.next();
            while (!rs.isAfterLast()) {
                String EAN = rs.getString(1);
                String name = rs.getString(2);
                products.add(new Product(name, EAN));
                rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return products;
    }

}
