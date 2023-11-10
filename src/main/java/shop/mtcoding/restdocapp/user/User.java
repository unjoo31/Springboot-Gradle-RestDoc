package shop.mtcoding.restdocapp.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 무조건 있어야함 : 오브젝트 매핑때 기본적으로 default constractor를 때리는데 없으면 안됨
@Getter
public class User {
    @Id // import javax.persistence.Id; -> jacarta?
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, length = 20, nullable = false)
    private String username;
    @Column(length = 100, nullable = false)
    private String password;
    @Column(length = 100, nullable = false)
    private String email;

    // 메서드를 만들때는 의미있는 이름으로 만들기 (의미있는 setter 만들기)
    public void updatePassword(String password){
        this.password = password;
    }

    @Builder
    public User(Integer id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
