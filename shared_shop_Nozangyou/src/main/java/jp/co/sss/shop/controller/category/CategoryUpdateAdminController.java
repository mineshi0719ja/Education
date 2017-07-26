package jp.co.sss.shop.controller.category;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
public class CategoryUpdateAdminController {

    @Autowired
    CategoryRepository categoryRepository;

    @RequestMapping(path = "/category/update/input", method = RequestMethod.POST)
    public String updateInput(Model model, @ModelAttribute CategoryForm form) {
        Category category = categoryRepository.getOne(form.getId());
        CategoryBean categoryBean = new CategoryBean();
        BeanUtils.copyProperties(category, categoryBean);
        model.addAttribute("category", categoryBean);
        return "category/update/category_update_input";
    }

    @RequestMapping(path = "/category/update/check", method = RequestMethod.POST)
    public String updateCheck(Model model,@Valid @ModelAttribute CategoryForm form, BindingResult result) {

        if (result.hasErrors()){
            return updateInputBack(model,form);
        }

       return "category/update/category_update_check";
    }

    @RequestMapping(path = "/category/update/complete", method = RequestMethod.POST)
    public String updateComplete(Model model, @ModelAttribute CategoryForm form, HttpSession session) {

        Category category = categoryRepository.getOne(form.getId());

        Integer deleteFlag = category.getDeleteFlag();
        Date insertDate = category.getInsertDate();

        BeanUtils.copyProperties(form, category);

        category.setDeleteFlag(deleteFlag);
        category.setInsertDate(insertDate);

        categoryRepository.save(category);

        model.addAttribute("categoryId",form.getId());

		// カテゴリ情報を全件検索
		List<Category> categoryList = categoryRepository.findByDeleteFlagOrderByInsertDateDesc(Constant.NOT_DELETED);

		// エンティティ内の検索結果をJavaBeansにコピー
		List<CategoryBean> categoryBeanList = BeanCopy.copyEntityToCategoryBean(categoryList);

		// セッションスコープ中に保存されたカテゴリ一覧の情報を更新
		session.setAttribute("categories", categoryBeanList);

        return "category/update/category_update_complete";
    }

    @RequestMapping(path = "/category/update/input/back", method = RequestMethod.POST)
    public String updateInputBack(Model model, @ModelAttribute CategoryForm form) {
        CategoryBean categoryBean = new CategoryBean();
        BeanUtils.copyProperties(form, categoryBean);
        model.addAttribute("category", categoryBean);

        return "category/update/category_update_input";
    }
}
