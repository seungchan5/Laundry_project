package aug.laundry.dao;

import aug.laundry.domain.CouponList;
import aug.laundry.domain.Orders;
import aug.laundry.dto.*;
import aug.laundry.enums.category.Category;
import aug.laundry.enums.category.Pass;
import aug.laundry.enums.repair.RepairCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URLDecoder;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class LaundryRepository {

    private final LaundryMapper laundryMapper;

    @Value("${repair.images}")
    private String directory;


    public OrderInfo firstInfo(Long memberId, Long ordersDetailId) {
        OrderInfo orderInfo = new OrderInfo();

        OrderInfoDB orderInfoDB = laundryMapper.firstInfo(memberId, ordersDetailId);
        if (orderInfoDB.getIsQuick() != null) orderInfo.setQuick(true);
        if (orderInfoDB.getIsDry() != null) orderInfo.setDry(true);
        if (orderInfoDB.getIsCommon() != null) orderInfo.setCommon(true);
        if (orderInfoDB.getIsRepair() != null) orderInfo.setRepair(true);
        return orderInfo;
    }

    public List<MyCoupon> getCoupon(Long memberId) {
        return laundryMapper.getCoupon(memberId);
    }


    public Address getAddress(Long memberId) {
        return laundryMapper.getAddress(memberId);
    }

    public List<Category> getDry(Long memberId, Long ordersDetailId) {
        return laundryMapper.getDry(memberId, ordersDetailId).stream().map(x -> Category.valueOf(x)).collect(Collectors.toList());
    }

    public List<RepairCategory> getRepair(Long memberId, Long ordersDetailId) {
        return laundryMapper.getRepair(memberId, ordersDetailId).stream().map(x -> RepairCategory.valueOf(x)).collect(Collectors.toList());
    }

    public Pass isPass(Long memberId) {
        return laundryMapper.isPass(memberId) == null ? Pass.COMMON : Pass.PASS;
    }

    public boolean validCoupon(Long memberId, Long couponListId) {
        if (couponListId == null) return false;
        CouponList couponList = laundryMapper.validCoupon(memberId, couponListId);
        if (couponList == null || !couponList.getMemberId().equals(memberId) || !couponList.getCouponListId().equals(couponListId) || couponList.getCouponListStatus() != 1L) {
            return false;
        }
        return true;
    }

    public Integer useCoupon(Long memberId, Long couponListId, Long ordersId) {
        return laundryMapper.useCoupon(memberId, couponListId, ordersId);
    }


    public void insert(Orders orders) {
        laundryMapper.insert(orders);
    }

    public Long getCouponDiscount(Long memberId, Long couponListId) {
        return laundryMapper.getCouponDiscount(memberId, couponListId);
    }

    public Long check(Long memberId, Long ordersDetailId) {
        return laundryMapper.check(memberId, ordersDetailId);
    }

    @Transactional
    public void removeOrdersDetail(Long ordersDetailId) {
        laundryMapper.removeDrycleaning(ordersDetailId); // 드라이클리닝 장바구니 삭제
        log.info("remove Drycleaning");
        laundryMapper.removeCommon(ordersDetailId); // 생활빨래 장바구니 삭제
        log.info("remove Common");
        List<Long> repairIdAll = laundryMapper.getRepairId(ordersDetailId);
        for (Long repairId : repairIdAll) {
            List<String> repairImageStoreNames = laundryMapper.getRepairImageStoreName(repairId); // 실제 이미지 파일 삭제
            System.out.println("repairImageStoreNames = " + repairImageStoreNames);
            removeRepairImageFile(repairImageStoreNames);
            laundryMapper.removeRepairImages(repairId); // RepairImage DB에서 삭제
        }
        log.info("remove RepairImage");
        laundryMapper.removeRepair(ordersDetailId); // 수선 장바구니 삭제
        log.info("remove Repair");
        laundryMapper.removeQuickLaundry(ordersDetailId);
        log.info("remove QuickLaundry");
        laundryMapper.removeOrdersDetail(ordersDetailId); // 장바구니 삭제
        log.info("remove OrdersDetail");
    }

    private void removeRepairImageFile(List<String> repairImageStoreNames) {
        // 실제저장된 Repair Image 파일 삭제
        for (String storeName : repairImageStoreNames) {
            String srcFileName = URLDecoder.decode(directory + storeName);
            System.out.println("srcFileName = " + srcFileName);
            File file = new File(srcFileName);
            boolean delete = file.delete();
        }
    }

    public void createOrdersDetail(Long memberId) {
        laundryMapper.createOrdersDetail(memberId);
    }

    public void insertDryCleaning(Long ordersDetailId, Category category) {
        laundryMapper.insertDryCleaning(ordersDetailId, category.name());
    }

    public void removeDryCleaning(Long ordersDetailId) {
        laundryMapper.removeDrycleaning(ordersDetailId);
    }

    public void removeRepair(Long ordersDetailId) {
        laundryMapper.removeRepair(ordersDetailId);
    }
    public void removeCommon(Long ordersDetailId) {
        laundryMapper.removeCommon(ordersDetailId);
    }

    public List<OrderDrycleaning> reloadDrycleaning(Long orderDetailId) {
        List<OrderDrycleaning> result = laundryMapper.reloadDrycleaning(orderDetailId);
        if (result == null || result.isEmpty()) return null;
        return result;
    }

    public List<OrderRepair> reloadRepair(Long orderDetailId) {
        return laundryMapper.reloadRepair(orderDetailId);
    }

    public List<String> getRepairImage(Long repairId) {
        return laundryMapper.getRepairImage(repairId);
    }

    public Long insertRepair(Long ordersDetailId, Map<String, RepairFormData> repairData, List<MultipartFile> files) {
        for (String s : repairData.keySet()) { // s = 1 ~ 시작되는 숫자
            InsertRepairDto insertRepairDto = new InsertRepairDto();
            insertRepairDto.setOrdersDetailId(ordersDetailId);
            insertRepairDto.setRequest(repairData.get(s).getRequest());
            insertRepairDto.setCategory(repairData.get(s).getTitle());
            laundryMapper.insertRepair(insertRepairDto);
            log.info("InsertRepairDto.getRepairId = {}", insertRepairDto.getRepairId());
            return insertRepairDto.getRepairId();
        }
        return null;
    }

    public List<Long> getRepairId(Long ordersDetailId) {
        return laundryMapper.getRepairId(ordersDetailId);
    }

    public void insertCommon(Long ordersDetailId) {
        laundryMapper.insertCommon(ordersDetailId);
    }

    public void insertQuickLaundry(Long ordersDetailId) {
        laundryMapper.insertQuickLaundry(ordersDetailId);
    }

    public void updateOrdersDetail(Long ordersId, Long ordersDetailId) {
        laundryMapper.updateOrdersDetail(ordersId, ordersDetailId);
    }

    public void insertInspection(Long ordersId) {
        laundryMapper.insertInspection(ordersId);
    }

    public List<Long> findByRepairId(Long ordersDetailId) {
        return laundryMapper.findByRepairId(ordersDetailId);
    }

    public void removeRepairImages(Long repairId) {
        laundryMapper.removeRepairImages(repairId);
    }

    public void removeRepairImagesFile(Long repairId) {
        List<String> repairImageStoreNames = laundryMapper.getRepairImageStoreName(repairId); // 실제 이미지 파일 삭제
        System.out.println("repairImageStoreNames = " + repairImageStoreNames);
        removeRepairImageFile(repairImageStoreNames);
    }
  
   public List<MyCoupon> getCoupon2(Long memberId) {
        List<MyCoupon> coupon = laundryMapper.getCoupon2(memberId);
        log.info("getCoupon2={}", coupon);
        return coupon;
    }
}
