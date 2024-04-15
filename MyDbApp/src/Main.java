import java.util.Scanner;


import Cart.cartImpl;
import Users.userImpl;
import Utils.userUtil;
import Order.orderImpl;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    private static userImpl ur = new userImpl();
    private static cartImpl or = new cartImpl();
    private static orderImpl oi = new orderImpl();
    static userUtil util = new userUtil();
    static boolean loggedIn = false;

    public static void main(String[] args) {
        boolean running = true;
        
        while (running) {
        	System.out.println("===================================== 어서오세요. Bluebottle 입니다. =====================================");
        	System.out.println("        	");
        	System.out.println("       			┌───────────────┐  ┌───────────────┐  ┌───────────────┐               ");
        	System.out.println("        		│   1. 회원가입   	│  │   2. 로그인	   │  │   3. 주문하기	  │	          ");
        	System.out.println("        		└───────────────┘  └───────────────┘  └───────────────┘               ");
    		System.out.println("        		┌───────────────┐  ┌───────────────┐  ┌───────────────┐               ");
    		System.out.println("        		│   4. 주문내역   	│  │  5. 마이페이지	  │   6. 종료 	  │	               ");
    		System.out.println("        		└───────────────┘  └───────────────┘  └───────────────┘               ");
    		System.out.println("        	");
    		System.out.println("========================================== 메뉴를 선택하세요. ============================================");
    		System.out.println("        	");
    		System.out.print(">>  ");
            System.out.println("===========================================");
            System.out.println("==========어서오세요 BlueBottle입니다.=========");
            System.out.println("1. 회원가입");
            System.out.println(loggedIn);
            if(!loggedIn) {
            	System.out.println("2. 로그인");
            } else if(loggedIn) {
            	System.out.println("2. 로그아웃");
            }
            System.out.println("3. 주문하기");
            System.out.println("4. 주문내역");
            System.out.println("5. 마이페이지");
            System.out.println("6. 종료");
            System.out.println("==========실행할 메뉴의 번호를 입력해주세요.=========");
            System.out.println("=============================================");
            System.out.print(">> ");
            
            int num = scanner.nextInt();
            scanner.nextLine();
            
            switch (num) {
                case 1:
                    if (!loggedIn) {
                        ur.signUp(scanner);
                    } else {
                        System.out.println("이미 로그인 상태입니다. 메뉴를 재선택해주세요.");
                    }
                    break;
                case 2:
                    if (!loggedIn) {
                        ur.signIn(scanner);
                        loggedIn = true;
                        System.out.println(util.getCurrentId());
                    } else if(loggedIn) {
                    	util.logout();
                    	loggedIn = false;
                        System.out.println("로그아웃 되었습니다.");
                    }
                    break;
                case 3:
                    if (loggedIn) {
                        or.cart(scanner);
                        or.pay(scanner);
                    } else {
                        System.out.println("로그인이 필요한 기능입니다. 로그인 후 다시 시도해주세요.");
                    }
                    break;
                case 4:
                	if (loggedIn) {
                		oi.order(scanner);
                    } else {
                        System.out.println("로그인이 필요한 기능입니다. 로그인 후 다시 시도해주세요.");
                    }
                    break;
                case 5:
                	if (loggedIn) {
                		
                    } else {
                        System.out.println("로그인이 필요한 기능입니다. 로그인 후 다시 시도해주세요.");
                    }
                    break;
                case 6:
                	System.out.println("감사합니다. 또 방문해주세요.");
                    running = false; // 종료합니다.
                    break;
                default:
                    System.out.println("번호를 다시 입력해주세요.");
            }
        }
    }
}
