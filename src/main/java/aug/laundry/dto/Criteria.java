package aug.laundry.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria {
    int pageNo = 1; // 요청 페이지 번호
    int amount = 10; // 한페이지당 게시물수
    int startNo = 1;
    int endNo = 10;
    private int total; // 총 게시물 수
    int realEnd; // 페이지 끝번호

    public void setPageNo(int pageNo, int total) {
        this.pageNo = pageNo;
        this.total = total;
        this.realEnd = (int)Math.ceil((total*1.0)/amount);

        if(pageNo>0) {
            endNo = pageNo * amount;
            startNo = pageNo * amount - (amount-1);
        }
    }
}
