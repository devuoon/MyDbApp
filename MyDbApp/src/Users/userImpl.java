package Users;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import Utils.commonUtil;
import Utils.userUtil;

public class userImpl implements user {
    static Scanner scanner = new Scanner(System.in);
    static boolean loggedIn = false; // 로그인 상태를 저장하는 변수
    userUtil util = new userUtil();

    // 회원가입
    @Override
    public void signUp(Scanner sc) {
    	System.out.println("=========================================== 홈 > 회원가입 ==============================================");
    	while(true) {
    		System.out.print("아이디 >> ");
            String id = sc.next();

            // 아이디 중복 검사
            if (util.IdExistCheck(id)) {
                System.out.println("이미 존재하는 아이디입니다. 다른 아이디를 사용해주세요.");
                continue;
            } else if(!util.IdExistCheck(id)) {
            	System.out.println("사용가능한 아이디입니다.");
            }

            System.out.print("비밀번호 >> " );
            String password = sc.next();
            System.out.print("이름 >> ");
            String name = sc.next();
            System.out.print("이메일 >> ");
            String email = sc.next();
            System.out.println("        	");

            try (Connection conn = commonUtil.getConnection()) {
                String sql = "INSERT INTO users (id, pw, name, email, stamp) VALUES (?, ?, ?, ?, 0)";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, id);
                    pstmt.setString(2, password);
                    pstmt.setString(3, name);
                    pstmt.setString(4, email);
                    int rowInserted = pstmt.executeUpdate();
                    if (rowInserted > 0) {
                    	System.out.println("회원가입이 완료되었습니다. 로그인을 진행해주세요.");
                    	 System.out.println("        	");
                         return;
                    }
                }
            } catch (SQLException e) {
                System.out.println("회원가입에 실패하였습니다. 다시 시도해주세요.");
                e.printStackTrace();
            }
    	}
        
    }

    // 로그인
    public boolean signIn(Scanner sc) {
    	System.out.println("=========================================== 홈 > 로그인 ==============================================");
    	while(true) {
    		 System.out.print("아이디 >> ");
             String id = sc.next();
             System.out.print("비밀번호 >> ");
             String password = sc.next();

             if (util.validLogin(id, password)) {
                 // 로그인 성공
                 System.out.println(util.getNameById(id) + "님, 반갑습니다.");
                 return true;
             } else {
                 System.out.println("아이디 또는 비밀번호가 올바르지 않습니다. ( 메뉴로 돌아가기 : 0 )");
                 int numZero = sc.nextInt();
                 sc.nextLine();
                 if(numZero == 0) {
                	return true;
                 } else {
                	 continue;
                 }
             }
    	}
    }

    // 적립
    @Override
    public void stamp(Scanner sc) {
        try (Connection conn = commonUtil.getConnection()) {
            String selectSql = "SELECT sum(QUAN) AS \"수량합계\" FROM CART WHERE ID = ?";
            try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
                selectStmt.setString(1, util.getCurrentId());
                try (ResultSet rsQuan = selectStmt.executeQuery()) {
                    int SQuan = 0;
                    if (rsQuan.next()) {
                        SQuan = rsQuan.getInt("수량합계");
                    }

                    String updateSql = "UPDATE USERS "
                            + "SET STAMP = ? "
                            + "WHERE ID = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setInt(1, SQuan);
                        updateStmt.setString(2, util.getCurrentId());
                        int rowsUpdated = updateStmt.executeUpdate();
                        if (rowsUpdated > 0) {
                            System.out.println("적립이 완료되었습니다.");
                        } else {
                            System.out.println("적립에 실패했습니다.");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
