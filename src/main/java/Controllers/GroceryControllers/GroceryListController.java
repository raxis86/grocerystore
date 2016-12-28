package Controllers.GroceryControllers;

import Models.Grocery;
import Services.GroceryService;
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

        GroceryService groceryService = new GroceryService();

        /*List<Grocery> groceryList = new Grocery().select();*/

        req.setAttribute("groceryList",groceryService.getGroceryList());

        //List<Grocery> groceryList = new Grocery().select();


        //HttpSession session = req.getSession();

        //session.setAttribute("grocerylist",groceryList);


        //req.setAttribute("groceryList",groceryList);


        RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/grocerylist.jsp");
        rd.forward(req,resp);


        //resp.sendRedirect("grocerylist.jsp");
    }
}