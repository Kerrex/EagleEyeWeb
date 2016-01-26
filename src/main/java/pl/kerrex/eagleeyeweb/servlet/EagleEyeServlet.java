/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.kerrex.eagleeyeweb.servlet;

import pl.kerrex.eagleeyeweb.database.CustomerListService;
import pl.kerrex.eagleeyeweb.logic.Customer;
import pl.kerrex.eagleeyeweb.logic.Product;
import pl.kerrex.eagleeyeweb.database.ProductListService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * @author tomek
 */
@WebServlet(
        name = "EagleEyeServlet",
        urlPatterns = {"/eagleeye"}
)
public class EagleEyeServlet extends HttpServlet {
    ArrayList<Customer> customers = (ArrayList<Customer>) new CustomerListService().createCustomerList();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductListService listService = new ProductListService();

        //Deklaracje zmiennych
        String currentDate = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
        String nextJSP = "/jsp/eagleeye.jsp";

        /*
        startDate - data "od" na pasku daty
        endDate - data "do" na pasku daty
        productList - lista produktów załadowana przez ProductListService
        customerList - lista wszystkich klientów (stąd zmienna globalna)
         */
        req.setAttribute("startDate", currentDate);
        req.setAttribute("endDate", currentDate);
        req.setAttribute("productList", listService.createAllProductList());
        req.setAttribute("customerList", customers);
        getServletContext().getRequestDispatcher(nextJSP).forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Got POST request: " + req.getParameter("action") + " " + Arrays.toString(req.getParameterValues("customers")));
        if (req.getParameter("action").equals("search")) {
            search(req, resp);
        }
    }

    private void search(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductListService listService = new ProductListService();
        //Print kontrolny
        System.out.println("Date: " + req.getParameter("dpstart") + " to " + req.getParameter("dpend"));

        /*
        nextJSP - po prostu ścieżka od JSP
        fromDate - data odczytana z paska
        toDate - patrz wyżej
        selectedCustomers - tablica klientów wybranych z listy

         */
        String nextJSP = "/jsp/eagleeye.jsp";
        String fromDate = req.getParameter("dpstart");
        String toDate = req.getParameter("dpend");
        String selectedCustomers = Arrays.toString(req.getParameterValues("customers"));
        List<Product> productList = listService.createProductList(fromDate, toDate, selectedCustomers);

        /*
        startDate - data ustawiona na pasku, chodzi o to, aby została stara data sprzed zatwierdzenia
        endDate - patrz wyżej
        customerList - lista wszystkich klientów
        productList - lista wyfiltrowanych produktów
        selectedCustomers - klienci wybrani, chodzi o to, aby zostali zaznaczeni po zatwierdzeniu
         */
        req.setAttribute("startDate", fromDate);
        req.setAttribute("endDate", toDate);
        req.setAttribute("customerList", customers);
        req.setAttribute("productList", productList);
        req.setAttribute("selectedCustomers", selectedCustomers);
        getServletContext().getRequestDispatcher(nextJSP).forward(req, resp);
        System.out.println("Post dispatched");
    }
}
