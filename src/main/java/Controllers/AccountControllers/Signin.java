package Controllers.AccountControllers;

import Services.AccountService;
import Services.Exceptions.NoSavedInDbException;
import Services.Message;
import Tools.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by raxis on 27.12.2016.
 */
public class Signin extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(Signin.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/signin.jsp");
        rd.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        req.setAttribute("name",req.getParameter("name"));
        req.setAttribute("lastname",req.getParameter("lastname"));
        req.setAttribute("surname",req.getParameter("surname"));
        req.setAttribute("phone",req.getParameter("phone"));
        req.setAttribute("address",req.getParameter("address"));

        AccountService accountService = new AccountService();
        Message message = new Message();
        try {
            message =
            accountService.signIn(req.getParameter("email"),
                                  req.getParameter("password"),
                                  req.getParameter("name"),
                                  req.getParameter("lastname"),
                                  req.getParameter("surname"),
                                  req.getParameter("phone"),
                                  req.getParameter("address"));
        } catch (NoSavedInDbException e) {
            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/savetodberror.jsp");
            rd.forward(req,resp);
        }

        if(message.isOk()){
            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/signinsuccess.jsp");
            rd.forward(req,resp);
        }
        else {
            req.setAttribute("messages",message.getMessagesError());
            doGet(req,resp);
        }

    }
}
