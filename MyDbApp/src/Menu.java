import java.util.Scanner;

import Users.userImpl;

public class Menu {
	static Scanner scanner = new Scanner(System.in);
    private static userImpl ur = new userImpl(); // user 인터페이스를 구현한 클래스의 객체를 생성하여 대입합니다.
    
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
        System.out.print(">> ");
        
        int num = scanner.nextInt();
        scanner.nextLine();
        
        switch (num) {
            case 1:
                ur.signUp(scanner); // user 인터페이스를 구현한 클래스의 signUp 메서드를 호출
                break;
            case 2:
            	ur.signIn(scanner);
                break;
            case 3:
                // 다른 메뉴의 처리 코드
                break;
            case 4:
                // 다른 메뉴의 처리 코드
                break;
            case 5:
                System.out.println("감사합니다. 또 방문해주세요.");
                return;
            default:
                System.out.println("번호를 다시 입력해주세요.");
        }
    }
}
