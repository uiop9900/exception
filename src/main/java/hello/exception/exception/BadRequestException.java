package hello.exception.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "error.bad")
public class BadRequestException extends RuntimeException{

    // Spring 자체에 있는 ResponseStatusExceptionResolver에 걸린다.
    // 기존에 Servlet 강의에서 구현했던 에러를 먹고 sendError 에 원하는 에러를 넣었던 로직이 spring 자체에 존재한다.


}
