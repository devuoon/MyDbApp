package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class commonUtil {
	// DB 연결 
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String user = "adam";
        String pw = "1234";
        return DriverManager.getConnection(url, user, pw);
    }
}
