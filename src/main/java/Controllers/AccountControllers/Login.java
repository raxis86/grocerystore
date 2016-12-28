package Controllers.AccountControllers;

import Services.AccountService;
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

/**
 * Created by raxis on 25.12.2016.
 */
public class Login extends HttpServlet{
    private static final Logger logger = LoggerFactory.getLogger(Login.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/login.jsp");
        rd.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        AccountService accountService = new AccountService();

        Message message = accountService.userLogin(req.getParameter("email"),req.getParameter("password"),req);
        if(message.isOk()){
            RequestDispatcher rd=req.getRequestDispatcher("/index.jsp");
            rd.forward(req,resp);
        }
        else {
            req.setAttribute("messages",message.getMessagesError());
            doGet(req,resp);
        }

    }
}
