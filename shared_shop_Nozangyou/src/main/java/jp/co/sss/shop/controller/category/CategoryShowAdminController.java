package jp.co.sss.shop.controller.category;

import java.util.List;

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

@Controller
public class CategoryShowAdminController {

    @Autowired
    CategoryRepository categoryRepository;

    @RequestMapping(path = "/category/list", method = RequestMethod.GET)
    public String showCategoryList(Model model, @ModelAttribute CategoryForm form) {

        List<Category> categoryList = categoryRepository.findByDeleteFlagOrderByInsertDateDesc(0);

        model.addAttribute("categories", categoryList);

        return "category/list/category_list";
    }

    @RequestMapping(path = "/category/detail", method = RequestMethod.GET)
    public String showCategory(Model model, @ModelAttribute CategoryForm form) {

        Category category = categoryRepository.findOne(form.getId());

        CategoryBean categoryBean = new CategoryBean();

        // Userエンティティの各フィールドの値をUserBeanにコピー
        BeanUtils.copyProperties(category, categoryBean);

        model.addAttribute("category", categoryBean);

        return "/category/detail/category_detail";
    }


}
