package pl.kerrex.eagleeyeweb.database;


import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.swing.*;
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

    public void insertCustomer(String name, String REGON) {
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO eagleeye.Klient (Nazwa_klienta, REGON) VALUES (?, ?)");
            ps.setString(1, name);
            ps.setString(2, REGON);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Podany klient już istnieje!");
        }

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