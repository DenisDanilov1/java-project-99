 package hexlet.code.handler;

 import org.springframework.http.HttpStatus;
 import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.ControllerAdvice;
 import org.springframework.web.bind.annotation.ExceptionHandler;
 import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

 import hexlet.code.exception.ResourceNotFoundException;

 @ControllerAdvice
 public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

     @ExceptionHandler(ResourceNotFoundException.class)
     public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
         return ResponseEntity.status(HttpStatus.NOT_FOUND)
                 .body("An error has occurred: " + ex.getMessage());
     }

     @ExceptionHandler(Exception.class)
     public ResponseEntity<String> handleException(Exception ex) {
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                 .body("An error has occurred: " + ex.getMessage());
     }
 }
