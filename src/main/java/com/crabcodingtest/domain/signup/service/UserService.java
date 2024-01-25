package com.crabcodingtest.domain.signup.service;

import com.crabcodingtest.domain.signup.entity.User;
import com.crabcodingtest.domain.signup.repository.SignupUserRepository;
import com.crabcodingtest.domain.signup.repository.SignupUserRepository_Test;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
// JPA를 사용하는 로직에는 반드시 @Transactional 을 부여해주는 것이 좋다. 그래야 lazy loading도 가능하고, open session ???
// 아무튼 기본적으로 데이터 변경 로직에는 @Transactional 있는 게 좋다.
@Transactional(readOnly = true) // 데이터를 변경하지 않고 '읽기'만 하는 경우에는 readOnly = true 속성을 부여하여 성능 상에서 이점을 가져가도록 하자.
@RequiredArgsConstructor
public class UserService {

    // 회원 서비스 개발 - 11~15분대
    // 필드 주입, Setter 주입, 생성자 주입 설명!!!

    private final SignupUserRepository_Test repository;

    /**
     * 회원가입
     * @param user
     * @return
     */
    @Transactional // readOnly = true 속성이 많은 경우엔 차라리 이렇게 필요한 부분에만 따로 @Transactional 어노테이션을 부여해주면 더 가독성 있다. (어차피 디폴트는 false이므로)
    public Long join(User user){
        validateDuplicateUser(user); // 중복 회원 검증
        repository.save(user);
        return user.getId();
    }

    private void validateDuplicateUser(User user) {
        // cf. 아래 로직의 경우 동시성 처리가 안돼있다. (여러 개 WAS, 멀티스레드 등..) 따라서 DB상에서 name을 unique value로 하여 2차 방어를 하는 방법도 있다.
        List<User> findUsers = repository.findByName(user.getName());
        if(findUsers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체조회
    public List<User> findUsers(){
        return repository.findAll();
    }

    // 회원 1명 조회
    public User findOne(Long userId){
        return repository.findOne(userId);
    }
}
