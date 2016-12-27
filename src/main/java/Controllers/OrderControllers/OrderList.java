package Controllers.OrderControllers;

import Interfaces.*;
import Models.*;
import ViewModels.OrderView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * Created by raxis on 26.12.2016.
 */
public class OrderList extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(OrderList.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        HttpSession session=req.getSession();
        IRepoUser user = (User) session.getAttribute("user");

        if(user!=null){
            List<OrderView> orderViewList = new ArrayList<>();

            IRepoOrder order = new Order();
            List<IRepoOrder> orderList=order.selectByUserId(user.getId());

            for(IRepoOrder repoOrder : orderList){
                OrderView orderView = new OrderView();
                Map<String,Integer> map = new HashMap<>();
                IRepoGroceryList groceryList = new GroceryList();
                List<IRepoGroceryList> groceryLists = groceryList.select(repoOrder.getGrocerylistid());
                String orderStatus = new OrderStatus().selectOne(repoOrder.getOrderstatusid()).getStatus();

                orderView.setId(repoOrder.getId().toString());
                orderView.setFullName(String.format("%s %s %s",user.getLastName(),user.getName(),user.getSurName()));
                orderView.setStatus(orderStatus);
                orderView.setDate(repoOrder.getDatetime().toString());
                orderView.setAddress(repoOrder.getAddress());
                orderView.setPrice(repoOrder.getPrice().toString());
                for(IRepoGroceryList list:groceryLists){
                    String str=new Grocery().selectOne(list.getGroceryId()).getName();
                    map.put(str,Integer.valueOf(list.getQuantity()));
                }
                orderView.setGroceries(map);

                orderViewList.add(orderView);
            }

            req.setAttribute("orderlist",orderViewList);

            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/orderlist.jsp");
            rd.forward(req,resp);

        }
        else {
            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/deadend.jsp");
            rd.forward(req,resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        IRepoOrder order = new Order().selectOne(UUID.fromString(req.getParameter("orderid")));

        order.setOrderstatusid(UUID.fromString("1c8d12cf-6b0a-4168-ae2a-cb416cf30da5"));

        order.update();

        RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/grocerylist.jsp");
        rd.forward(req,resp);
    }
}
