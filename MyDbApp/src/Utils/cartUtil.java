package Utils;

import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Users.userImpl;

public class cartUtil {
	userUtil uu = new userUtil();
	userImpl ui = new userImpl();
	public int totalPrice = 0; // 총 가격
	public int totalQuan = 0; // 총 수량

	// 장바구니에 메뉴 추가
	public void addToCart(int menuNum, int quan) {
		 // totalPrice와 totalQuan 초기화
        totalPrice = 0;
        totalQuan = 0;
		try (Connection conn = commonUtil.getConnection()) {
			String insertSql = "INSERT INTO CART (CNO, MNO, ID, QUAN, ODATE) VALUES (CART_SEQ.NEXTVAL, ?, ?, ?, SYSDATE)";
			try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
				insertStmt.setInt(1, menuNum);
				insertStmt.setString(2, uu.getCurrentId());
				insertStmt.setInt(3, quan);
				insertStmt.executeUpdate();
			}

			// 메뉴 정보 가져오기
			String selectSql1 = "SELECT M.MNAME, M.MPRICE \n"
					+ "FROM CART C \n"
					+ "INNER JOIN MENU M ON M.MNO = C.MNO \n"
					+ "WHERE ID = ?\n"
					+ "ORDER BY C.CNO DESC\n"
					+ "FETCH FIRST 1 ROW ONLY";
			try (PreparedStatement selectStmt = conn.prepareStatement(selectSql1)) {
				selectStmt.setString(1, uu.getCurrentId());				
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

	// 결제 수단 
	public void payToStamp() {
	    try (Connection conn = commonUtil.getConnection()) {
	        String selectSql2 = "SELECT STAMP FROM USERS WHERE ID = ?";
	        try (PreparedStatement selectStmt = conn.prepareStatement(selectSql2)) {
	            selectStmt.setString(1, uu.getCurrentId());
	            ResultSet rs = selectStmt.executeQuery();
	            if (rs.next()) {
	                int stamp = rs.getInt("STAMP");
	                if (stamp < 10) {
	                    System.out.println("현재 스탬프 : " + stamp + "개");
	                    System.out.println("스탬프가 부족합니다.");
	                    return;
	                } else if (stamp >= 10) {
	                    System.out.println("현재 스탬프 : " + stamp + "개");
	                    updateStamp();
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	
	public int updateStamp() {
	    int updatedStamp = 0;
	    try (Connection conn = commonUtil.getConnection()) {
	        String updateSql = "UPDATE USERS\n"
	                + "SET STAMP = (STAMP - 10) \n"
	                + "WHERE ID = ?";
	        try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
	            updateStmt.setString(1, uu.getCurrentId());
	            int rowsAffected = updateStmt.executeUpdate();
	            if (rowsAffected > 0) {
	                updatedStamp = getStampById(uu.getCurrentId()); // 업데이트된 스탬프 값을 가져옴
	                System.out.println("결제 후 스탬프: " + updatedStamp + "개");
					System.out.println("        	");
	            } else {
	                System.out.println("스탬프 업데이트에 실패했습니다.");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return updatedStamp;
	}
	
	// 아이디에 해당하는 스탬프 값을 가져오는 메서드
	public int getStampById(String id) {
	    int stamp = 0;
	    try (Connection conn = commonUtil.getConnection()) {
	        String selectSql = "SELECT STAMP FROM USERS WHERE ID = ?";
	        try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
	            selectStmt.setString(1, id);
	            try (ResultSet rs = selectStmt.executeQuery()) {
	                if (rs.next()) {
	                    stamp = rs.getInt("STAMP");
	                    System.out.println("현재 적립 스탬프: " + stamp + "개");
						System.out.println(
								"============================================================================");
						System.out.println("        	");
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return stamp;
	}
}
