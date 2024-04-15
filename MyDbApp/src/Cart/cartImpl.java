package Cart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import Utils.commonUtil;
import Utils.cartUtil;
import Order.orderImpl;


public class cartImpl implements cart {
    static Scanner scanner = new Scanner(System.in);
    cartUtil cu = new cartUtil();
    orderImpl oi = new orderImpl();

    @Override
    public void cart(Scanner sc) {
        while (true) {
        	 System.out.println("--------------메뉴판--------------");
             System.out.println("1   (HOT) 아메리카노   1,800원");
             System.out.println("2   (ICE) 아메리카노   2,100원");
             System.out.println("3   (HOT) 헤이즐넛     2,300원");
             System.out.println("4   (ICE) 헤이즐넛     2,600원");
             System.out.println("5   (HOT) 바닐라라떼   3,300원");
             System.out.println("6   (ICE) 바닐라라떼   3,600원");
             System.out.println("7   (ICE) 고구마라떼   3,500원");
             System.out.println("8   (ICE) 뉴딸기라떼   3,700원");
             System.out.println("9   (ICE) 아인슈페너 초코  4,000원");
             System.out.println("10  (HOT) 녹차         2,000원");
             System.out.println("11  (ICE) 녹차         2,300원");
             System.out.println("12  (HOT) 캐모마일     2,000원");
             System.out.println("13  (ICE) 캐모마일     2,300원");
             System.out.println("-------------------------------");
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
        System.out.println("주문 가격 합계: " + cu.totalPrice + "원");
        System.out.println("");
    }

    @Override
    public void pay(Scanner sc) {
        try (Connection conn = commonUtil.getConnection()) {
            String selectSql = "SELECT ID FROM USERS";
            try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
                ResultSet rs = selectStmt.executeQuery();
                String currentId = null;
                if (rs.next()) {
                    currentId = rs.getString("ID");
                }

                if (currentId == null) {
                    throw new RuntimeException("현재 ID를 가져올 수 없습니다.");
                }

                // CART_SEQ.NEXTVAL을 사용하여 다음 시퀀스 값을 가져옴
                String insertSql = "INSERT INTO PAY (PNO, ID, SPRICE) VALUES (PAY_SEQ.NEXTVAL, ?, ?)";
                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                    insertStmt.setString(1, currentId);
                    insertStmt.setInt(2, cu.totalPrice);

                    int rowsInserted = insertStmt.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("결제가 완료되었습니다.");

                        // 결제 확인
                        System.out.println("총 주문 수량은 " + cu.totalQuan + "개, 금액은 " + cu.totalPrice + "원 입니다.");
                        System.out.println("스탬프 " + cu.totalQuan + "개 적립되었습니다.");

                        // 여기서 CART 테이블의 결제 번호를 업데이트하는 로직을 추가할 수 있습니다.
                        // UPDATE CART SET PNO = (SELECT MAX(PNO) FROM PAY) WHERE PNO IS NULL;

                        oi.order(sc); // 주문하기
                    } else {
                        System.out.println("결제에 실패했습니다.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
