package pl.kerrex.eagleeyeweb.database;

import pl.kerrex.eagleeyeweb.logic.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tomek on 25.01.16.
 */
public class CustomerListService {
    private DBConnector connector;

    public CustomerListService() {
        connector = DBConnector.getInstance();
    }

    public List<Customer> createCustomerList() {
        ResultSet rs = null;
        ArrayList<Customer> customers = null;
        try {
            rs = connector.getCustomers();
            customers = new ArrayList<>();
            rs.next();
            while (!rs.isAfterLast()) {
                String name = rs.getString("Nazwa_klienta");
                long id = rs.getLong("idKlient");
                customers.add(new Customer(id, name));
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
        System.out.println(customers);
        return customers;
    }
}
