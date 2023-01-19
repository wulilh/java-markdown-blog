package top.b0x0.jmb.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * 全局异常处理
 *
 * @author wuliling Created By 2023-01-17 10:05
 **/
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({ArticleNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView articleHandler(ArticleNotFoundException notFound) {
        ModelAndView modelAndView = new ModelAndView("error/404_article");
        log.error("article {} not found !", notFound.getSha256());
        return modelAndView;
    }

    @ExceptionHandler({Exception.class, Throwable.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) {
        ModelAndView modelAndView = new ModelAndView("error/500");
        e.printStackTrace();
        log.error("uri : {} , exception: {}", request.getRequestURI(), e.getCause());
        return modelAndView;
    }
}
