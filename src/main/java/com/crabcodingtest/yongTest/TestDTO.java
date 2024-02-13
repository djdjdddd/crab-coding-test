package com.crabcodingtest.yongTest;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
// 참조 : ★https://91ms.tistory.com/29
// JSON <-> Java 객체 간의 변환을 해주는 Jackson 라이브러리가 변환을 하는 과정에서
// 기본 생성자와 같은 creators를 필요로 하므로 추가해줬다.
// 단, 클래스 바깥에서 함부로 생성을 막아주고 싶었기에 AccessLevel을 PRIVATE으로 주었다.
// (@Builder 어노테이션이 기본 생성자를 만들어주지 않기 때문에 이러한 과정이 따로 필요했음)
public class TestDTO {

    // ★Validation
    // ★FE에서 할 수 있는만큼 똑같이 validation 할 수 있는 거 같은데??
    // 대신.. BE에서 하는 거니까 2차 체크용, 로그용으로 사용해야 될 거 같기도...
    // 아니면 WRMS처럼 BE에서 난 에러를 화면에 보여주는 장치를 해준다면..? 굳이 FE에서 js로 번거롭게 유효성 검사 로직 안 짜도 될 거 같은데??

    private String str;
    @NotBlank(message = "null 또는 \"\" 또는 \" \" 일 순 없다 이말이야.")
    @Email // "asdf@naver" <- 이렇게 입력해도 Validation 통과하는데.. 사실상 효용이 있다고 할 수 있는지;;
           // 제대로 사용하려면 추가적으로 Error 커스터마이징을 해서 써야 되려나
           // @Pattern(regex=) <- 정규식 써서 해결하면 될 듯.
    private String mail;
    @Max(10)
    private int num;
    @Positive
    private int positiveInteger;
    @AssertTrue // 또는 @AssertFalse
    private boolean bool;
    @DecimalMax(value = "10.0")
    private double d;
    @Past
    LocalDate localDate;
    //@NotBlank
    private TestEnum testEnum;
    //@NotBlank
    private List<TestEnum> testEnumList;

    @Slf4j
    @Getter
    @RequiredArgsConstructor
    public enum TestEnum {
//        SUCCESS("SUCCESS"),FAIL("FAIL"),TEST("TEST"); // 애초에 123 같은 숫자를 쓰면 컴파일 에러가 뜨네??
        SUCCESS,FAIL,TEST;

        //private final String str;

        // @JsonCreator, @JsonProperty 에 대한 공부 필요 (잭슨 라이브러리꺼네)
        @JsonCreator // ★annotation used for indicating that a constructor or static factory method should be used for creating value instances during deserialization.
        public static TestEnum from(/*@JsonProperty("")*/ String param){
            for(TestEnum testEnum : TestEnum.values()){
                if(testEnum.name().equals(param)){ // 1. 즉 FE에서 들어온 값이 "FAIL"이면
                    return testEnum; // 2. FAIL 객체를 리턴
                }
//                if(param.equalsIgnoreCase(testEnum)){ // 1. 즉 FE에서 들어온 값이 "FAIL"이면
//                    return testEnum; // 2. FAIL 객체를 리턴
//                }
            }
//            throw new BusinessLogicException(ExceptionCode.INVALID_GENDER);
            throw new IllegalArgumentException(">>>>>>>>>>>>>>>>>>>> 잘못된 값을 입력하셨습니다 : " + param);
        }
    }

    @Builder
    public TestDTO(String str, String mail, int num, boolean bool, double d, TestEnum testEnum, List<TestEnum> testEnumList){
        this.str = str;
        this.mail = mail;
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
