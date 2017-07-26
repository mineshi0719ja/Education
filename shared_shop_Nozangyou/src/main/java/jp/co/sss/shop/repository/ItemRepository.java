package jp.co.sss.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import jp.co.sss.shop.entity.Item;

/**
 * itemsテーブル用リポジトリ
 *
 * @author System Shared
 */
@Component
public interface ItemRepository extends JpaRepository<Item, Integer> {
	public List<Item> findByDeleteFlagOrderByInsertDateDesc(int deleteFlag);

	@Query("SELECT i FROM Item i WHERE i.price <1000 and i.deleteFlag = 0")
	public List<Item> findByPriceQuery();

	@Query("SELECT i FROM Item i WHERE i.price >=1000 and i.price<2000 and i.deleteFlag = 0")
	public List<Item> findByPrice1Query();

	@Query("SELECT i FROM Item i WHERE i.price >=2000 and i.price<3000 and i.deleteFlag = 0")
	public List<Item> findByPrice2Query();

	@Query("SELECT i FROM Item i WHERE i.price >=3000 and i.price<10000 and deleteFlag = 0")
	public List<Item> findByPrice3Query();

	@Query("SELECT i FROM Item i WHERE i.price >=10000 and deleteFlag = 0")
	public List<Item> findByPrice4Query();

	/**
	 * キーワード検索時のリポジトリ
	 * @author 岡崎晴
	 */
	@Query("SELECT i FROM Item i WHERE i.name LIKE :keyword and deleteFlag = 0")
	public List<Item> findByNameQuery(@Param("keyword") String keyword);

	/**
	 * @author AyeAyeHan
	 * @return
	 * 価格の昇順で全商品を表示するための追加Query
	 */
	@Query("SELECT i from Item i where i.deleteFlag = 0 ORDER BY i.price Asc")
	public List<Item> findAllPriceAsc();

	/**
	 * @author AyeAyeHan
	 * @return
	 * 価格の降順で全商品を表示るための追加Query
	 */
	@Query("SELECT i from Item i where i.deleteFlag = 0 ORDER BY i.price Desc")
	public List<Item> findAllPriceDesc();

	//全条件の同時検索
	//上限価格
	@Query("SELECT i FROM Item i WHERE i.name LIKE :keyword AND i.category.id = :categoryId AND i.price <= :topPrice AND i.deleteFlag = 0")
	public List<Item> findByKeywordAndCategoryAndTopPrice(@Param("keyword") String keyword, @Param("categoryId") Integer categoryId, @Param("topPrice") Integer topPrice);
	//キーワードと下限価格の同時検索
	@Query("SELECT i FROM Item i WHERE i.name LIKE :keyword AND i.category.id = :categoryId AND i.price >= :bottomPrice AND i.deleteFlag = 0")
	public List<Item> findByKeywordAndCategoryAndBottomPrice(@Param("keyword") String keyword, @Param("categoryId") Integer categoryId, @Param("bottomPrice") Integer bottomPrice);
	//キーワードと上限価格、下限価格の同時検索
	@Query("SELECT i FROM Item i WHERE i.name LIKE :keyword AND i.category.id = :categoryId AND i.price BETWEEN :bottomPrice AND :topPrice AND i.deleteFlag = 0")
	public List<Item> findByKeywordAndCategoryAndPriceBetween(@Param("keyword") String keyword, @Param("categoryId") Integer categoryId,
			@Param("bottomPrice") Integer bottomPrice, @Param("topPrice") Integer topPrice);

	//キーワードとカテゴリーの同時検索
	@Query("SELECT i FROM Item i WHERE i.category.id = :categoryId AND i.name LIKE :keyword AND i.deleteFlag = 0")
	public List<Item> findByKeywordLikeAndCategory( @Param("keyword") String keyword, @Param("categoryId") Integer categoryId);
	//キーワードと上限価格の同時検索
	@Query("SELECT i FROM Item i WHERE i.name LIKE :keyword AND i.price <= :topPrice AND i.deleteFlag = 0")
	public List<Item> findByKeywordAndTopPrice(@Param("keyword") String keyword, @Param("topPrice") Integer topPrice);
	//キーワードと下限価格の同時検索
	@Query("SELECT i FROM Item i WHERE i.name LIKE :keyword AND i.price >= :bottomPrice AND i.deleteFlag = 0")
	public List<Item> findByKeywordAndBottomPrice(@Param("keyword") String keyword, @Param("bottomPrice") Integer bottomPrice);
	//キーワードと上限価格、下限価格の同時検索
	@Query("SELECT i FROM Item i WHERE i.name LIKE :keyword AND i.price BETWEEN :bottomPrice AND :topPrice AND i.deleteFlag = 0")
	public List<Item> findByKeywordAndPriceBetween(@Param("keyword") String keyword,
			@Param("bottomPrice") Integer bottomPrice, @Param("topPrice") Integer topPrice);

	//カテゴリーと上限価格の同時検索
	@Query("SELECT i FROM Item i WHERE i.category.id = :categoryId AND i.price <= :topPrice AND i.deleteFlag = 0")
	public List<Item> findByCategoryAndTopPrice(@Param("categoryId") Integer categoryId, @Param("topPrice") Integer topPrice);
	//カテゴリーと下限価格の同時検索
	@Query("SELECT i FROM Item i WHERE i.category.id = :categoryId AND i.price >= :bottomPrice AND i.deleteFlag = 0")
	public List<Item> findByCategoryAndBottomPrice(@Param("categoryId") Integer categoryId, @Param("bottomPrice") Integer bottomPrice);
	//カテゴリーと上限価格、下限価格の同時検索
	@Query("SELECT i FROM Item i WHERE i.category.id = :categoryId AND i.price >= :bottomPrice AND i.price <= :topPrice AND i.deleteFlag = 0")
	public List<Item> findByCategoryAndPriceBetween(@Param("categoryId") Integer categoryId,
			@Param("bottomPrice") Integer bottomPrice, @Param("topPrice") Integer topPrice);
}