package jp.co.sss.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.shop.entity.Category;

/**
 * categoriesテーブル用リポジトリ
 *
 * @author System Shared
 */
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findByNameAndDeleteFlag(String name, int deleteFlag);

    List<Category> findByDeleteFlagOrderByInsertDateDesc(int deleteFlag);

}
