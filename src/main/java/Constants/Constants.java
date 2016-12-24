package Constants;

import java.util.UUID;

/**
 * Created by raxis on 23.12.2016.
 */
public class Constants {
    public static final UUID NULL_UUID=UUID.fromString("00000000-0000-0000-0000-000000000000");
    public static final String GROCERY_SELECTALL_QUERY="SELECT * FROM GROCERIES";
    public static final String GROCERY_PREP_INSERT_QUERY="INSERT INTO groceries values (?,?,?,?,?,?)";
    public static final String GROCERY_PREP_UPDATE_QUERY="UPDATE groceries SET PARENTID=?,ISCATEGORY=?,NAME=?,QUANTITY=?,PRICE=? WHERE ID=?";
    public static final String GROCERY_PREP_DELETE_QUERY="DELETE FROM groceries WHERE ID=?";
}
