package windows.manager;

import driver.Engine;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by mirnafayez on 04/05/17.
 */
public class ReportController {

    private String totalBooksSales="SELECT SUM(Num_books) AS S FROM SHOPPING_CART WHERE Confirmed=1 AND  Con_date >= date_sub(NOW(), INTERVAL 1 MONTH)";
    private String salesForBooksSQL="SELECT B.ISBN,Title,SUM(Num_books)AS S FROM BOOK AS B,SHOPPING_CART AS SC WHERE B.ISBN= SC.ISBN AND Confirmed=1 AND  Con_date >= date_sub(NOW(), INTERVAL 1 MONTH)GROUP BY B.ISBN ";

    private String topPurchasingUsersSQL="SELECT User_name, SUM(BOOK_price) AS total FROM SHOPPING_CART WHERE Confirmed=1 AND  Con_date >= date_sub(NOW(), INTERVAL 3 MONTH) GROUP BY User_name ORDER BY total DESC";
    private String topSellingBooksSQL = "SELECT B.ISBN ,Title ,SUM(Num_books) AS S FROM SHOPPING_CART AS SC , BOOK AS B WHERE B.ISBN= SC.ISBN AND Confirmed=1 AND  Con_date >= date_sub(NOW(), INTERVAL 3 MONTH)  GROUP BY ISBN ORDER  BY S DESC ";



    protected String getTotalBooksSales()throws SQLException{
        ResultSet rs =Engine.STATEMENT.executeQuery(totalBooksSales);
        rs.next();
        return rs.getString("S");
    }

    protected ResultSet getSalesForBooks()throws SQLException{
        return Engine.STATEMENT.executeQuery(salesForBooksSQL);
    }

    protected ResultSet getTopPurchasingUsers()throws SQLException{
        return Engine.STATEMENT.executeQuery(topPurchasingUsersSQL);
    }

    protected ResultSet getTopSellingBooks()throws SQLException{
            return Engine.STATEMENT.executeQuery(topSellingBooksSQL);
    }

}
