package jp.co.sss.shop.interceptor;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.sss.shop.bean.CategoryBean;
import jp.co.sss.shop.entity.Category;
import jp.co.sss.shop.repository.CategoryRepository;
import jp.co.sss.shop.util.BeanCopy;
import jp.co.sss.shop.util.Constant;

/**
 * カテゴリ一覧取得用AOP
 *
 * @author System Shared
 */
@Aspect
@Component
public class CategoryListInterceptor {
	@Autowired
	HttpSession session;

	@Autowired
	CategoryRepository categoryRepository;

	/**
	 * 最新のカテゴリ一覧情報を取得し、セッションスコープに保存
	 *
	 * @param joinPoint
	 *            ジョインポイント
	 */
	@Before("(execution(* jp.co.sss.shop.controller.item.ItemShowCustomerController.*(..)) ||"
			+ " execution(* jp.co.sss.shop.controller.login.LoginController.*(..))) &&"
			+ " @annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public void getCategoryList(JoinPoint joinPoint) {
		if (session.getAttribute("categories") == null) {
			// カテゴリ情報を全件検索
			List<Category> categoryList = categoryRepository
					.findByDeleteFlagOrderByInsertDateDesc(Constant.NOT_DELETED);

			// エンティティ内の検索結果をJavaBeansにコピー
			List<CategoryBean> categoryBeanList = BeanCopy.copyEntityToCategoryBean(categoryList);

			session.setAttribute("categories", categoryBeanList);
		}
	}
}
