package pl.kerrex.eagleeyeweb.database;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import pl.kerrex.eagleeyeweb.database.beans.Product;

import java.util.List;

/**
 * Created by tomek on 12.02.16.
 */
public class ProductListService {
 /*   DBConnector connector;

    public ProductListService() {
        this.connector = DBConnector.getInstance();
    }*/

    public List<Product> createProductList() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            List<Product> productList = session.createQuery("FROM Product").list();
            return productList;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            System.out.println("[DEBUG]: Product list created");
            session.close();
        }
        return null;
        /* ArrayList<Product> products = new ArrayList<>();
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
        return products;*/
    }

    public boolean addProduct(String name, String ean) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            Product product = new Product();
            product.setName(name);
            product.setEAN(ean);
            session.save(product);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        } finally {
            System.out.println("[DEBUG]: Product with name " + name + " and EAN " + ean + " added");
            session.close();
        }
        /*try {
            connector.insertProduct(name, ean);
            return true;
        } catch (SQLException e) {
            return false;
        }*/
    }

    public void deleteProduct(String ean) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            Product product = session.get(Product.class, ean);
            session.delete(product);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
            System.out.println("[DEBUG]: Deleted product with ean " + ean);
        }
        /* try {
            connector.removeProduct(ean);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }

    public boolean updateProduct(String oldEan, String ean, String name) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            session.createQuery("UPDATE Product SET EAN = :ean, name = :name WHERE EAN = :oldEan")
                    .setParameter("ean", ean)
                    .setParameter("name", name)
                    .setParameter("oldEan", oldEan)
                    .executeUpdate();
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        } finally {
            System.out.println("[DEBUG]: Updated product - old ean: " +
                    "" + oldEan + ", new ean: " + ean + ", name " + name);
            session.close();
        }
        /* try {
            connector.updateProduct(oldEan, ean, name);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }*/
    }

    public Product getProductByEan(String ean) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Product product = session.get(Product.class, ean);
            return product;
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
        /*ResultSet rs = null;
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
        }*/
    }
}
