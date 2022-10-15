package hello.exception.servlet;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
public class ServletController {

    @GetMapping("/error-ex")
    public void errorEx() {
        throw  new RuntimeException("예외발생!");
    }

    // 내가 sendError로 404에러를 보냈기때문에 아래의 매핑으로 들어오면 404에러가 난다. (상태코드를 지정할수있다)
    @GetMapping("/error-404")
    public void error404(HttpServletResponse response) throws IOException {
        response.sendError(404, "404오류!");
    }

    @GetMapping("/error-500")
    public void error500(HttpServletResponse response) throws IOException {
        response.sendError(500, "500오류!");
    }
}
