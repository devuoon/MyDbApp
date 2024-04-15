package Utils;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class cartUtil {
	userUtil uu = new userUtil();
	public int totalPrice = 0; // 총 가격
	public int totalQuan = 0; // 총 수량

	// 장바구니에 메뉴 추가
	public void addToCart(int menuNum, int quan) {
		try (Connection conn = commonUtil.getConnection()) {
			String insertSql = "INSERT INTO CART (CNO, MNO, ID, QUAN) VALUES (CART_SEQ.NEXTVAL, ?, ?, ?)";
			try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
				insertStmt.setInt(1, menuNum);
				insertStmt.setString(2, uu.getCurrentId());
				insertStmt.setInt(3, quan);
				insertStmt.executeUpdate();
			}

			// 메뉴 정보 가져오기
			String selectSql1 = "SELECT M.MNAME, M.MPRICE FROM CART C INNER JOIN MENU M ON M.MNO = C.MNO ORDER BY C.CNO DESC FETCH FIRST 1 ROW ONLY";
			try (PreparedStatement selectStmt = conn.prepareStatement(selectSql1)) {
				ResultSet rs = selectStmt.executeQuery();
				if (rs.next()) {
					String mName = rs.getString("MNAME");
					int mPrice = rs.getInt("MPRICE");
					totalPrice += (mPrice * quan); // 수량을 곱하여 총 가격에 추가
					totalQuan += quan;
					System.out.printf("%s %d잔 장바구니 추가\n", mName, quan); // 메뉴와 갯수 출력
					System.out.println("        	");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
