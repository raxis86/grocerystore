package Controllers.GroceryControllers;

import Services.Abstract.IGroceryService;
import Services.Concrete.GroceryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by raxis on 24.12.2016.
 */
public class GroceryListController extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(GroceryListController.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        IGroceryService groceryService = new GroceryService();

        req.setAttribute("groceryList",groceryService.getGroceryList());


        RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/grocerylist.jsp");
        rd.forward(req,resp);
    }
}
