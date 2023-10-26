package aug.laundry.enums.zipcode;

import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter
public enum ZipCode_SEOUL {
    강북구("강북구", Arrays.asList("010", "011", "012")),
    도봉구("도봉구", Arrays.asList("013", "014", "015")),
    노원구("노원구", Arrays.asList("016", "017", "018", "019")),
    중랑구("중랑구", Arrays.asList("020", "021", "022", "023")),
    동대문구("동대문구", Arrays.asList("024", "025", "026")),
    성북구("성북구", Arrays.asList("027", "028", "029")),
    종로구("종로구", Arrays.asList("030", "031", "032")),
    은평구("은평구", Arrays.asList("033", "034", "035")),
    서대문구("서대문구", Arrays.asList("036", "037", "038")),
    마포구("마포구", Arrays.asList("039", "040", "041", "042")),
    용산구("용산구", Arrays.asList("043", "044")),
    중구("중구", Arrays.asList("045", "046")),
    성동구("성동구", Arrays.asList("047", "048")),
    광진구("광진구", Arrays.asList("049", "050", "051")),
    강동구("강동구", Arrays.asList("052", "053", "054")),
    송파구("송파구", Arrays.asList("055", "056", "057", "058", "059")),
    강남구("강남구", Arrays.asList("060", "061", "062", "063", "064")),
    서초구("서초구", Arrays.asList("065", "066", "067", "068")),
    동작구("동작구", Arrays.asList("069", "070", "071")),
    영등포구("영등포구", Arrays.asList("072", "073", "074")),
    강서구("강서구", Arrays.asList("075", "076", "077", "078")),
    양천구("양천구", Arrays.asList("079", "080", "081")),
    구로구("구로구", Arrays.asList("082", "083", "084")),
    금천구("금천구", Arrays.asList("085", "086")),
    관악구("관악구", Arrays.asList("087", "088", "089")),
    없음("없음", Collections.EMPTY_LIST);

    private String title;
    private List<String> zipCode;

    ZipCode_SEOUL(String title, List zipCode) {
        this.title = title;
        this.zipCode = zipCode;
    }

    public static String findByZipCode(String zipCode) {
        String code = zipCode.substring(0, 3);
        ZipCode_SEOUL zip = Arrays.stream(values())
                .filter(zipCodeSeoul -> zipCodeSeoul.zipCode.contains(code))
                .findAny()
                .orElse(없음);
        return zip.title.equals("없음") ? zip.title : "서울시 " + zip.title;
    }
}
