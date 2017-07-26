package jp.co.sss.shop.controller.category;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
public class CategoryDeleteAdminController {

	@Autowired
	CategoryRepository categoryRepository;

	@RequestMapping(path = "/category/delete/check", method = RequestMethod.POST)
	public String deleteCheck(Model model, @ModelAttribute CategoryForm form) {
		Category category = categoryRepository.findOne(form.getId());



		CategoryBean categoryBean = new CategoryBean();

		// Userエンティティの各フィールドの値をUserBeanにコピー
		BeanUtils.copyProperties(category, categoryBean);

		model.addAttribute("category", categoryBean);

		return "category/delete/category_delete_check";
	}

	@RequestMapping(path = "/category/delete/complete", method = RequestMethod.POST)
	public String deletetComplete(@ModelAttribute CategoryForm form, HttpSession session, Model model) {

		Category category = categoryRepository.findOne(form.getId());
		model.addAttribute("categoryDelete", category.getItemList());

		if(category.getItemList().size() > 0){

			CategoryBean categoryBean = new CategoryBean();

			// Userエンティティの各フィールドの値をUserBeanにコピー
			BeanUtils.copyProperties(category, categoryBean);

			model.addAttribute("category", categoryBean);

			model.addAttribute("errormessage", "対象のカテゴリに該当する商品が登録されているため削除できません。");
			return "category/delete/category_delete_check";


		}else{
			category.setDeleteFlag(1);
			categoryRepository.save(category);

			// カテゴリ情報を全件検索
			List<Category> categoryList = categoryRepository.findByDeleteFlagOrderByInsertDateDesc(Constant.NOT_DELETED);

			// エンティティ内の検索結果をJavaBeansにコピー
			List<CategoryBean> categoryBeanList = BeanCopy.copyEntityToCategoryBean(categoryList);

			// セッションスコープ中に保存されたカテゴリ一覧の情報を更新
			session.setAttribute("categories", categoryBeanList);

			return "category/delete/category_delete_complete";


		}
	}
}
