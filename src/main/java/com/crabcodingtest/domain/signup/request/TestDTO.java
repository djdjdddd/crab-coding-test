package com.crabcodingtest.domain.signup.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
// 참조 : ★https://91ms.tistory.com/29
// JSON <-> Java 객체 간의 변환을 해주는 Jackson 라이브러리가 변환을 하는 과정에서
// 기본 생성자와 같은 creators를 필요로 하므로 추가해줬다.
// 단, 클래스 바깥에서 함부로 생성을 막아주고 싶었기에 AccessLevel을 PRIVATE으로 주었다.
// (@Builder 어노테이션이 기본 생성자를 만들어주지 않기 때문에 이러한 과정이 따로 필요했음)
public class TestDTO {
    private String str;
    private int num;
    private boolean bool;
    private double d;
    private TestEnum testEnum;
    private List<TestEnum> testEnumList;

    @Getter
    public enum TestEnum{
        SUCCESS(111), FAIL(222), TEST(999); // 애초에 123 같은 숫자를 쓰면 컴파일 에러가 뜨네??
        private final int number;
        TestEnum(int number){
            this.number = number;
        }
    }

    @Builder
    public TestDTO(String str, int num, boolean bool, double d, TestEnum testEnum, List<TestEnum> testEnumList){
        this.str = str;
        this.testEnum = testEnum;
        this.testEnumList = testEnumList;
        /**
         * 생성자에 Builder 패턴을 적용한다는 건가?
         * this.num = num;
         * this.bool = bool;
         * 이런 걸 생략하니까 값이 세팅이 안되더라. (당연한 건데, 잘 이해가 안됐음. 왜 굳이 써야 하는지..)
         * ★걍 생성자에서 this.a = a; 하는 거랑 똑같은 거네
         */
    }
}
