package aug.laundry.enums.repair;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RepairCategory {

    RE_SEALING("재박음질", 3000L),
    RE_SEALING_INNER("재박음질 (안감)", 7000L),
    BUTTON("단추달기/조이기", 1000L),
    HOOK("후크달기/조이기", 4000L),
    RUBBER_BAND("고무줄 수선", 10000L),
    WAIST("허리수선", 15000L),
    QUILTED("누빔", 5000L),
    OVERHANGING("덧댐", 15000L),
    ZIPPER_SLIDER("지퍼 슬라이더 교체", 5000L),
    SLEEVE("소매수선", 15000L),
    FORM_SIZE_REDUCE("폼 줄이기", 25000L),
    SIZE_REDUCE("사이즈 수선", 50000L),
    RE_SEALING_MORE_THAN_30("재박음질(30cm이상)", 7000L),
    WAIST_SUIT("허리수선(정장바지)", 7000L),
    LENGTH_BOTTOM("기장수선(하의)", 6000L),
    LENGTH_TOP("기장수선(상의)", 15000L),
    LENGTH_OUTER("기장수선(겉옷)", 30000L),
    ZIPPER_PANTS("지퍼교체(바지)", 10000L),
    ZIPPER_ETC("지퍼교체(기타)", 15000L),
    ZIPPER_OUTER("지퍼교체(겉옷)", 25000L),
    SLEEVE_DECORATION("소매수선(장식소매)", 30000L),
    FORM_SIZE_REDUCE_SHOULDER("품 줄이기(어깨+품)", 40000L),
    PANTS_BOTTOM_WIDTH("통수선(바지밑통)", 10000L),
    PANTS_ENTIRE_WIDTH("통수선(바지전체)", 20000L),
    PANTS_LENGTH_BOTTOM_WIDTH("통수선(바지기장+밑통)", 15000L),
    PADDING_HAT_RACCOON("패딩모자 털 교체(라쿤)", 120000L),
    DOWNPADDING_FUR_CHARGE_LONG_GOOSE("다운패딩 털 충전 롱패딩 (거위)", 250000L),
    DOWNPADDING_FUR_CHARGE_SHORT_GOOSE("다운패딩 털 충전 숏패딩 (거위)", 200000L),
    DOWNPADDING_FUR_CHARGE_LONG_DUCK("다운패딩 털 충전 롱패딩 (오리)", 200000L),
    DOWNPADDING_FUR_CHARGE_SHORT_DUCK("다운패딩 털 충전 숏패딩 (오리)", 150000L),
    CHANGE_NATURAL_LEATHER_COLLAR("천연가죽 교체 (카라/주머니)", 20000L),
    CHANGE_ARTIFICIAL_LEATHER_COLLAR("인조가죽 교체 (카라/주머니)", 15000L),
    SLEEVE_BANDING("소매 시보리 수선", 15000L),
    NECKLINE_BANDING("목둘레 시보리 수선", 5000L),
    HEM_BANDING("밑단 시보리 수선", 15000L),
    LOOSE_KNIT("올뜯김(니트류 - 1cm이하)", 25000L),
    LOOSE_COMMON("올뜯김(일반의류 - 2cm이하)", 35000L),
    LOOSE_PLUS_5MM("올뜯김(0.5cm 추가)", 10000L),
    LOOSE_NECKLINE_LESS_THAN_1CM("목선풀림(1cm 이하)", 15000L),
    LOOSE_NECKLINE_5MM("목선풀림(0.5cm 추가)", 5000L),
    LOOSE_NECKLINE_HALF("목선풀림(절반)", 35000L),
    LOOSE_NECKLINE_ALL("목선풀림(전체)", 60000L),
    REDUCE_BELT_LENGTH("벨트 길이 줄임", 5000L),
    PUNCH_BELT_HOLE("벨트 구멍 펀칭", 2000L),
    REINFORCE_SHOES_SOLE_ALL("밑창보강(구두-일체형)", 39000L),
    REINFORCE_SNEAKERS_SOLE_("밑창보강(운동화)", 45000L),
    REINFORCE_SHOES_SOLE_PART("밑창보강(구두-부분)", 25000L),
    REPLACE_HEEL_FABRIC("뒤꿈치 원단 교체", 39000L),
    RESTORE_DISCOLORATION("변색복원(운동화)", 19000L),
    SHOES_SOLE_GLUE_PART("밑창접착(부분)", 10000L),
    SHOES_SOLE_GLUE_ALL("밑창보강(전체)", 29000L);

    private final String title;
    private final Long price;

    RepairCategory(String title, Long price) {
        this.title = title;
        this.price = price;
    }
}
