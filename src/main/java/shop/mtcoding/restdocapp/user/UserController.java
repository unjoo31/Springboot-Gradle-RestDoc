package shop.mtcoding.restdocapp.user;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.restdocapp.util.ApiUtil;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserReporitory userRepository;

    
    // 회원가입
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid UserRequest.JoinDTO requestDTO, Errors errors){

        if(errors.hasErrors()){
            FieldError fieldError = errors.getFieldErrors().get(0);
            String key = fieldError.getField();
            String value = fieldError.getDefaultMessage();
            return new ResponseEntity<>(ApiUtil.error(value+" : "+key), HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.save(requestDTO.toEntity());

        return ResponseEntity.ok().body(ApiUtil.success(user));
    }

    // 유저조회
    @GetMapping("/users/{id}")
    public ResponseEntity<?> userInfo(@PathVariable Integer id){
        
        Optional<User> userOP = userRepository.findById(id);
        
        // 클린코드 방식
        if(userOP.isEmpty()){
            return new ResponseEntity<>(
                    ApiUtil.error("해당 아이디가 존재하지 않습니다"), 
                    HttpStatus.NOT_FOUND
            );
        }
        return ResponseEntity.ok().body(ApiUtil.success(userOP.get()));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(){
        return ResponseEntity.ok().body(null);
    }
}
