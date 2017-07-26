package jp.co.sss.shop.controller.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.sss.shop.bean.ItemBean;
import jp.co.sss.shop.entity.Item;
import jp.co.sss.shop.repository.ItemRepository;
import jp.co.sss.shop.util.Constant;
import jp.co.sss.shop.util.BeanCopy;

@Controller
public class ItemDeleteAdminController {
	@Autowired
	ItemRepository itemRepository;

	@RequestMapping(path = "/item/delete/check", method = RequestMethod.POST)
	public String deleteCheck(Integer id, Model model) {
		Item item = itemRepository.findOne(id);
		ItemBean itemBean = BeanCopy.copyEntityToBean(item);
		model.addAttribute("item", itemBean);
		return "item/delete/item_delete_check";
	}

	@RequestMapping(path = "/item/delete/complete", method = RequestMethod.POST)
	public String deleteComplete(Integer id) {

		Item item = itemRepository.findOne(id);
		item.setDeleteFlag(Constant.DELETED);
        itemRepository.save(item);

		return "item/delete/item_delete_complete";
	}
}
