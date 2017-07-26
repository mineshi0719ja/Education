package jp.co.sss.shop.controller.user;

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
public class UserDeleteAdminController {

    @Autowired
    UserRepository userRpository;

    @RequestMapping(path = "/user/delete/check/admin", method = RequestMethod.POST)
    public String deleteCheck(Model model, @ModelAttribute UserForm form) {
        User user = userRpository.findOne(form.getId());

        UserBean userBean = new UserBean();

        // Userエンティティの各フィールドの値をUserBeanにコピー
        BeanUtils.copyProperties(user, userBean);

        model.addAttribute("user", userBean);

       return "user/delete/user_delete_check_admin";
    }

    @RequestMapping(path = "/user/delete/complete/admin", method = RequestMethod.POST)
    public String deleteComplete(@ModelAttribute UserForm form, HttpSession session) {

        User user = userRpository.findOne(form.getId());
        user.setDeleteFlag(1);
        userRpository.save(user);

        return "user/delete/user_delete_complete_admin";
    }

}
