package pl.kerrex.eagleeyeweb.servlet;

import org.apache.commons.lang3.time.DateUtils;
import pl.kerrex.eagleeyeweb.database.BoughtProductListService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by tomek on 15.02.16.
 */
@WebServlet(
        name = "DashboardServlet",
        urlPatterns = {"/dashboard"}
)
public class DashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BoughtProductListService boughtProductListService = new BoughtProductListService();

        int lastWeek = boughtProductListService.countProducts(DateUtils.truncate(Calendar.getInstance().getTime(), Calendar.DAY_OF_MONTH),
                DateUtils.truncate(DateUtils.addDays(Calendar.getInstance().getTime(), -7), Calendar.DAY_OF_MONTH));
        int lastMonth = boughtProductListService.countProducts(DateUtils.truncate(Calendar.getInstance().getTime(), Calendar.DAY_OF_MONTH),
                DateUtils.truncate(DateUtils.addDays(Calendar.getInstance().getTime(), -30), Calendar.DAY_OF_MONTH));
        int lastYear = boughtProductListService.countProducts(DateUtils.truncate(Calendar.getInstance().getTime(), Calendar.DAY_OF_MONTH),
                DateUtils.truncate(DateUtils.addDays(Calendar.getInstance().getTime(), -365), Calendar.DAY_OF_MONTH));
        req.setAttribute("lastWeek", lastWeek);
        req.setAttribute("lastMonth", lastMonth);
        req.setAttribute("lastYear", lastYear);
        getServletContext().getRequestDispatcher("/jsp/dashboard.jsp").forward(req, resp);
    }
}
