package jp.co.sss.shop.form;

/**
 * 詳細検索用のフォームクラス
 * @author 伊藤
 *	@param keyword 検索ワード
 *	@param categoryId 検索カテゴリーのID
 *	@param bottomPrice 検索する価格の下限
 *	@param topPrice 検索する価格の上限
 */
public class SearchForm {
	private boolean category;
	private boolean price;
	private boolean keyword;

	private String keyword1;
	private Integer id;
	private Integer priceMin;
	private Integer priceMax;
	/**
	 * categoryを取得します。
	 * @return category
	 */
	public boolean isCategory() {
	    return category;
	}
	/**
	 * categoryを設定します。
	 * @param category category
	 */
	public void setCategory(boolean category) {
	    this.category = category;
	}
	/**
	 * priceを取得します。
	 * @return price
	 */
	public boolean isPrice() {
	    return price;
	}
	/**
	 * priceを設定します。
	 * @param price price
	 */
	public void setPrice(boolean price) {
	    this.price = price;
	}
	/**
	 * keywordを取得します。
	 * @return keyword
	 */
	public boolean isKeyword() {
	    return keyword;
	}
	/**
	 * keywordを設定します。
	 * @param keyword keyword
	 */
	public void setKeyword(boolean keyword) {
	    this.keyword = keyword;
	}
	/**
	 * keyword1を取得します。
	 * @return keyword1
	 */
	public String getKeyword1() {
	    return keyword1;
	}
	/**
	 * keyword1を設定します。
	 * @param keyword1 keyword1
	 */
	public void setKeyword1(String keyword1) {
	    this.keyword1 = keyword1;
	}
	/**
	 * idを取得します。
	 * @return id
	 */
	public Integer getId() {
	    return id;
	}
	/**
	 * idを設定します。
	 * @param id id
	 */
	public void setId(Integer id) {
	    this.id = id;
	}
	/**
	 * priceMinを取得します。
	 * @return priceMin
	 */
	public Integer getPriceMin() {
	    return priceMin;
	}
	/**
	 * priceMinを設定します。
	 * @param priceMin priceMin
	 */
	public void setPriceMin(Integer priceMin) {
	    this.priceMin = priceMin;
	}
	/**
	 * priceMaxを取得します。
	 * @return priceMax
	 */
	public Integer getPriceMax() {
	    return priceMax;
	}
	/**
	 * priceMaxを設定します。
	 * @param priceMax priceMax
	 */
	public void setPriceMax(Integer priceMax) {
	    this.priceMax = priceMax;
	}

}
