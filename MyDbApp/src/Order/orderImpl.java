package Order;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import Utils.commonUtil;
import Utils.userUtil;
import Users.userImpl;
import Utils.orderUtil;
import Utils.cartUtil;

public class orderImpl implements order {
	static Scanner scanner = new Scanner(System.in);
	private userUtil uu = new userUtil();
	userImpl ui = new userImpl();
	orderUtil ou = new orderUtil();
	cartUtil cu = new cartUtil();

	@Override
	public void pay(int menuNum, int quan) {
		try (Connection conn = commonUtil.getConnection()) {
			

			// CART 테이블 정보 조회 및 주문 처리
			String selectSql = "SELECT CNO, MNO, ID, QUAN, TO_CHAR(ODATE, 'YYYY-MM-DD HH24:MI') AS ODATE FROM CART WHERE ID = ?";
			try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
			    selectStmt.setString(1, uu.getCurrentId());
			    ResultSet rs = selectStmt.executeQuery();
			    while (rs.next()) {
			        int cno = rs.getInt("CNO");
			        int mno = rs.getInt("MNO");
			        String id = rs.getString("ID");
			        int quantity = rs.getInt("QUAN");
			        String orderDate = rs.getString("ODATE"); // 날짜 및 시간을 문자열로 가져옴

			        // 주문 처리
			        String insertSql = "INSERT INTO ORDERS (CNO, MNO, ID, QUAN, ODATE) VALUES (?, ?, ?, ?, TO_DATE(?, 'YYYY-MM-DD HH24:MI'))";
			        try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
			            insertStmt.setInt(1, cno);
			            insertStmt.setInt(2, mno);
			            insertStmt.setString(3, id);
			            insertStmt.setInt(4, quantity);
			            insertStmt.setString(5, orderDate); // 문자열로 된 날짜 및 시간을 설정

			            int rowsInserted = insertStmt.executeUpdate();
			            if (rowsInserted > 0) {
//			                System.out.println("주문이 성공적으로 처리되었습니다.");
			            } else {
			                System.out.println("주문 처리에 실패했습니다.");
			            }
			        }
			    }
			}



			// 주문 조회 쿼리
			String selectSqlOrder = "SELECT * FROM ORDERS o JOIN MENU m ON m.MNO = o.MNO JOIN USERS u ON u.ID = o.ID WHERE u.ID = ?";
			// 주문 내역 조회 및 출력
			try (PreparedStatement selectStmtOrder = conn.prepareStatement(selectSqlOrder)){
			    selectStmtOrder.setString(1, uu.getCurrentId());
			    ResultSet rs2 = selectStmtOrder.executeQuery();
			    System.out.println("============================================================================");
			    System.out.println(uu.getCurrentName() + "님의 주문내역 ");
			    System.out.printf("%-30s%-20s%-5s%-10s\n", "주문날짜", "메뉴명", "수량", "결제금액");
			    System.out.println("----------------------------------------------------------------------------");
			    while (rs2.next()) {
			        int price = rs2.getInt("MPRICE");
			        String menuName = rs2.getString("MNAME");
			        int orderQuan = rs2.getInt("QUAN");
			        String orderDate = rs2.getString("ODATE"); // ODATE의 문자열 형식으로 변환된 값 가져오기
			        System.out.printf("%-30s%-20s%-5s%-10s\n", orderDate, menuName, orderQuan, price * orderQuan);
			        
			    }
			    System.out.println("----------------------------------------------------------------------------");
			    //System.out.println("============================================================================");
			    System.out.println("        	");
			}
		

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}