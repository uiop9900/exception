package hello.exception.exhandler.advice;

import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "hello.exception.api") // package로 대상 지정
public class ExControllerAdvice {
    // 기존에 @RestContoller에 같이 있던 예외처리 로직을 따로 가져온다.

    // 여기서 흐름이 끝난다 -> Servlet Container까지 갔다가 다시 돌아오는 흐름이 아님
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 정상적 return 이 아니라 에러를 만들고 싶으면 붙인다.
    // 에러발생 -> ExceptionHandler -> ExceptionHandlerExceptionResolver -> @ExceptionHandler가 있는지 찾음 -> 존재하면 호출
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandler(IllegalArgumentException e) {
        log.error("[exceptionHanlder] ex", e);
        return new ErrorResult("BAD", e.getMessage()); // 정상으로 return 한다. -> http 상태코드 200
    }

    @ExceptionHandler // (UserException.class) 생략 : 아래의 param과 동일하기에 생략가능
    public ResponseEntity<ErrorResult> userHanlder(UserException e) {
        log.error("[exceptionHanlder] ex", e);
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    // @ExceptionHandler 를 사용하면 에러를 잡고 정상적으로 반환 200 되기때문에 에러를 발생시키려면 꼭 @ResponseStatus로 에러를 내려줘야 한다.
    // param으로 넣어준 Exception의 자식들도 잡아준다. => 상단에서 정의된 Exception이 잡지 못하는 예외들을 여기서 다 잡는다.
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHanlder(Exception e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("EX", "내부 오류");
    }

}
