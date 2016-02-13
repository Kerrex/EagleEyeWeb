package pl.kerrex.eagleeyeweb.database;


import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.*;
import java.text.SimpleDateFormat;

/**
 * Created by tomek on 15.12.15.
 */
public class DBConnector {
    private static DBConnector instance;
    private MysqlDataSource dataSource;
    private Connection con;
    private Statement st;
    private int lastBoughtProductId;

    private DBConnector() {
        dataSource = new MysqlDataSource();
        dataSource.setUser("tomek");
        dataSource.setPassword("tomek");
        dataSource.setServerName("kerrex.pl");
        try {
            connect();
        } catch (SQLException ex) {
            System.err.println("Nie można nawiązać połączenia z bazą danych. Sprawdź konfigurację i połączenie z internetem, a następnie spróbuj ponownie");
        }
    }

    public static synchronized DBConnector getInstance() {

        if (instance == null || instance.isClosed()) {
            instance = new DBConnector();
        }
        return instance;
    }

    private void connect() throws SQLException {
        con = dataSource.getConnection();
        st = con.createStatement();
    }

    public boolean isClosed() {
        try {
            return con.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ResultSet getCustomers() throws SQLException {
        return st.executeQuery
                ("SELECT idKlient, Nazwa_klienta FROM eagleeye.Klient ORDER BY idKlient ASC;");
    }

    public ResultSet getCustomersWithRegon() throws SQLException {
        return st.executeQuery
                ("SELECT idKlient, Nazwa_klienta, REGON FROM eagleeye.Klient ORDER BY idKlient ASC;");
    }

    public ResultSet findCustomers(String cname, String cregon) throws SQLException {

        PreparedStatement ps;
        if (cname.equals("")) {
            ps = con.prepareStatement
                    ("SELECT idKlient, Nazwa_klienta, REGON FROM eagleeye.Klient " +
                            "WHERE UPPER(REGON) LIKE ? ORDER BY idKlient ASC");
            ps.setString(1, "%" + cregon.toUpperCase() + "%");
        } else if (cregon.equals("")) {
            ps = con.prepareStatement
                    ("SELECT idKlient, Nazwa_klienta, REGON FROM eagleeye.Klient " +
                            "WHERE UPPER(Nazwa_klienta) LIKE ? ORDER BY idKlient ASC");
            ps.setString(1, "%" + cname.toUpperCase() + "%");
        } else {
            ps = con.prepareStatement("SELECT idKlient, Nazwa_klienta, REGON FROM eagleeye.Klient " +
                    "WHERE UPPER(REGON) LIKE ? AND UPPER(Nazwa_klienta) LIKE ? ORDER BY idKlient ASC");
            ps.setString(1, "%" + cregon.toUpperCase() + "%");
            ps.setString(2, "%" + cname.toUpperCase() + "%");
        }

        return ps.executeQuery();
    }
    public int getCustomersSetLength() throws SQLException {
        int length;
        ResultSet rs = st.executeQuery("SELECT Count(*) FROM eagleeye.Klient;");
        rs.next();
        length = rs.getInt(1);
        return length;
    }

    /*public ResultSet getCustomers(int id) throws SQLException {

        return st.executeQuery
                ("SELECT idKlient, Nazwa_klienta, REGON FROM eagleeye.Klient WHERE idKlient > "+id+" ORDER BY idKlient ASC;");
    }*/

    /*public int getCustomersSetLength(int id) throws SQLException {
        int length;
        ResultSet rs = st.executeQuery("SELECT Count(*) FROM eagleeye.Klient WHERE idKlient > "+id+";");
        rs.next();
        length = rs.getInt(1);
        return length;
    }*/



    /*public int getBoughtProductsLength() throws SQLException {
        int length;
        ResultSet rs = st.executeQuery("SELECT Count(*) FROM eagleeye.KlientProdukt;");
        rs.next();
        length = rs.getInt(1);
        return length;
    }*/

    public ResultSet getBoughtProducts(java.util.Date dateFrom, java.util.Date dateTo, String customerIds) throws SQLException {

        PreparedStatement ps = con.prepareStatement("SELECT Nazwa_produktu, eagleeye.KlientProdukt.EAN, Sum(Ilosc) FROM eagleeye.KlientProdukt " +
                "JOIN eagleeye.Produkt ON eagleeye.KlientProdukt.EAN = eagleeye.Produkt.EAN  " +
                "WHERE idKlient IN (" + customerIds + ") AND Data BETWEEN ? AND ? GROUP BY Nazwa_produktu, eagleeye.KlientProdukt.EAN;");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        //ps.setObject(1, customerIds);
        //System.out.println(customerIds);
        System.out.println(sdf.format(dateFrom));
        ps.setString(1, sdf.format(dateFrom));
        ps.setString(2, sdf.format(dateTo));
        System.out.println(ps.toString());
        //return st.executeQuery("SELECT EAN, Sum(Ilosc) FROM eagleeye.KlientProdukt WHERE idKlient IN (3,4,5) AND Data BETWEEN '2015-10-1' AND '2015-12-5' GROUP BY EAN;");
        return ps.executeQuery();
        /*return st.executeQuery
                ("SELECT idKlientProdukt, idKlient, EAN, Data, Ilosc FROM eagleeye.KlientProdukt WHERE idKlientProdukt > "+id+" ORDER BY idKlientProdukt ASC;");*/

    }


    public ResultSet getAllBoughtProducts() throws SQLException {
        return st.executeQuery("SELECT Nazwa_produktu, eagleeye.KlientProdukt.EAN, Sum(Ilosc) FROM eagleeye.KlientProdukt " +
                "JOIN eagleeye.Produkt ON eagleeye.KlientProdukt.EAN = eagleeye.Produkt.EAN " +
                "GROUP BY Nazwa_produktu, eagleeye.KlientProdukt.EAN;");
    }
    public long getBoughtProductsLength(Date dateFrom, Date dateTo, long customerId) throws SQLException {

        PreparedStatement ps = con.prepareStatement("SELECT Nazwa, EAN, Ilosc FROM eagleeye.KlientProdukt WHERE idKlientProdukt = ? AND Data BETWEEN ? AND ?");
        ps.setLong(1, customerId);
        ps.setDate(2, dateFrom);
        ps.setDate(3, dateTo);

        ResultSet rs = ps.executeQuery();
        rs.next();
        long count = rs.getLong(1);
        rs.close();

        return count;

        /*int length;
        ResultSet rs = st.executeQuery("SELECT Count(*) FROM eagleeye.KlientProdukt WHERE idKlientProdukt > "+id+";");
        rs.next();
        length = rs.getInt(1);
        return length;*/
    }

    public ResultSet getProducts() throws SQLException {
        return st.executeQuery
                ("SELECT EAN, Nazwa_produktu FROM eagleeye.Produkt;");
    }

    public int getProductsLength() throws SQLException {
        int length;
        ResultSet rs = st.executeQuery("SELECT Count(*) FROM eagleeye.Produkt;");
        rs.next();
        length = rs.getInt(1);
        rs.close();
        return length;
    }

    public void insertCustomer(String name, String REGON) throws SQLException {
            PreparedStatement ps = con.prepareStatement("INSERT INTO eagleeye.Klient (Nazwa_klienta, REGON) VALUES (?, ?)");
            ps.setString(1, name);
            ps.setString(2, REGON);
            ps.execute();
    }

    public void removeCustomer(long id) throws SQLException {
        PreparedStatement ps = con.prepareStatement("DELETE FROM eagleeye.Klient WHERE idKlient = ?");
        ps.setLong(1, id);
        ps.execute();
    }

    public ResultSet getCustomerById(long id) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT idKlient, Nazwa_klienta, REGON FROM eagleeye.Klient WHERE idKlient = ?");
        ps.setLong(1, id);
        return ps.executeQuery();
    }

    public void editCustomer(long id, String name, String regon) throws SQLException {
        PreparedStatement ps = con.prepareStatement("UPDATE eagleeye.Klient SET Nazwa_klienta = ?, REGON = ? WHERE idKlient = ?");
        ps.setString(1, name);
        ps.setString(2, regon);
        ps.setLong(3, id);
        ps.execute();
    }

    public void insertProduct(String name, String ean) throws SQLException {
        PreparedStatement ps = con.prepareStatement("INSERT INTO eagleeye.Produkt (Nazwa_produktu, EAN) VALUES (?, ?)");
        ps.setString(1, name);
        ps.setString(2, ean);
        ps.execute();
    }

    public void removeProduct(String ean) throws SQLException {
        PreparedStatement ps = con.prepareStatement("DELETE FROM eagleeye.Produkt WHERE EAN = ?");
        ps.setString(1, ean);
        ps.execute();
    }

    public void updateProduct(String oldEan, String ean, String name) throws SQLException {
        PreparedStatement ps = con.prepareStatement("UPDATE eagleeye.Produkt SET EAN = ? , Nazwa_produktu = ? WHERE EAN = ?");
        //PreparedStatement ps2 = con.prepareStatement("UPDATE ")
        ps.setString(1, ean);
        ps.setString(2, name);
        ps.setString(3, oldEan);
        ps.execute();
    }

    public ResultSet getProductByEan(String ean) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT Nazwa_produktu, EAN FROM eagleeye.Produkt WHERE EAN = ?");
        ps.setString(1, ean);
        return ps.executeQuery();
    }


    /*public ResultSet getProductsxcept(String[] eans) throws SQLException {
        StringBuilder NOTIN = new StringBuilder("EAN NOT IN (");
        for(int i = 0 ; i < eans.length ; i++) {
            if(i < eans.length - 1)
                NOTIN.append(eans[i]).append(",");
            else
                NOTIN.append(eans[i]).append(")");
        }
        return st.executeQuery
                ("SELECT EAN, Nazwa_produktu FROM eagleeye.Produkt WHERE "+NOTIN.toString()+";");
    }

    public int getProductsExceptLength(String[] eans) throws SQLException {
        int length;
        *//*StringBuilder NOTIN = new StringBuilder("EAN NOT IN (");
        for(int i = 0 ; i < eans.length ; i++) {
            if(i < eans.length - 1)
                NOTIN.append(eans[i]).append(",");
            else
                NOTIN.append(eans[i]).append(")");
        }*//*
        String NOTIN = Arrays.toString( eans );
        NOTIN = NOTIN.substring( 1, NOTIN.length()-1 );
        ResultSet rs = st.executeQuery("SELECT Count(*) FROM eagleeye.Produkt WHERE "+NOTIN+";");
        rs.next();
        length = rs.getInt(1);
        return length;
    }*/
}