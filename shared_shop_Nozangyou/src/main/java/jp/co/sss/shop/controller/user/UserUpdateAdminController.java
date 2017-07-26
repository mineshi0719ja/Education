package jp.co.sss.shop.controller.user;

import java.sql.Date;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.sss.shop.bean.UserBean;
import jp.co.sss.shop.entity.User;
import jp.co.sss.shop.form.UserForm;
import jp.co.sss.shop.repository.UserRepository;

@Controller
public class UserUpdateAdminController {

    @Autowired
    UserRepository userRpository;

	@RequestMapping(path = "/user/update/input/admin", method = RequestMethod.POST)
	public String updateInput(Model model, @ModelAttribute UserForm form) {
	    User user = userRpository.getOne(form.getId());
	    UserBean userBean = new UserBean();
	    BeanUtils.copyProperties(user, userBean);
	    model.addAttribute("user", userBean);
		return "user/update/user_update_input_admin";
	}

	@RequestMapping(path = "/user/update/check/admin", method = RequestMethod.POST)
	public String updateCheck(Model model,@Valid @ModelAttribute UserForm form, BindingResult result) {

	    if (result.hasErrors()){
            return updateInputBack(model,form);
        }

	   return "user/update/user_update_check_admin";
	}

	@RequestMapping(path = "/user/update/complete/admin", method = RequestMethod.POST)
	public String updateComplete(Model model, @ModelAttribute UserForm form) {

	    User user  = userRpository.findOne(form.getId());

	    Integer deleteFlag = user.getDeleteFlag();
	    Date insertDate = user.getInsertDate();

	    BeanUtils.copyProperties(form, user);

	    user.setDeleteFlag(deleteFlag);
	    user.setInsertDate(insertDate);

	    userRpository.save(user);

	    model.addAttribute("userId",form.getId());

	    return "user/update/user_update_complete_admin";
	}

	@RequestMapping(path = "/user/update/input/back/admin", method = RequestMethod.POST)
	public String updateInputBack(Model model, @ModelAttribute UserForm form) {
	    UserBean userBean = new UserBean();
	    BeanUtils.copyProperties(form, userBean);
	    model.addAttribute("user", userBean);
	    return "user/update/user_update_input_admin";
	}
}
