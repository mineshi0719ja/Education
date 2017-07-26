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

import jp.co.sss.shop.entity.User;
import jp.co.sss.shop.form.UserForm;
import jp.co.sss.shop.repository.UserRepository;

@Controller
public class UserRegistAdminController {

	@Autowired
	UserRepository userRpository;

	@RequestMapping(path = "/user/regist/input/admin", method = RequestMethod.GET)
	public String registInput(@ModelAttribute UserForm form) {

		return "user/regist/user_regist_input_admin";
	}

	@RequestMapping(path = "/user/regist/check/admin", method = RequestMethod.POST)
	public String registCheck(@Valid @ModelAttribute UserForm form, BindingResult result) {
		if (result.hasErrors()) {
			return registInput(form);
		}

		return "user/regist/user_regist_check_admin";
	}

	@RequestMapping(path = "/user/regist/complete/admin", method = RequestMethod.POST)
	public String registComplete(@ModelAttribute UserForm form, HttpSession session) {
		User user = new User();

		BeanUtils.copyProperties(form, user);

		userRpository.save(user);

		return "user/regist/user_regist_complete_admin";
	}
}
