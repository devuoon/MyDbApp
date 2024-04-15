package Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import Utils.commonUtil;
import Utils.userUtil;
import Users.userImpl;
import Utils.orderUtil;

public class orderImpl implements order {
	private userUtil uu = new userUtil();
	userImpl ui = new userImpl();
	orderUtil ou = new orderUtil();

	@Override
	public void order(Scanner sc) {
		try (Connection conn = commonUtil.getConnection()) {
			String selectSqlNO = "SELECT MAX(PNO) AS PNO FROM PAY WHERE ID = ? GROUP BY ID, NO";
			try (PreparedStatement selectStmtNo = conn.prepareStatement(selectSqlNO)) {
				selectStmtNo.setString(1, uu.getCurrentId());
				try (ResultSet rsNo = selectStmtNo.executeQuery()) {
					int SPNO = 0;
					if (rsNo.next()) {
						SPNO = rsNo.getInt("PNO");
					}
//                System.out.println(uu.getCurrentId());

					String insertSql = "INSERT INTO ORDERS (ONO, ODATE, ID, OCHECK, PNO) VALUES (ORDER_SEQ.NEXTVAL, ?, ?, NULL, ?)";
					try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
						// 현재 날짜와 시간을 가져오는 코드
						LocalDateTime now = LocalDateTime.now();
						// LocalDateTime 객체를 java.sql.Timestamp로 변환
						Timestamp timestamp = Timestamp.valueOf(now);

						// 매개변수 설정
						insertStmt.setTimestamp(1, timestamp);
						insertStmt.setString(2, uu.getCurrentId());
						insertStmt.setInt(3, SPNO);

						int rowInserted = insertStmt.executeUpdate();
						if (rowInserted > 0) {
							// 정상적으로 삽입되었을 때의 처리
						}
					}
				}

				String selectSqlName = "SELECT U.NAME, M.MNAME, M.MPRICE, c.QUAN, c.CNO, O.ODATE  \n"
						+ "FROM ORDERS O \n" + "	INNER JOIN PAY P ON O.PNO = P.PNO\n"
						+ "	INNER JOIN CART C ON P.NO = C.NO\n" + "	INNER JOIN MENU M ON M.MNO = C.MNO \n"
						+ " 	INNER JOIN USERS U ON C.ID = U.ID\n" + "WHERE U.ID = ?"
						+ "GROUP BY U.NAME, M.MNAME, M.MPRICE, c.QUAN, c.CNO, O.ODATE";
				try (PreparedStatement selectStmtName = conn.prepareStatement(selectSqlName)) {
					selectStmtName.setString(1, uu.getCurrentId());
					try (ResultSet rsName = selectStmtName.executeQuery()) {
						System.out.println("===========================================================");
						System.out.println(uu.getCurrentId() + "님의 주문내역:");
						System.out.printf("%-20s%-20s%-10s%-10s\n", "날짜", "메뉴명", "수량", "결제금액");
						while (rsName.next()) {
							Timestamp timestamp = rsName.getTimestamp("ODATE");
							LocalDateTime localDateTime = timestamp.toLocalDateTime();
							DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
							String formattedDateTime = localDateTime.format(formatter);
							String menuName = rsName.getString("MNAME");
							int price = rsName.getInt("MPRICE");
							int quantity = rsName.getInt("QUAN");
							System.out.printf("%-20s%-20s%-10d%-10d\n", formattedDateTime, menuName, quantity,
									price * quantity);
						}
						System.out.println("===========================================================");
						ui.stamp(sc);
						System.out.println("        	");
					}
				}

				ou.removeToCart();
			}
		} catch (SQLException e) {
			System.out.println("주문 처리 중 오류가 발생했습니다.");
			e.printStackTrace();
		}
	}
}
