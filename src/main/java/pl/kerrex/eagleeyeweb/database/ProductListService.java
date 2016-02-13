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

    public boolean addProduct(String name, String ean) {
        try {
            connector.insertProduct(name, ean);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public void deleteProduct(String ean) {
        try {
            connector.removeProduct(ean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean updateProduct(String oldEan, String ean, String name) {
        try {
            connector.updateProduct(oldEan, ean, name);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Product getProductByEan(String ean) {
        ResultSet rs = null;
        try {
            rs = connector.getProductByEan(ean);
            rs.next();
            String name = rs.getString(1);
            String EAN = rs.getString(2);
            return new Product(name, EAN);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
