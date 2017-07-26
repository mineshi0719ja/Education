package jp.co.sss.shop.controller.category;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.sss.shop.bean.CategoryBean;
import jp.co.sss.shop.entity.Category;
import jp.co.sss.shop.form.CategoryForm;
import jp.co.sss.shop.repository.CategoryRepository;
import jp.co.sss.shop.util.BeanCopy;
import jp.co.sss.shop.util.Constant;

@Controller
public class CategoryRegistAdminController {

	@Autowired
	CategoryRepository categoryRepository;

	@RequestMapping(path = "/category/regist/input", method = RequestMethod.GET)
	public String registInput(@ModelAttribute CategoryForm form) {
		return "category/regist/category_regist_input";
	}

	@RequestMapping(path = "/category/regist/check", method = RequestMethod.POST)
	public String registCheck(@Valid @ModelAttribute CategoryForm form, BindingResult result) {

		if (result.hasErrors()) {
			return registInput(form);
		}

		return "category/regist/category_regist_check";
	}

	@RequestMapping(path = "/category/regist/complete", method = RequestMethod.POST)
	public String registComplete(@ModelAttribute CategoryForm form, HttpSession session) {

		Category category = new Category();

		BeanUtils.copyProperties(form, category);

		categoryRepository.save(category);

		// カテゴリ情報を全件検索
		List<Category> categoryList = categoryRepository.findByDeleteFlagOrderByInsertDateDesc(Constant.NOT_DELETED);

		// エンティティ内の検索結果をJavaBeansにコピー
		List<CategoryBean> categoryBeanList = BeanCopy.copyEntityToCategoryBean(categoryList);

		// セッションスコープ中に保存されたカテゴリ一覧の情報を更新
		session.setAttribute("categories", categoryBeanList);

		return "category/regist/category_regist_complete";
	}

}
