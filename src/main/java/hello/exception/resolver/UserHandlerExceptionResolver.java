package hello.exception.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.exception.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class UserHandlerExceptionResolver implements HandlerExceptionResolver {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {

            if (ex instanceof UserException) {
                log.info("UserException resolver to 400");
                String acceptHeader = request.getHeader("accept");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                // http header가 json/html 이렇게 2가지로 나눠서 만드는게 현명하다.
                if ("application/json".equals(acceptHeader)) {
                    Map<String, Object> errorResult = new HashMap<>();
                    errorResult.put("ex", ex.getClass());
                    errorResult.put("message", ex.getMessage());
                    String result = objectMapper.writeValueAsString(errorResult);
                    // 기존의 예외를 먹어버리고 새롭게 에러를 담아서 response에 담기때문에 ServletContainer까지 전달된다.
                    response.setContentType("application/json");
                    response.setCharacterEncoding("utf-8");
                    response.getWriter().write(result);
                    return new ModelAndView();
                    // 여기서 끝난다. 따로 또 내려오거나 하는거 없음.
                    // response에 값을 다 담기때문에 WAS입장에서는 정상응답으로 받는다. -> ExceptionResolver가 예외들을 해결한다.
                } else {
                    // TEXT/HTML
                    return new ModelAndView("error/500");
                }

            }
        } catch (IOException e) {
            log.error("resolver ex", e);
        }
        return null;
    }
}
