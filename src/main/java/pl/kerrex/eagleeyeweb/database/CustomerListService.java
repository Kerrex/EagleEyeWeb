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
            customers = (ArrayList<Customer>) parseCustomers(rs);
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

    public List<Customer> createRegonCustomerList() {
        ResultSet rs = null;
        ArrayList<Customer> customers = null;
        try {
            rs = connector.getCustomersWithRegon();
            customers = (ArrayList<Customer>) parseCustomersWithRegon(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public List<Customer> findCustomers(String cname, String cregon) {
        ResultSet rs = null;
        ArrayList<Customer> customers = null;
        try {
            rs = connector.findCustomers(cname, cregon);
            customers = (ArrayList<Customer>) parseCustomersWithRegon(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    private List<Customer> parseCustomersWithRegon(ResultSet rs) throws SQLException {
        rs.next();
        ArrayList<Customer> customers = new ArrayList<>();
        while (!rs.isAfterLast()) {
            String name = rs.getString("Nazwa_klienta");
            long id = rs.getLong("idKlient");
            String REGON = rs.getString("REGON");
            customers.add(new Customer(id, name, REGON));
            rs.next();
        }
        return customers;
    }

    private List<Customer> parseCustomers(ResultSet rs) throws SQLException {
        rs.next();
        ArrayList<Customer> customers = new ArrayList<>();
        while (!rs.isAfterLast()) {
            String name = rs.getString("Nazwa_klienta");
            long id = rs.getLong("idKlient");
            customers.add(new Customer(id, name));
            rs.next();
        }
        return customers;
    }

    public boolean insertCustomer(String name, String REGON) {
        boolean isSuccessful;
        try {
            connector.insertCustomer(name, REGON);
            isSuccessful = true;
        } catch (SQLException e) {
            isSuccessful = false;
            e.printStackTrace();
        }
        return isSuccessful;
    }
}
