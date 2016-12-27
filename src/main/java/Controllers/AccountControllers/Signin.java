package Controllers.AccountControllers;

import Interfaces.IRepoUser;
import Models.Role;
import Models.User;
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

        /*String email=req.getParameter("email").toLowerCase();
        if(email.matches("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$")){
            IRepoUser user = new User();
            user.setId(UUID.randomUUID());
            user.setEmail(email);
            user.setPassword(Tool.computeHash(req.getParameter("password")));
            user.setName(req.getParameter("name"));
            user.setLastName(req.getParameter("lastname"));
            user.setSurName(req.getParameter("surname"));
            user.setPhone(req.getParameter("phone"));
            user.setAddress(req.getParameter("address"));

            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/signinsuccess.jsp");
            rd.forward(req,resp);
        }
        else {
            req.setAttribute("message","email некорректен!");
            doGet(req,resp);
        }*/
        /*User user = new User().getOne(req.getParameter("email"), Tool.computeHash(req.getParameter("password")));


        if(user!=null){
            Role role = new Role().getOne(user.getRoleID());
            HttpSession session = req.getSession(true);
            session.setAttribute("user",user);
            session.setAttribute("role",role);
            String str;
            str=req.getPathInfo();
            str=req.getContextPath();
            str=req.getPathTranslated();
            str=req.getServletPath();
        }
        RequestDispatcher rd=req.getRequestDispatcher("/index.jsp");
        rd.forward(req,resp);*/
    }
}
