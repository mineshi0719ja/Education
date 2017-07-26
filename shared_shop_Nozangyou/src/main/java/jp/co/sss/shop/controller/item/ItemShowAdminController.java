package jp.co.sss.shop.controller.item;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sss.shop.bean.ItemBean;
import jp.co.sss.shop.entity.Item;
import jp.co.sss.shop.repository.ItemRepository;
import jp.co.sss.shop.util.BeanCopy;
import jp.co.sss.shop.util.Constant;

@Controller
public class ItemShowAdminController {
	@Autowired
	ItemRepository itemRepository;

	@RequestMapping(path = "/item/list/admin")
	public String showItem(Model model) {
		// 商品情報を全件検索(新着順)
		List<Item> itemList = itemRepository.findByDeleteFlagOrderByInsertDateDesc(Constant.NOT_DELETED);

		// エンティティ内の検索結果をJavaBeansにコピー
		List<ItemBean> itemBeanList = BeanCopy.copyEntityToItemBean(itemList);

		model.addAttribute("items", itemBeanList);

		return "item/list/item_list_admin";
	}

	@RequestMapping(path = "/item/detail/admin/{id}")
	public String showItem(@PathVariable int id, Model model, HttpSession session) {

		Item item = itemRepository.findOne(id);

		ItemBean itemBean = new ItemBean();

		// Userエンティティの各フィールドの値をUserBeanにコピー
		BeanUtils.copyProperties(item, itemBean);

		itemBean.setCategoryName(item.getCategory().getName());
		model.addAttribute("item", itemBean);

		return "item/detail/item_detail_admin";
	}
}
