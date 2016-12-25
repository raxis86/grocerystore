package Constants;

import java.util.UUID;

/**
 * Created by raxis on 23.12.2016.
 */
public class Constants {
    public static final UUID NULL_UUID=UUID.fromString("00000000-0000-0000-0000-000000000000");
    public static final String GROCERY_SELECTALL_QUERY="SELECT * FROM GROCERIES";
    public static final String GROCERY_PREP_SELECTONE_QUERY="SELECT * FROM GROCERIES WHERE ID=?";
    public static final String GROCERY_PREP_INSERT_QUERY="INSERT INTO groceries values (?,?,?,?,?,?)";
    public static final String GROCERY_PREP_UPDATE_QUERY="UPDATE groceries SET PARENTID=?,ISCATEGORY=?,NAME=?,QUANTITY=?,PRICE=? WHERE ID=?";
    public static final String GROCERY_PREP_DELETE_QUERY="DELETE FROM groceries WHERE ID=?";
    public static final String USER_SELECTALL_QUERY="SELECT * FROM users";
    public static final String USER_PREP_SELECTONE_QUERY="SELECT * FROM users WHERE ID=?";
    public static final String USER_PREP_SELECTONE_BY_AUTH_QUERY="SELECT * FROM users WHERE NAME=? AND PASSWORD=?";
    public static final String USER_PREP_INSERT_QUERY="INSERT INTO users values (?,?,?,?,?)";
    public static final String USER_PREP_UPDATE_QUERY="UPDATE users SET ROLEID=?,NAME=?,EMAIL=?,PASSWORD=? WHERE ID=?";
    public static final String USER_PREP_DELETE_QUERY="DELETE FROM users WHERE ID=?";
    public static final String ROLE_SELECTALL_QUERY="SELECT * FROM roles";
    public static final String ROLE_PREP_SELECTONE_QUERY="SELECT * FROM roles WHERE ID=?";
    public static final String ROLE_PREP_INSERT_QUERY="INSERT INTO roles values (?,?)";
    public static final String ROLE_PREP_UPDATE_QUERY="UPDATE roles SET NAME=? WHERE ID=?";
    public static final String ROLE_PREP_DELETE_QUERY="DELETE FROM roles WHERE ID=?";
}
