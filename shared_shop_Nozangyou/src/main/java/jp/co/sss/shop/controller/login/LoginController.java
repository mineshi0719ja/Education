package jp.co.sss.shop.controller.login;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.sss.shop.bean.UserBean;
import jp.co.sss.shop.form.LoginForm;
import jp.co.sss.shop.repository.UserRepository;

@Controller
public class LoginController {
	@Autowired
	UserRepository userRepository;
	@Autowired
	HttpSession session;

	@RequestMapping(path = "/login", method = RequestMethod.GET)
	public String login(@ModelAttribute LoginForm form) {
		if(session.getAttribute("user") != null){
			if(((UserBean)(session.getAttribute("user"))).getAuthority() != 2){
				return "admin_menu";
			}
		}
		return "login";
	}

	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public String doLogin(@Valid @ModelAttribute LoginForm form, BindingResult result, HttpSession session) {
		if (result.hasErrors()) {
			return login(form);
		} else {
			Integer authority = ((UserBean) session.getAttribute("user")).getAuthority();
			if(authority.intValue() == 2) {
				// 一般会員ログインした場合、商品一覧画面に遷移
				return "redirect:/item/list/1/1";
			} else {
				// 運用管理者、もしくはシステム管理者としてログインした場合、管理者用メニュー画面に遷移
				return "admin_menu";
			}
		}
	}
}
