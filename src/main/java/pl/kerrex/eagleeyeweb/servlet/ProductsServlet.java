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
        ProductListService productListService = new ProductListService();
        List<Product> productList = productListService.createProductList();
        req.setAttribute("productList", productList);
        getServletContext().getRequestDispatcher("/jsp/Produkty/products.jsp").forward(req, resp);
    }
}
