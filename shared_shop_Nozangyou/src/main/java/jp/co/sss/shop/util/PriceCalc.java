package jp.co.sss.shop.util;

import java.util.List;

import jp.co.sss.shop.entity.OrderItem;

/**
 * 料金計算用クラス
 *
 * @author System Shared
 */
public class PriceCalc {
	/**
	 * 注文した商品の合計金額を計算
	 *
	 * @param list
	 *            注文した商品情報
	 * @return 合計金額
	 */
	public static int orderItemPriceTotal(List<OrderItem> list) {
		int total = 0;

		for (OrderItem orderItem : list) {
			total = total + (orderItem.getItem().getPrice() * orderItem.getQuantity());
		}

		return total;
	}

}
