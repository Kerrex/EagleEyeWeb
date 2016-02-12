package pl.kerrex.eagleeyeweb.servlet;

import pl.kerrex.eagleeyeweb.database.CustomerListService;
import pl.kerrex.eagleeyeweb.logic.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by tomek on 10.02.16.
 */
@WebServlet(
        name = "CustomerServlet",
        urlPatterns = {"/customers"}
)
public class CustomersServlet extends HttpServlet {
    CustomerListService customerListService = new CustomerListService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action.equals("addcustomer")) {
            addCustomer(req, resp);
        } else if (action.equals("editcustomer")) {
            editCustomer(req, resp);
        }
    }

    private void editCustomer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("idCustomer"));
        String name = req.getParameter("name");
        String REGON = req.getParameter("regon");

        boolean isSuccessful = customerListService.updateCustomer(id, name, REGON);

        if (isSuccessful) {
            req.setAttribute("state", "success");
            showCustomers(req, resp);
        } else {
            req.setAttribute("state", "failed");
            forwardToEditCustomer(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Wczytuje parametry i sprawdzam czy jakieś są podane
        String action = req.getParameter("action");
        if (action == null) {
            //Po prostu pokazuję całą listę jeśli nie ma żadnych parametrów
            showCustomers(req, resp);
        } else if (action.equals("search")) {
            search(req, resp);
        } else if (action.equals("addcustomer")) {
            forwardToAddCustomer(req, resp);
        } else if (action.equals("erase")) {
            removeCustomer(req, resp);
        } else if (action.equals("edit")) {
            forwardToEditCustomer(req, resp);
        }
    }

    private void forwardToEditCustomer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("idCustomer"));
        Customer customer = customerListService.getCustomerById(id);
        req.setAttribute("customer", customer);
        getServletContext().getRequestDispatcher("/jsp/Klienci/editcustomer.jsp").forward(req, resp);
    }

    private void removeCustomer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("idCustomer"));
        customerListService.removeCustomer(id);
        showCustomers(req, resp);

    }

    private void forwardToAddCustomer(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        getServletContext().getRequestDispatcher("/jsp/Klienci/addcustomer.jsp").forward(req, resp);
    }

    private void search(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String regon = req.getParameter("regon");
        System.out.println("Looking for customer with name " + name + " and REGON " + regon);
        showCustomers(req, resp);
    }

    public void addCustomer(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String name = req.getParameter("name");
        String REGON = req.getParameter("regon");
        boolean isSuccessful = customerListService.insertCustomer(name, REGON);

        if (isSuccessful)
            req.setAttribute("state", "success");
        else
            req.setAttribute("state", "failed");

        forwardToAddCustomer(req, resp);
    }

    private void showCustomers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Customer> customerList = customerListService.createRegonCustomerList();
        req.setAttribute("customerList", customerList);
        getServletContext().getRequestDispatcher("/jsp/Klienci/customers.jsp").forward(req, resp);
    }
}
