package pl.kerrex.eagleeyeweb.database;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import pl.kerrex.eagleeyeweb.database.beans.Customer;

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
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            List<Customer> customerList = session.createQuery("from Customer").list();
            return customerList;
        } catch (HibernateException e) {
            e.printStackTrace();

        } finally {
            System.out.println("[DEBUG]: Customer list created");
            session.close();
        }
        return null;
        /*ResultSet rs = null;
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
        return customers;*/
    }

/*    public List<Customer> createRegonCustomerList() {
        ResultSet rs = null;
        ArrayList<Customer> customers = null;
        try {
            rs = connector.getCustomersWithRegon();
            customers = (ArrayList<Customer>) parseCustomersWithRegon(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return customers;
    }*/

    public List<Customer> findCustomers(String cname, String cregon) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Customer> customerList = null;
        try {
            if (cname.equals("")) {
                customerList = session.createQuery("FROM Customer WHERE REGON LIKE :regon")
                        .setParameter("regon", "%" + cregon + "%")
                        .list();
            } else if (cregon.equals("")) {
                customerList = session.createQuery("FROM Customer WHERE UPPER(name) LIKE UPPER(:name) ")
                        .setParameter("name", "%" + cname + "%")
                        .list();
            } else {
                customerList = session.createQuery("FROM Customer WHERE UPPER(name) LIKE UPPER(:name) AND REGON LIKE :regon")
                        .setParameter("name", "%" + cname + "%")
                        .setParameter("regon", "%" + cregon + "%")
                        .list();
            }
            return customerList;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
            System.out.println("[DEBUG]: Found customers with name " + cname + " and REGON " + cregon);
        }
        return null;
        /*ResultSet rs = null;
        ArrayList<Customer> customers = null;
        try {
            rs = connector.findCustomers(cname, cregon);
            customers = (ArrayList<Customer>) parseCustomersWithRegon(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return customers;*/
    }

/*    private List<Customer> parseCustomersWithRegon(ResultSet rs) throws SQLException {
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
    }*/

    public boolean insertCustomer(String name, String REGON) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            Customer newCustomer = new Customer();
            newCustomer.setName(name);
            newCustomer.setREGON(REGON);
            session.save(newCustomer);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        } finally {

            session.close();
            return true;
        }
        /*boolean isSuccessful;
        try {
            connector.insertCustomer(name, REGON);
            isSuccessful = true;
        } catch (SQLException e) {
            isSuccessful = false;
            e.printStackTrace();
        }
        return isSuccessful;*/
    }

    public void removeCustomer(long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            Customer oldCustomer = session.get(Customer.class, id);
            session.delete(oldCustomer);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {

            session.close();
        }
        /*try {
            connector.removeCustomer(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }

    public Customer getCustomerById(long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Customer customer = session.get(Customer.class, id);
            return customer;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
        /*ResultSet rs = null;
        try {
            rs = connector.getCustomerById(id);
            rs.next();
            long idCustomer = rs.getLong(1);
            String name = rs.getString(2);
            String REGON = rs.getString(3);
            return new Customer(idCustomer, name, REGON);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;*/
    }

    public boolean updateCustomer(long id, String name, String regon) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            Customer customer = session.get(Customer.class, id);
            customer.setName(name);
            customer.setREGON(regon);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
        }
        /*try {
            connector.editCustomer(id, name, regon);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }*/

    }
}
