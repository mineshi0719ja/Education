package jp.co.sss.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.shop.entity.Order;

/**
 * ordersテーブル用リポジトリ
 *
 * @author System Shared
 */
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUserIdAndDeleteFlagOrderByInsertDateDesc(int userId,int deleteFlag);

    List<Order> findByDeleteFlagOrderByInsertDateDesc(int deleteFlag);

}
