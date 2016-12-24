package Tools;

import Models.Grocery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

/**
 * Created by raxis on 23.12.2016.
 */
public class Tool {
    private static final Logger logger = LoggerFactory.getLogger(Tool.class);

    private static volatile Connection connection;


    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.error("Can't load JDBC driver!",e);
            e.printStackTrace();
        }
    }

    public static synchronized Connection getConnection(){
        if(connection==null){
            Properties properties = new Properties();
            properties.setProperty("characterEncoding","utf-8");
            properties.setProperty("user","root");
            properties.setProperty("password","yamaha");
            properties.setProperty("verifyServerCertificate","false");
            properties.setProperty("useSSL","false");
            try {
                connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/groceriesstore",properties);
            } catch (SQLException e) {
                logger.error("Cant get connection",e);
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error("Cant close connection",e);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Grocery grocery = new Grocery();
        List<Grocery> groceryList = grocery.select();

        for(Grocery g:groceryList){
            System.out.println(g.getId()+" " + g.getParentid() + " " + g.isIscategory() + " " + g.getName() + " " + g.getQuantity() + " " + g.getPrice());
        }

        /*grocery.setId(UUID.randomUUID());
        grocery.setParentid(new UUID(0L,0L));
        grocery.setIscategory(false);
        grocery.setQuantity(100);
        grocery.setName("banana");
        grocery.setPrice(BigDecimal.valueOf(10.25));
        grocery.insert();*/

        /*Grocery grocery1 = groceryList.get(0);
        grocery1.setPrice(BigDecimal.valueOf(89.99));
        grocery1.update();*/

        /*groceryList.get(2).delete();*/

    }

}
