package aug.laundry.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponse {

    private int code;
    private String message;

    @Builder
    public ErrorResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
