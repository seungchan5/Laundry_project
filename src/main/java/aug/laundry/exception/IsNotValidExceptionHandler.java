package aug.laundry.exception;

import aug.laundry.dao.payment.PaymentDao;
import aug.laundry.domain.Paymentinfo;
import aug.laundry.service.PaymentService;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.io.IOException;
import java.math.BigDecimal;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class IsNotValidExceptionHandler {

    @Value("${import.rest-api}")
    private String restApi;

    @Value("${import.rest-api-secret}")
    private String restApiSecret;

    private IamportClient iamportClient;
    private final PaymentService paymentService;




    @org.springframework.web.bind.annotation.ExceptionHandler(IsNotValidException.class)
    public ResponseEntity<ErrorResponse> isNotValidException(IsNotValidException e) throws IamportResponseException, IOException {

        iamportClient = new IamportClient(restApi, restApiSecret);
        Long paymentinfoId = Long.parseLong(e.getMessage().replaceAll("[^0-9]", ""));
        log.info("paymentinfoId={}", paymentinfoId);

        Paymentinfo paymentinfo = paymentService.findPaymentinfoByPaymentinfoId(paymentinfoId);

        log.info("paymentinfo={}", paymentinfo);
        //환불
        iamportClient.cancelPaymentByImpUid(new CancelData(
                paymentinfo.getImpUid(), true,
                BigDecimal.valueOf(paymentinfo.getAmount())));

        paymentService.updateRefundInfoBypaymentinfoId(paymentinfoId, e.getMessage());

        ErrorResponse response = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
