package jp.co.sss.shop.controller.item;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sss.shop.bean.ItemBean;
import jp.co.sss.shop.bean.OrderItemBean;
import jp.co.sss.shop.entity.Category;
import jp.co.sss.shop.entity.Item;
import jp.co.sss.shop.entity.OrderItem;
import jp.co.sss.shop.form.CategoryForm;
import jp.co.sss.shop.form.SearchForm;
import jp.co.sss.shop.repository.CategoryRepository;
import jp.co.sss.shop.repository.ItemRepository;
import jp.co.sss.shop.util.Constant;

@Controller
public class ItemShowCustomerController {
	@Autowired
	ItemRepository itemRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	HttpSession session;

	/**
	 * トップ画面表示
	 * @param model
	 * @return
	 * @author 伊藤
	 */
	@RequestMapping(path = "/")
	public String index(Model model) {
		List<Item> itemList = itemRepository.findByDeleteFlagOrderByInsertDateDesc(Constant.NOT_DELETED);

		//商品がない場合
		if(itemList.size() == 0){
			return "index";
		}

		//リストから注文履歴のないものを除外
		for(int i = (itemList.size() - 1); i >= 0; i--){
			if(itemList.get(i).getOrderItemsList().size() == 0){
				itemList.remove(i);
			}
		}

		//商品がない場合を再検証
		if(itemList.size() == 0){
			return "index";
		}

		//OrderItemBeanリストの作成
		List<OrderItemBean> items = new ArrayList<OrderItemBean>();
		for(Item item: itemList){
			OrderItemBean orderItemBean = new OrderItemBean();
			orderItemBean.setId(item.getId());
			orderItemBean.setName(item.getName());
			orderItemBean.setPrice(item.getPrice());
			orderItemBean.setImage(item.getImage());

			Integer sum = 0;
			for(OrderItem orderItem: item.getOrderItemsList()){
				sum += orderItem.getQuantity();
			}
			orderItemBean.setSubtotal(sum);

			items.add(orderItemBean);
		}

		//注文数の多い順にソート
		for (int j = 0; j < items.size(); j++) {
			for (int k = 1; k < (items.size() - j); k++) {
				if (Integer.compare(items.get(k).getSubtotal(), items.get(k - 1).getSubtotal()) > 0) {
					items = exChange(items, k);
				}
			}
		}

		//リスト内の要素数を10まで調整
		while(items.size() > 10){
			items.remove(10);
		}

		model.addAttribute("items", items);
		return "index";
	}

	/**
	 * @author 伊藤
	 * 商品を新着順に表示取得する
	 */
	@RequestMapping(path="/item/list/1/{index}")
	public String showItemsOrderByNew(@PathVariable int index, Model model) {
		 List<Item> itemList = itemRepository.findByDeleteFlagOrderByInsertDateDesc(Constant.NOT_DELETED);


		if(itemList == null){
			return "item/list/item_list";
		}

		model.addAttribute("sort", 1);
		return paging(itemList, index, model);
	}
	/**
	 * @author 伊藤
	 * 商品を売れ筋順に表示取得する
	 */
	@RequestMapping(path="/item/list/2/{index}")
	public String showItemsOrderByPopular(@PathVariable int index, Model model){
		List<Item> itemList = itemRepository.findByDeleteFlagOrderByInsertDateDesc(Constant.NOT_DELETED);

		//商品がない場合
		if(itemList == null){
			return "index";
		}

		//リストから注文履歴のないものを除外
		for(int i = (itemList.size() - 1); i >= 0; i--){
			if(itemList.get(i).getOrderItemsList().size() == 0){
				itemList.remove(i);
			}
		}

		//OrderItemBeanリストの作成
		List<OrderItemBean> orderItems = new ArrayList<OrderItemBean>();
		for(Item item: itemList){
			OrderItemBean orderItemBean = new OrderItemBean();
			orderItemBean.setId(item.getId());
			orderItemBean.setName(item.getName());
			orderItemBean.setPrice(item.getPrice());
			orderItemBean.setImage(item.getImage());

			Integer sum = 0;
			for(OrderItem orderItem: item.getOrderItemsList()){
				sum += orderItem.getQuantity();
			}
			orderItemBean.setSubtotal(sum);

			orderItems.add(orderItemBean);
		}

		//注文数の多い順にソート
		for (int j = 0; j < orderItems.size(); j++) {
			for (int k = 1; k < (orderItems.size() - j); k++) {
				if (Integer.compare(orderItems.get(k).getSubtotal(), orderItems.get(k - 1).getSubtotal()) > 0) {
					orderItems = exChange(orderItems, k);
				}
			}
		}

		//ページングメソッドに渡すため、アイテムリストに移し変え
		itemList.clear();
		for(OrderItemBean oib : orderItems){
			Item item = new Item();
			item.setId(oib.getId());
			item.setName(oib.getName());
			item.setPrice(oib.getPrice());
			item.setCategory(itemRepository.findOne(oib.getId()).getCategory());
			item.setImage(oib.getImage());
			itemList.add(item);
		}

		model.addAttribute("sort", 2);
		return paging(itemList, index, model);
	}
	/**
	 * 詳細検索用コントローラー
	 * @author 伊藤
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(path = "/items/detailSearch/{index}")
	public String detailSearch(@PathVariable int index, @ModelAttribute SearchForm form, Model model){
		model.addAttribute("searchMethod", "detail");

		List<Item> itemList = null;
		if(form.isKeyword()){
			String searchWord = "%" + form.getKeyword1() + "%";

			//全条件での検索
			if(form.isCategory() && form.isPrice()){
				//上限価格のみ
				if(form.getPriceMin() == null || form.getPriceMin() <= 0){
					itemList = itemRepository.findByKeywordAndCategoryAndTopPrice(searchWord, form.getId(), form.getPriceMax());
				//下限価格のみ
				}else if(form.getPriceMax() == null || form.getPriceMax() <= 0){
					itemList = itemRepository.findByKeywordAndCategoryAndBottomPrice(searchWord, form.getId(), form.getPriceMin());
				//中間検索
				}else{
					itemList = itemRepository.findByKeywordAndCategoryAndPriceBetween(searchWord, form.getId(), form.getPriceMin(), form.getPriceMax());
				}
			//キーワードとカテゴリーでの検索
			}else if(form.isCategory()){
				itemList = itemRepository.findByKeywordLikeAndCategory(searchWord, form.getId());
			//キーワードと価格での検索
			}else if(form.isPrice()){
				//上限価格のみ
				if(form.getPriceMin() == null || form.getPriceMin() <= 0){
					itemList = itemRepository.findByKeywordAndTopPrice(searchWord, form.getPriceMax());
				//下限価格のみ
				}else if(form.getPriceMax() == null || form.getPriceMax() <= 0){
					itemList = itemRepository.findByKeywordAndBottomPrice(searchWord, form.getPriceMin());
				//中間検索
				}else{
					itemList = itemRepository.findByKeywordAndPriceBetween(searchWord, form.getPriceMin(), form.getPriceMax());
				}
			}
		//カテゴリーと価格での検索
		}else if(form.isCategory() && form.isPrice()){
			//上限価格のみ
			if(form.getPriceMin() == null || form.getPriceMin() <= 0){
				itemList = itemRepository.findByCategoryAndTopPrice(form.getId(), form.getPriceMax());
			//下限価格のみ
			}else if(form.getPriceMax() == null || form.getPriceMax() <= 0){
				itemList = itemRepository.findByCategoryAndBottomPrice(form.getId(), form.getPriceMin());
			//中間検索
			}else{
				itemList = itemRepository.findByCategoryAndPriceBetween(form.getId(), form.getPriceMin(), form.getPriceMax());
			}
		}else{
			if(session.getAttribute("search") != null){
				itemList = (List<Item>)session.getAttribute("search");
			}
		}

		if(form.isKeyword()){
			model.addAttribute("checkKeyword", true);
		}
		if(form.isCategory()){
			model.addAttribute("checkCategory", true);
		}
		if(form.isPrice()){
			model.addAttribute("checkPrice", true);
		}

		model.addAttribute("keyword", form.getKeyword1());
		model.addAttribute("id", form.getId());
		model.addAttribute("priceMin", form.getPriceMin());
		model.addAttribute("priceMax", form.getPriceMax());

		session.setAttribute("search", itemList);

		return paging(itemList, index, model);
	}

	/**
	 * @author AyeAyeHan
	 * 価格昇順で検索処理メソッド
	 */
	@RequestMapping(path="/item/list/3/{index}")
	public String idSearchAsc(@PathVariable int index, Model model){
		model.addAttribute("sort", 3);
		return paging(itemRepository.findAllPriceAsc(), index, model);
	}
	/**
	 * @author AyeAyeHan
	 * 価格降順で検索処理メソッド
	 */
	@RequestMapping(path="/item/list/4/{index}")
	public String idSearchDesc(@PathVariable int index,Model model){
		model.addAttribute("sort", 4);
		return paging(itemRepository.findAllPriceDesc(), index, model);
	}

	/**
	 * @author 岡崎晴
	 * キーワード検索の結果を表示するメソッド
	 */
	@RequestMapping(path="/items/keywordSearch/{index}")
	public String keywordSearch(@PathVariable int index, String keyword,Model model) {
		String searchWord;
		model.addAttribute("searchMethod", "keyword");

		if (keyword != null && keyword.length() > 0) {
			searchWord = "%" + keyword + "%";
			model.addAttribute("keyword", keyword);
			if(itemRepository.findByNameQuery(searchWord).isEmpty()){
				int[] link = {1};

				model.addAttribute("items", null);
				model.addAttribute("link", link);
				model.addAttribute("index", index);
				return "item/list/item_list";
			}else{
				return paging(itemRepository.findByNameQuery(searchWord), index, model);
			}
		} else {
			model.addAttribute("searchMethod", "keywordNull");
			searchWord = "%";
			return paging(itemRepository.findByNameQuery(searchWord), index, model);

		}

	}

	/**
	 * @author 渡邊悠希
	 * カテゴリ検索の結果を表示するメソッド
	 */
	@RequestMapping(path="/items/categorySearch/{index}")
	public String CategorySearch(@PathVariable int index, CategoryForm form, Model model) {
		Category category = categoryRepository.findOne(form.getId());
		model.addAttribute("id", form.getId());
		model.addAttribute("name", category.getName());
		model.addAttribute("searchMethod", "category");

		List<Item> itemList = category.getItemList();
		for(int i = itemList.size() -1; i >= 0; i--){
			if(itemList.get(i).getDeleteFlag() == 1){
				itemList.remove(i);
			}
		}
		return paging(itemList, index, model);
	}

	/**
	 * @author 佐々木
	 * 商品の詳細を表示するメソッド
	 */
	@RequestMapping(path = "/goodsDtails/{id}")
	public String goodsDtails(@PathVariable Integer id, Model model){
		model.addAttribute("item", itemRepository.findOne(id));
		return "item/detail/item_detail_user";
	}

	/**
	 * @author 佐々木
	 * 値段が1000円未満の商品を表示するメソッド
	 */
	@RequestMapping(path="/items/searchWithQuery/{index}")
	public String searchWithQuery(@PathVariable int index, Model model){
		model.addAttribute("searchMethod", "price");
		model.addAttribute("searchPath", "searchWithQuery");
		return paging(itemRepository.findByPriceQuery(), index, model);
	}

	/**
	 * @author 佐々木
	 * 値段が1000円以上2000円未満の商品を表示するメソッド
	 */
	@RequestMapping(path="/items/searchWith1Query/{index}")
	public String searchWith1Query(@PathVariable int index, Model model){
		model.addAttribute("searchMethod", "price1");
		model.addAttribute("searchPath", "searchWith1Query");
		return paging(itemRepository.findByPrice1Query(), index, model);
	}

	/**
	 * @author 佐々木
	 * 値段が2000円以上3000円未満の商品を表示するメソッド
	 */
	@RequestMapping(path="/items/searchWith2Query/{index}")
	public String searchWith2Query(@PathVariable int index, Model model){
		model.addAttribute("searchMethod", "price2");
		model.addAttribute("searchPath", "searchWith2Query");
		return paging(itemRepository.findByPrice2Query(), index, model);
	}

	/**
	 * @author 佐々木
	 * 値段が3000円以上10000円未満の商品を表示するメソッド
	 */
	@RequestMapping(path="/items/searchWith3Query/{index}")
	public String searchWith3Query(@PathVariable int index, Model model){
		model.addAttribute("searchMethod", "price3");
		model.addAttribute("searchPath", "searchWith3Query");
		return paging(itemRepository.findByPrice3Query(), index, model);
	}

	/**
	 * @author 佐々木
	 * (追)値段が10000円以上の商品を表示するメソッド
	 */
	@RequestMapping(path="/items/searchWith4Query/{index}")
	public String searchWith4Query(@PathVariable int index, Model model){
		model.addAttribute("searchMethod", "price4");
		model.addAttribute("searchPath", "searchWith4Query");
		return paging(itemRepository.findByPrice4Query(), index, model);
	}

	/**
	 * 商品リストの順序入れ替え用
	 *
	 * @author 伊藤
	 * @param items 商品リスト<OrederItemBean>
	 * @param num, num-1 入れ替えるオブジェクトのリスト内インデックス
	 * @return items 順序を入れ替えた商品リスト
	 */
	private List<OrderItemBean> exChange(List<OrderItemBean> items, int num){
		OrderItemBean temp = items.get(num);
		items.set(num, items.get(num - 1));
		items.set(num - 1, temp);
		return items;
	}

	/**
	 * ページング用
	 *
	 * @author 伊藤
	 */
	private String paging(List<Item> itemList, int index, Model model){
		model.addAttribute("index", index);
		if(itemList == null){
			model.addAttribute("items", null);
			return "item/list/item_list";
		}
		int totalItem = itemList.size();
		if(totalItem == 0){
			model.addAttribute("items", null);
			return "item/list/item_list";
		}

		//@param showNum ページに表示する商品数
		int showNum;
		if(totalItem % 10 == 0){
			showNum = 10;
		}else{
			showNum = totalItem % 10;
		}

		int underNum = (index - 1) * 10;
		List<ItemBean> items = new ArrayList<ItemBean>();
		//最後のページかで場合わけ
		if(index == (totalItem / 10) + 1){
			for(int i = 0; i < showNum; i++){
				Item item = itemList.get(underNum + i);
				ItemBean itemBean = new ItemBean();
				itemBean.setId(item.getId());
				itemBean.setName(item.getName());
				itemBean.setPrice(item.getPrice());
				itemBean.setCategoryName(item.getCategory().getName());
				itemBean.setImage(item.getImage());
				items.add(itemBean);
			}
		}else{
			for(int i = 0; i < 10; i++){
				Item item = itemList.get(underNum + i);
				ItemBean itemBean = new ItemBean();
				itemBean.setId(item.getId());
				itemBean.setName(item.getName());
				itemBean.setPrice(item.getPrice());
				itemBean.setCategoryName(item.getCategory().getName());
				itemBean.setImage(item.getImage());
				items.add(itemBean);
			}
		}

		//下に表示するリンクの数値配列
		int[] link;
		if(totalItem % 10 == 0){
			link = new int[totalItem / 10];
			for(int j = 1; j <= (totalItem / 10); j++){
				link[j - 1] = j;
			}
		}else{
			link = new int[(totalItem / 10) + 1];
			for(int j = 1; j <= (totalItem / 10) + 1; j++){
				link[j - 1] = j;
			}
		}

		model.addAttribute("items", items);
		model.addAttribute("link", link);
		model.addAttribute("index", index);
		return "item/list/item_list";
	}
}
