package jp.co.sss.shop.controller.user;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.sss.shop.bean.UserBean;
import jp.co.sss.shop.entity.User;
import jp.co.sss.shop.form.UserForm;
import jp.co.sss.shop.repository.UserRepository;

@Controller
public class UserShowAdminController {
	@Autowired
	UserRepository userRepository;

	/**
	 * 会員一覧表示
	 *
	 * @param model
	 *            モデル
	 * @param form
	 *            フォーム
	 * @return 遷移先
	 */
	@RequestMapping(path = "/user/list", method = RequestMethod.GET)
	public String showUserList(Model model, @ModelAttribute UserForm form) {

		List<User> userList = userRepository.findByDeleteFlagOrderByInsertDateDesc(0);

		model.addAttribute("users", userList);

		return "user/list/user_list";
	}

	/**
	 * 会員詳細表示
	 *
	 * @param model
	 *            モデル
	 * @param form
	 *            フォーム
	 * @return 遷移先
	 */
	@RequestMapping(path = "/user/detail/admin", method = RequestMethod.GET)
	public String showUser(Model model, @ModelAttribute UserForm form, HttpSession session) {
		User user = userRepository.findOne(form.getId());

		UserBean userBean = new UserBean();

		// Userエンティティの各フィールドの値をUserBeanにコピー
		BeanUtils.copyProperties(user, userBean);

		model.addAttribute("user", userBean);

		return "/user/detail/user_detail_admin";
	}
}
