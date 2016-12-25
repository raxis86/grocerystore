package Controllers.AccountControllers;

import Interfaces.IRepo;
import Models.Role;
import Models.User;
import Tools.Tool;
import jdk.nashorn.internal.objects.annotations.Function;
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

        User user = new User().select(req.getParameter("name"),Tool.computeHash(req.getParameter("password")));


        if(user!=null){
            Role role = new Role().select(user.getRoleID());
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
        rd.forward(req,resp);
    }
}
