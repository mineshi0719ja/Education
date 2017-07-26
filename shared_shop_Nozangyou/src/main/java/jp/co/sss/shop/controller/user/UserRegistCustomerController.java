package jp.co.sss.shop.controller.user;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.sss.shop.bean.UserBean;
import jp.co.sss.shop.entity.User;
import jp.co.sss.shop.form.UserForm;
import jp.co.sss.shop.repository.UserRepository;


@Controller
public class UserRegistCustomerController  {

	@Autowired
	UserRepository userRpository;

	/**
	 * 新規登録入力画面
	 * @author AyeAyeHan
	 *
	 */
	@RequestMapping(path = "/user/regist/input", method = RequestMethod.GET)
	public String registInput(@ModelAttribute UserForm form) {

		return "user/regist/user_regist_input";
	}

	/**
	 * 新規登録確認画面
	 * @author AyeAyeHan
	 *
	 */
	@RequestMapping(path = "/user/regist/check", method = RequestMethod.POST)
	public String registCheck(@Valid @ModelAttribute UserForm form, BindingResult result) {
		if (result.hasErrors()) {
			return registInput(form);
		}

		return "user/regist/user_regist_check";
	}

	/**
	 * 新規登録完了画面(session保持状態でindexページへ)
	 * @author 高野将平
	 *
	 */
	@RequestMapping(path = "/user/regist/complete", method = RequestMethod.POST)
	public String registComplete(@ModelAttribute UserForm form, HttpSession session) {
		User user = new User();
		BeanUtils.copyProperties(form, user);
		userRpository.save(user);

		UserBean userBean = new UserBean();
		userBean.setId(user.getId());
		userBean.setName(user.getName());
		userBean.setAuthority(user.getAuthority());
		session.setAttribute("user", userBean);

		return "user/regist/user_regist_complete";
	}



}