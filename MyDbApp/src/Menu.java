import java.util.Scanner;

import Cart.cartImpl;
import Users.userImpl;
import Utils.userUtil;
import Order.orderImpl;

public class Menu {
    static Scanner scanner = new Scanner(System.in);
    private static userImpl ui = new userImpl();
    private static cartImpl ci = new cartImpl();
    private static orderImpl oi = new orderImpl();
    static userUtil util = new userUtil();
    static boolean loggedIn = false;

    public static void menu() {
    	
        boolean running = true;
        while (running) {
        	if(!util.isLoggedIn()) {
        		System.out.println("        	");
        		System.out.println("===================================== 어서오세요. Bluebottle 입니다. =====================================");
            	System.out.println("        	");
            	System.out.println("       			┌───────────────┐  ┌───────────────┐                    ");
            	System.out.println("        		│   1. 회원가입   	│  │   2. 로그인	   │                  ");
            	System.out.println("        		└───────────────┘  └───────────────┘            ");
        		System.out.println("        		┌───────────────┐  ┌───────────────┐  ┌───────────────┐               ");
        		System.out.println("        		│   3. 주문하기   	│  │   4. 주문내역	   │  │  5. 종료 	      │	               ");
        		System.out.println("        		└───────────────┘  └───────────────┘  └───────────────┘               ");
        		System.out.println("        	");
        		System.out.println("========================================== 메뉴를 선택하세요. ============================================");
        	} else if (util.isLoggedIn()) {
        		System.out.println("===================================== 어서오세요. Bluebottle 입니다. =====================================");
            	System.out.println("        	");
            	System.out.println("       			┌───────────────┐  ┌───────────────┐                    ");
            	System.out.println("        		│   1. 회원가입   	│  │   2. 로그아웃	   │                  ");
            	System.out.println("        		└───────────────┘  └───────────────┘            ");
        		System.out.println("        		┌───────────────┐  ┌───────────────┐  ┌───────────────┐               ");
        		System.out.println("        		│   3. 주문하기   	│  │   4. 주문내역	   │  │  5. 종료 	      │	               ");
        		System.out.println("        		└───────────────┘  └───────────────┘  └───────────────┘               ");
        		System.out.println("        	");
        		System.out.println("========================================== 메뉴를 선택하세요. ============================================");
        	}
        	
    		System.out.println("        	");
    		System.out.print(">>  ");
            
            int num = scanner.nextInt();
            scanner.nextLine();
            System.out.println("        	");
            
            
            switch (num) {
                case 1:
                	
                    if (!util.isLoggedIn()) {
                        ui.signUp(scanner);
                    } else {
                        System.out.println("이미 로그인 상태입니다. 메뉴를 재선택해주세요.");
                    }
                    break;
                case 2:
                    if (!util.isLoggedIn()) {
                        ui.signIn(scanner);
                    } else if(util.isLoggedIn()) {
                    	util.logout();
                        System.out.println("로그아웃 되었습니다.");
                    }
                    break;
                case 3:
                    if (util.isLoggedIn()) {
                        ci.cart(scanner);
                    } else {
                        System.out.println("로그인이 필요한 기능입니다. 로그인 후 다시 시도해주세요.");
                    }
                    break;
                case 4:
                	if (util.isLoggedIn()) {
                		oi.pay(num, num);
                    } else {
                        System.out.println("로그인이 필요한 기능입니다. 로그인 후 다시 시도해주세요.");
                    }
                    break;
                case 5:
                	System.out.println("감사합니다. 또 방문해주세요.");
                    running = false; // 종료합니다.
                    break;
                default:
                    System.out.println("번호를 다시 입력해주세요.");
            }
        }
    }
}