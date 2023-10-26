package aug.laundry.enums.category;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Category {

        COMMON("일반", null, null),
            BASIC("생활빨래", 4000L, COMMON),
            ADDITIONAL("생활빨래 20L 초과시 10L 당", 3800L, COMMON),

        CLOTHES("의류", null, null),
            Y_SHIRT("와이셔츠", 2100L, CLOTHES),
            SCHOOL_UNIFORM_SHIRT("교복셔츠", 2100L, CLOTHES),
            SCHOOL_UNIFORM_JACKET("교복자켓", 5000L, CLOTHES),
            REGULAR_SHIRT("일반셔츠", 2100L, CLOTHES),
            BLOUSE("블라우스", 4200L, CLOTHES),
            T_SHIRT("티셔츠", 4200L, CLOTHES),
            SWEAT_SHIRT("맨투맨", 4200L, CLOTHES),
            HOODIE("후드티", 4800L, CLOTHES),
            KNITWEAR("니트", 5500L, CLOTHES),
            SWEATER("스웨터", 5500L, CLOTHES),
            CARDIGAN("가디건", 5500L, CLOTHES),
            PANTS("바지", 4800L, CLOTHES),
            SKIRT("스커트", 4800L, CLOTHES),
            ONEPIECE("원피스", 6800L, CLOTHES),
            JUMPSUIT("점프수트", 6800L, CLOTHES),
            ARTIFICIAL_SKIN("인조가죽하의", 11000L, CLOTHES),
            VEST("조끼", 3000L, CLOTHES),
            PADDED_VEST("패딩조끼", 8000L, CLOTHES),
            SKI_BOARD_PANTS("스키,보드 바지", 24800L, CLOTHES),
            SKI_BOARD_JUMP_SUIT("스키, 보드 점스수트", 46800L, CLOTHES),
            SKI_BOARD_JACKET("스키, 보드 자켓", 37000L, CLOTHES),
            PADDED_PANTS("패딩바지", 11000L, CLOTHES),
            SUIT_JACKET("정장자켓", 5000L, CLOTHES),
            JACKET("자켓", 8000L, CLOTHES),
            JUMPER("점퍼", 8000L, CLOTHES),
            COAT("코트", 14000L, CLOTHES),
            TRENCH_COAT("트렌치 코드", 14000L, CLOTHES),
            LIGHTWEIGHT_PADDING("경량패딩", 9000L, CLOTHES),
            PADDING("일반패딩", 16800L, CLOTHES),
            DOWN_PADDING("다운패딩", 16800L, CLOTHES),
            ARTIFICIAL_LEATHER_JACKET("인조가죽자켓", 15000L, CLOTHES),
            TIE("넥타이", 2500L, CLOTHES),
            MUFFLER("목도리", 4000L, CLOTHES),
            SCARF("스카프", 4000L, CLOTHES),
            GLOVES("장갑", 4000L, CLOTHES),
            KNIT_CAP("니트모자", 4000L, CLOTHES),
            CAP_HAT("캡모자", 6000L, CLOTHES),

        BEDDING("침구류", null, null),
            REGULAR_BLANKET("일반이불", 12000L, BEDDING),
            MICROFIBER_BLANKET("극세사이불", 16000L, BEDDING),
            DOWNFER_BLANKET("다운퍼이불 (오리, 거위털)", 22000L, BEDDING),
            WOOL_BLANKET("양모이불", 23000L, BEDDING),
            SILK_QUIT_OF_SILK("실크이불", 25000L, BEDDING),
            BLANKET_PAD("이불패드", 10000L, BEDDING),
            BLANKET_COVER("이불커버", 10000L, BEDDING),
            SINGLE_BLANKET("홑이불", 10000L, BEDDING),
            REGULAR_TOPPER("일반토퍼", 18000L, BEDDING),
            GOOSE_TOPPER("구스토퍼", 25000L, BEDDING),
            PILLOW_COVER("베개커버", 3500L, BEDDING),
            PILLOW_COTTON("베개(솜)", 10000L, BEDDING),
            PILLOW_DOWNFER("베개(다운퍼)", 12000L, BEDDING),
        SHOES("신발", null, null),
            SNEAKERS("운동화", 6000L, SHOES),
            SHOESS("구두", 7000L, SHOES),
            LOAFERS("로퍼", 7000L, SHOES),
            SPORTS_SHOES("스포츠화", 9000L, SHOES),
            WALKER("워커", 11000L, SHOES),
            BOOTS("부츠", 15000L, SHOES),
            UGG_BOOTS("어그부츠", 20000L, SHOES);


    private final String title;
    private final Long price;
    private final Category parentCategory;
    private final Map<String, Long> childCategories;

    Category(String title, Long price, Category parentCategory) {
        this.childCategories = new ConcurrentHashMap<>();
        this.title = title;
        this.price = price;
        this.parentCategory = parentCategory;
        // parentCategory가 null이 아니라면
        if (Objects.nonNull(parentCategory)) {
            parentCategory.childCategories.put(this.title, Objects.isNull(this.price) ? 0L : this.price);
        }

    }

    // 상위 카테고리 이름 가져오기
    public Category getParentCategory() {
        return Optional.ofNullable(parentCategory).get();
    }

    // 하위 카테고리 리스트 가져오기
    public Map<String, Long> getChildCategories() {
        return Collections.unmodifiableMap(childCategories);
    }

    // 전체 하위카테고리 가져오기
    public static Map<String, Long> getAll() {
        return Arrays.stream(Category.values()).filter(x -> Objects.nonNull(x.parentCategory)).collect(Collectors.toMap(y -> y.title, y -> y.price));
    }

    // 상위 카테고리 전체 가져오기
    public static Set<Category> getParentCategoryAll() {
        return Arrays.stream(Category.values()).filter(x -> Objects.isNull(x.getPrice()) && x != Category.COMMON).collect(Collectors.toSet());
    }

    // 카테고리 Title로 카테고리 가져오기
    public static Optional<Category> findByTitle(String title) {
        return Arrays.stream(Category.values()).filter(x -> x.getTitle().equals(title)).findAny();
    }

}

