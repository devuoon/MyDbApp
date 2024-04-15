package Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class orderUtil {
	
	// 주문 후 장바구니 비우기
	public void removeToCart() {
	    try (Connection conn = commonUtil.getConnection()) {
	        String deleteSql = "DELETE FROM CART";
	        try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
	            int rowsDeleted = deleteStmt.executeUpdate();
	            if (rowsDeleted > 0) {
	                System.out.println("장바구니를 비웠습니다.");
	            } else {
	                System.out.println("장바구니가 이미 비어 있습니다.");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	// 오늘 날짜 
	public String today() {
	    Date today = new Date();
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    return dateFormat.format(today);
	}


}
