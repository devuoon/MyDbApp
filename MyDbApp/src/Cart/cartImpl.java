package Cart;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import Utils.commonUtil;
import Utils.cartUtil;
import Order.orderImpl;
import Utils.userUtil;

public class cartImpl implements cart {
	static Scanner scanner = new Scanner(System.in);
	cartUtil cu = new cartUtil();
	orderImpl oi = new orderImpl();
	userUtil uu = new userUtil();

	@Override
	public void cart(Scanner sc) {
		System.out.println("====================== 메뉴판 ======================");
		System.out.printf("%-10s%-30s%-10s\n", "No", "Menu", "  Price");
		System.out.printf("%-10s%-30s%-10s\n", "1", "(HOT) 아메리카노", "1,800원");
		System.out.printf("%-10s%-30s%-10s\n", "2", "(ICE) 아메리카노", "2,100원");
		System.out.printf("%-10s%-30s%-10s\n", "3", "(HOT) 헤이즐넛  ", " 2,300원");
		System.out.printf("%-10s%-30s%-10s\n", "4", "(ICE) 헤이즐넛  ", " 2,600원");
		System.out.printf("%-10s%-30s%-10s\n", "5", "(HOT) 바닐라라떼", "3,300원");
		System.out.printf("%-10s%-30s%-10s\n", "6", "(ICE) 바닐라라떼", "3,600원");
		System.out.printf("%-10s%-30s%-10s\n", "7", "(ICE) 고구마라떼", "3,500원");
		System.out.printf("%-10s%-30s%-10s\n", "8", "(ICE) 뉴딸기라떼", "3,700원");
		System.out.printf("%-10s%-30s%-10s\n", "9", "(ICE) 아인슈페너", "4,000원");
		System.out.printf("%-10s%-30s%-10s\n", "10", "(HOT) 녹차", "  2,000원");
		System.out.printf("%-10s%-30s%-10s\n", "11", "(ICE) 녹차", "  2,300원");
		System.out.printf("%-10s%-30s%-10s\n", "12", "(HOT) 캐모마일 ", " 2,000원");
		System.out.printf("%-10s%-30s%-10s\n", "13", "(ICE) 캐모마일 ", " 2,300원");
		System.out.println("==================================================");
		while (true) {
			System.out.print("주문하실 메뉴의 번호를 입력해주세요. (주문 종료: 0) >> ");
			int menuNum = scanner.nextInt();
			scanner.nextLine();

			if (menuNum == 0) {
				break;
			}

			// 수량 입력
			System.out.print("수량을 입력해주세요. >> ");
			int quan = scanner.nextInt();
			scanner.nextLine();

			// 장바구니에 추가
			cu.addToCart(menuNum, quan);
		}

		// 주문 가격 합계 출력
//		System.out.println("주문 가격 합계: " + cu.totalPrice + "원");
//		System.out.println("");
	}

	@Override
	public void pay(Scanner sc) {
		try (Connection conn = commonUtil.getConnection()) {
			// CART_SEQ.NEXTVAL을 사용하여 다음 시퀀스 값을 가져옴
			String insertSql = "INSERT INTO PAY (PNO, ID, SPRICE) VALUES (PAY_SEQ.NEXTVAL, ?, ?)";
			try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
				insertStmt.setString(1, uu.getCurrentId());
				insertStmt.setInt(2, cu.totalPrice);
//				System.out.println(uu.getCurrentId());

				int rowsInserted = insertStmt.executeUpdate();
				if (rowsInserted > 0) {
					System.out.println("결제가 완료되었습니다.");

					// 결제 확인
					System.out.println("총 주문 수량은 " + cu.totalQuan + "개, 금액은 " + cu.totalPrice + "원 입니다.");
					System.out.println("        	");
					oi.order(sc); // 주문하기
				} else {
					System.out.println("결제에 실패했습니다.");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
