import java.util.Scanner;

import Users.join;
import Users.login;
import Order.order;
import Users.mypage;

public class Main {
	static Scanner scanner = new Scanner(System.in);
	private static join jn = new join();
	private static login ln = new login();
	private static order od = new order();
	private static mypage mp = new mypage();
	
	public static void main(String[] args) {
		System.out.println("===========================================");
		System.out.println("==========어서오세요 BlueBottle입니다.=========");
		System.out.println("1. 회원가입");
		System.out.println("2. 로그인");
		System.out.println("3. 주문하기");
		System.out.println("4. 마이페이지");
		System.out.println("5. 종료");
		System.out.println("==========실행할 메뉴의 번호를 입력해주세요.=========");
		System.out.println("=============================================");
		
		int num = scanner.nextInt();
		scanner.nextLine();
		
		switch (num) {
		case 1:
			jn.run(scanner);
		case 2:
			ln.run(scanner);
		case 3:
			od.run(scanner);
		case 4:
			mp.run(scanner);
		case 5:
			System.out.println("감사합니다. 또 방문해주세요.");
			return;
		default:
			System.out.println("번호를 다시 입력해주세요.");
		}
	}
}
