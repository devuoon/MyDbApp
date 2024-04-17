package Cart;

import java.util.Scanner;
import Utils.cartUtil;
import Utils.orderUtil;
import Order.orderImpl;
import Users.userImpl;
import Utils.userUtil;

public class cartImpl implements cart {
	static Scanner scanner = new Scanner(System.in);
	cartUtil cu = new cartUtil();
	orderImpl oi = new orderImpl();
	userUtil uu = new userUtil();
	userImpl ui = new userImpl();
	orderUtil ou = new orderUtil();

	@Override
	public void cart(Scanner sc) {
		System.out.println("====================== 메뉴판 ======================");
		System.out.printf("%-10s%-30s%-10s\n", "No", "Menu", "  Price");
		System.out.println("--------------------------------------------------");
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
		// 결제 수단 입력
		System.out.println("        	");
		System.out.println("결제 수단을 선택해주세요. (스탬프 10개 이상 음료 1잔 무료)");
		System.out.println("1. 카드   2. 현금   3. 스탬프");
		System.out.print(">> ");
		int pay = scanner.nextInt();
		scanner.nextLine();
		if (pay == 1) {
			System.out.println("카드로 결제되었습니다.");
			// 주문 가격 합계 출력
			System.out.println("주문 수량 : " + cu.totalQuan + "개 / 가격 합계 : " + cu.totalPrice + "원");
			System.out.println("");
			oi.pay(pay, pay);
			ui.stamp(sc);
			// 주문이 성공적으로 처리되면 장바구니에서 해당 상품을 삭제
			ou.removeToCart();
		} else if (pay == 2) {
			System.out.println("현금으로 결제되었습니다.");
			// 주문 가격 합계 출력
			System.out.println("주문 수량 : " + cu.totalQuan + "개 / 가격 합계 : " + cu.totalPrice + "원");
			System.out.println("");
			oi.pay(pay, pay);
			ui.stamp(sc);
			// 주문이 성공적으로 처리되면 장바구니에서 해당 상품을 삭제
			ou.removeToCart();
		} else if (pay == 3) {
			cu.payToStamp();
			// 주문 가격 합계 출력
			System.out.println("주문 수량 : " + cu.totalQuan + "개 / 가격 합계 : 0원");
			System.out.println("");
			oi.pay(pay, pay);
			cu.getStampById(null);
			// 주문이 성공적으로 처리되면 장바구니에서 해당 상품을 삭제
			ou.removeToCart();
		}

		
	}

}
