package shop.mtcoding.restdocapp.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserReporitory extends JpaRepository<User, Integer>{

    @Query("select u from User u where u.username = :username and u.password = :password")
    Optional<User> findByUsernameAndPassword(@Param("username")String username, @Param("password")String password);
}
