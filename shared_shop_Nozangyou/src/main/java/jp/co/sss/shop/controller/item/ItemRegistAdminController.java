package jp.co.sss.shop.controller.item;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
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
public class ItemRegistAdminController {
	@Autowired
	ServletContext context;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	public ItemRepository itemRepository;

	@RequestMapping(path = "/item/regist/input")
	public String registInput(@ModelAttribute ItemForm form) {
		return "item/regist/item_regist_input";
	}

	@RequestMapping(path = "/item/regist/check", method = RequestMethod.POST)
	public String registCheck(@Valid @ModelAttribute ItemForm form, BindingResult result, HttpServletRequest request) {
		if(result.hasErrors()){
			return registInput(form);
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
			form.setImage("img/"+imageName);
		}

		// 選択したカテゴリの名前をFormクラスにセット
		Category category = categoryRepository.findOne(Integer.parseInt(form.getCategoryId()));
		form.setCategoryName(category.getName());

		return "item/regist/item_regist_check";
	}

	@RequestMapping(path = "/item/regist/complete", method = RequestMethod.POST)
	public String registComplete(@ModelAttribute ItemForm form) {
		// Formクラス内の各フィールドの値をエンティティにコピー
		Item item = BeanCopy.copyFormToEntity(form);

		itemRepository.save(item);

		return "item/regist/item_regist_complete";
	}
}
