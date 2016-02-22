/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.kerrex.eagleeyeweb.database;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import pl.kerrex.eagleeyeweb.database.beans.ProductCustomer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tomek
 */
public class BoughtProductListService {
    //private final DBConnector db;
    //private final List<Product> productList;

    //public BoughtProductListService() {
    //    db = DBConnector.getInstance();
    //}

    /**
     * @return
     */
    public List<ProductCustomer> createAllProductList() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            List<ProductCustomer> productCustomers = session.createQuery
                    ("SELECT product.name, product.EAN, SUM (quantity) FROM ProductCustomer GROUP BY product.name, product.EAN").list();
            return productCustomers;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
            System.out.println("[DEBUG]; All products list created");
        }
        /*ResultSet rs = null;
        try {
            rs = db.getAllBoughtProducts();
            return fillProductList(rs);
        } catch (SQLException ex) {
            Logger.getLogger(BoughtProductListService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;*/
        return null;
    }

    public List<ProductCustomer> createProductList(String fromDateString, String toDateString, String[] customers) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            List<ProductCustomer> productCustomers;
            List<Long> parsedCustomers = Arrays.stream(customers).map(Long::parseLong).collect(Collectors.toList());
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            if (parsedCustomers.isEmpty()) {
                productCustomers = session.createQuery("SELECT product.name, product.EAN, SUM (quantity) FROM ProductCustomer " +
                        "WHERE date BETWEEN :fromDate AND :toDate GROUP BY product.name, product.EAN ")
                        .setParameter("fromDate", fromDateString)
                        .setParameter("toDate", toDateString)
                        .list();
            } else {
                productCustomers = session.createQuery("SELECT product.name, product.EAN, SUM (quantity) FROM ProductCustomer " +
                        "WHERE customer.id IN :customers AND date BETWEEN :fromDate AND :toDate GROUP BY product.name, product.EAN")
                        .setParameterList("customers", parsedCustomers)
                        .setParameter("fromDate", sdf.parse(fromDateString))
                        .setParameter("toDate", sdf.parse(toDateString))
                        .list();
            }
            return productCustomers;
        } catch (HibernateException e) {
            e.printStackTrace();

        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            session.close();
            System.out.println("[DEBUG]: Product list created");
        }
        /*ResultSet rs = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        try {
            Date fromDate = sdf.parse(fromDateString);
            Date toDate = sdf.parse(toDateString);
            if (customers.equals("null")) customers = "SELECT idKlient FROM eagleeye.Klient";
            else customers = customers.substring(1, customers.length() - 1);
            rs = db.getBoughtProducts(fromDate, toDate, customers);
            return fillProductList(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;*/
        return null;
    }

/*    private List<Product> fillProductList(ResultSet rs) throws SQLException {
        List<Product> productList = new ArrayList<>();
        rs.next();
        while (!rs.isAfterLast()) {
            String name = rs.getString(1);
            String EAN = rs.getString(2);
            int quantity = rs.getInt(3);
            productList.add(new Product(name, EAN, quantity));
            rs.next();
        }
        rs.close();
        return productList;
    }*/

    public int countProducts(Date start, Date end) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            int count = ((Number) (session.createQuery("SELECT sum(quantity) FROM ProductCustomer WHERE date BETWEEN :endDate AND :startDate")
                    .setParameter("endDate", end)
                    .setParameter("startDate", start)
                    .iterate().next())).intValue();
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            session.close();
            System.out.println("[DEBUG]: Counted products");
        }
        /* ResultSet rs = null;
        int count = 0;

        try {
            rs = db.getBoughtProductsCount(end, start);
            rs.next();
            count = rs.getInt(1);
            System.out.println(count);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return count;*/
    }

}
