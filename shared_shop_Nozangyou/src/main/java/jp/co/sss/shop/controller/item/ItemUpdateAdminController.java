package jp.co.sss.shop.controller.item;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import jp.co.sss.shop.bean.ItemBean;
import jp.co.sss.shop.entity.Category;
import jp.co.sss.shop.entity.Item;
import jp.co.sss.shop.form.ItemForm;
import jp.co.sss.shop.repository.CategoryRepository;
import jp.co.sss.shop.repository.ItemRepository;
import jp.co.sss.shop.util.BeanCopy;
import jp.co.sss.shop.util.Constant;

@Controller
public class ItemUpdateAdminController {
	@Autowired
	ItemRepository itemRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@RequestMapping(path = "/item/update/input", method = RequestMethod.POST)
	public String updateInput(Integer id, Model model, @ModelAttribute ItemForm itemForm) {
		Item item = itemRepository.findOne(id);
		ItemBean itemBean = BeanCopy.copyEntityToBean(item);
		model.addAttribute("item", itemBean);
		return "item/update/item_update_input";
	}

	@RequestMapping(path = "/item/update/check", method = RequestMethod.POST)
	public String updateCheck(@Valid @ModelAttribute ItemForm form, BindingResult result, Model model) {
		if(result.hasErrors()){
			model.addAttribute("item", form);
			return "item/update/item_update_input";
		}

		if (form.getImageFile().getSize() > 0) {
			MultipartFile file = form.getImageFile();

			// アップロード対象のファイル名を取得
			String imageName = file.getOriginalFilename();

			// 現在の日時を「yyyyMMddhhmmss」形式の文字列として取得
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
			String date = dateFormat.format(new Date());

			// ファイルのアップロード先を指定
			imageName = date + "_" + imageName;
			File uploadPath = new File(Constant.FILE_UPLOAD_PATH, imageName);

			try {
				// 指定されたファイルを一時的にアップロード
				file.transferTo(uploadPath);
			} catch (Exception e) {
				e.printStackTrace();
				return "error";
			}

			// 一時的にアップロードしたファイルの名前をFormクラスにセット
			form.setImage("img/" + imageName);
		} else {
			// 商品画像が未入力の場合、登録済みの画像ファイルを取得する
			Item item = itemRepository.findOne(Integer.parseInt(form.getId()));
			form.setImage(item.getImage());
		}

		// 選択したカテゴリの名前をFormクラスにセット
		Category category = categoryRepository.findOne(Integer.parseInt(form.getCategoryId()));
		form.setCategoryName(category.getName());

		return "item/update/item_update_check";
	}

	@RequestMapping(path = "/item/update/complete", method = RequestMethod.POST)
	public String updateComplete(@ModelAttribute ItemForm form) {
		// Formクラス内の各フィールドの値をエンティティにコピー
		Item item = BeanCopy.copyFormToEntity(form);
		item.setDeleteFlag(Constant.NOT_DELETED);

		itemRepository.save(item);

		return "item/update/item_update_complete";
	}

	@RequestMapping(path = "/item/update/input/back", method = RequestMethod.POST)
    public String back(Model model, @ModelAttribute ItemForm form) {
		// ItemFormクラスの各フィールドの値をItemBeanクラスにコピー
        ItemBean itemBean = BeanCopy.copyFormToBean(form);

        model.addAttribute("item", itemBean);
        return "item/update/item_update_input";
    }
}
