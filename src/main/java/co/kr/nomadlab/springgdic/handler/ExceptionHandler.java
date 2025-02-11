package co.kr.nomadlab.springgdic.handler;

import co.kr.nomadlab.springgdic.exception.CustomException;
import co.kr.nomadlab.springgdic.exception.CustomRestException;
import co.kr.nomadlab.springgdic.util.Script;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(CustomException.class)
    public ResponseEntity<?> customException(CustomException e) {
        return new ResponseEntity<>(Script.back(e.getMessage()), e.getStatus());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(CustomRestException.class)
    public ResponseEntity<?> customRestException(CustomRestException e) {
        return new ResponseEntity<>(e.getMessage(), e.getStatus());
    }

}
