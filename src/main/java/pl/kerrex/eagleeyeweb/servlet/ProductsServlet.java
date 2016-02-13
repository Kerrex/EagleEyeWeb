package pl.kerrex.eagleeyeweb.servlet;

import pl.kerrex.eagleeyeweb.database.ProductListService;
import pl.kerrex.eagleeyeweb.logic.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by tomek on 12.02.16.
 */
@WebServlet(
        name = "ProductsServlet",
        urlPatterns = {"/products"}
)
public class ProductsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if (action == null) {
            showProductList(req, resp);
        } else if (action.equals("addproduct")) {
            forwardToAddProduct(req, resp);
        } else if (action.equals("erase")) {
            eraseProduct(req, resp);
        } else if (action.equals("edit")) {
            forwardToEditProduct(req, resp);
        }

    }

    private void forwardToEditProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductListService productListService = new ProductListService();
        String ean = req.getParameter("ean");
        Product product = productListService.getProductByEan(ean);
        req.setAttribute("product", product);
        getServletContext().getRequestDispatcher("/jsp/Produkty/editproduct.jsp").forward(req, resp);
    }

    private void editProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductListService productListService = new ProductListService();
        String oldEan = req.getParameter("ean");
        String ean = req.getParameter("newean");
        String name = req.getParameter("name");
        boolean isSuccessful = productListService.updateProduct(oldEan, ean, name);

        if (isSuccessful)
            showProductList(req, resp);
        else {
            req.setAttribute("state", "failed");
            forwardToEditProduct(req, resp);
        }

    }

    private void eraseProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductListService productListService = new ProductListService();
        String ean = req.getParameter("ean");
        productListService.deleteProduct(ean);
        showProductList(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if (action.equals("addproduct")) {
            addProduct(req, resp);
        } else if (action.equals("editproduct")) {
            editProduct(req, resp);
        }
    }

    private void addProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductListService productListService = new ProductListService();
        String name = req.getParameter("name");
        String ean = req.getParameter("ean");
        boolean isSuccessful = productListService.addProduct(name, ean);

        if (isSuccessful)
            req.setAttribute("state", "success");
        else
            req.setAttribute("state", "failed");

        getServletContext().getRequestDispatcher("/jsp/Produkty/addproducts.jsp").forward(req, resp);
    }

    private void forwardToAddProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/jsp/Produkty/addproducts.jsp").forward(req, resp);
    }

    private void showProductList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductListService productListService = new ProductListService();
        List<Product> productList = productListService.createProductList();
        req.setAttribute("productList", productList);
        getServletContext().getRequestDispatcher("/jsp/Produkty/products.jsp").forward(req, resp);
    }
}
