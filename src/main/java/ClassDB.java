import com.microsoft.sqlserver.jdbc.SQLServerPreparedStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

//public class ClassDB {
//    public static void main(String[] args) throws SQLException {
//       String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;database=StoreBikes";
//        String username = "sa1";
//        String password = "Daciada01011";
//        try {
//            Connection connection = DriverManager.getConnection(url, username, password);
//            System.out.println("Connect to Sql server");
//        } catch (SQLException e) {
//            System.out.println("Oop, there's an error :");
//            e.printStackTrace();
//        }
//    }
//}

public class ClassDB {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:sqlserver://localhost\\sqlexpress;user=sa1;password=Daciada01011;databaseName=StoreBikes";

        Connection connection = DriverManager.getConnection(url);
        Statement statement = connection.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

//        ResultSet result =  statement.executeQuery("select top 10 * from [StoreBikes].[sales].[customers]");
//        ResultSetMetaData metadata = result.getMetaData();
//        result.absolute(10);
//        for(int i=1;i<=metadata.getColumnCount();i++) {
//            System.out.println(result.getString(i));
//        }

        ResultSet results =  statement.executeQuery("select top 10 * from [StoreBikes].[sales].[customers]");
        boolean allData = results.next();
        results.getMetaData();

        System.out.println(allData);
    }
}