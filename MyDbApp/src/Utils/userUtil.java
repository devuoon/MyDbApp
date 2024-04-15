package Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class userUtil {
    boolean loggedIn = false;
    static String currentId = null;

    // 아이디 중복 확인
    public boolean IdExistCheck(String id) {
        String sql = "SELECT COUNT(*) FROM users WHERE id = ?";
        int count = userCount(sql, id);
        return count > 0;
    }

    // userCount 쿼리
    private int userCount(String sql, String... params) {
        try (Connection conn = commonUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // 파라미터 설정
            for (int i = 0; i < params.length; i++) {
                pstmt.setString(i + 1, params[i]);
            }
            // 쿼리 실행
            try (ResultSet rs = pstmt.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("쿼리 실행 중 오류가 발생했습니다.");
            e.printStackTrace();
            return -1;
        }
    }

    // 로그인
    public boolean validLogin(String id, String password) {
        String sql = "SELECT COUNT(*) FROM users WHERE id = ? AND pw = ?";
        try (Connection conn = commonUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    if (count > 0) {
                        loggedIn = true; // 로그인 상태 변경
                        currentId = id; // 현재 로그인된 사용자 아이디 저장
                        System.out.println(currentId);
                        return true;
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("로그인 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
        return false;
    }

    // 현재 로그인된 사용자 아이디 반환
    public String getCurrentId() {
        return currentId;
    }

    
    // 로그아웃
    public void logout() {
        loggedIn = false; // 로그아웃 상태로 변경
        currentId = null; // 현재 로그인된 사용자 아이디 초기화
    }



//    // 로그인 상태 반환
//    public boolean isLoggedIn() {
//        return loggedIn;
//    }

    // 로그인 후 이름 조회
    public String getNameById(String id) {
        String sql = "SELECT name FROM users WHERE id = ?";
        try (Connection conn = commonUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("name");
                }
            }
        } catch (SQLException e) {
            System.out.println("이름 조회 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
        return null;
    }
}
