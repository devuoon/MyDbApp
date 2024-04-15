package Users;

import java.util.Scanner;

public interface user {
    // 회원가입
    public void signUp(Scanner sc);

    // 로그인
    public boolean signIn(Scanner sc);

    // 스탬프 계산
    public void stamp(Scanner sc);
}
