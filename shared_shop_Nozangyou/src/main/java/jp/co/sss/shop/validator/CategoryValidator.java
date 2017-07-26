package jp.co.sss.shop.validator;

import javax.servlet.http.HttpSession;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import jp.co.sss.shop.annotation.CategoryCheck;
import jp.co.sss.shop.entity.Category;
import jp.co.sss.shop.repository.CategoryRepository;
import jp.co.sss.shop.util.Constant;

/**
 * カテゴリの重複登録検証クラス
 *
 * @author System Shared
 *
 */
public class CategoryValidator implements ConstraintValidator<CategoryCheck, Object> {
	private String name;
	private String id;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	HttpSession session;

	@Override
	public void initialize(CategoryCheck annotation) {
		this.name = annotation.fieldName();
		this.id = annotation.fieldId();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		BeanWrapper beanWrapper = new BeanWrapperImpl(value);

		String name = (String) beanWrapper.getPropertyValue(this.name);
		Integer id = (Integer) beanWrapper.getPropertyValue(this.id);
		Category category = categoryRepository.findByNameAndDeleteFlag(name, Constant.NOT_DELETED);

		if (category == null || category.getId() == id) {
			return true;
		} else {
			return false;
		}
	}
}
