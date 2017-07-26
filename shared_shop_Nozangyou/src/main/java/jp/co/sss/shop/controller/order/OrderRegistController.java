package jp.co.sss.shop.controller.order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.sss.shop.bean.BasketBean;
import jp.co.sss.shop.bean.OrderItemBean;
import jp.co.sss.shop.bean.UserBean;
import jp.co.sss.shop.entity.Item;
import jp.co.sss.shop.entity.Order;
import jp.co.sss.shop.entity.OrderItem;
import jp.co.sss.shop.entity.User;
import jp.co.sss.shop.form.AddressForm;
import jp.co.sss.shop.form.OrderForm;
import jp.co.sss.shop.repository.ItemRepository;
import jp.co.sss.shop.repository.OrderItemRepository;
import jp.co.sss.shop.repository.OrderRepository;
import jp.co.sss.shop.repository.UserRepository;
import jp.co.sss.shop.util.DateCheck;



@Controller
public class OrderRegistController {
	// TODO ここに処理を追加してください

	@Autowired
	UserRepository userRepository;

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	HttpSession session;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	OrderItemRepository orderItemRepository;

	/**
	 * @author 渡邊悠希
	 * お届け先入力画面へ遷移するメソッド
	 */
	@RequestMapping(path="/adress_input")
	public String AddressInput(@ModelAttribute AddressForm addressForm, Model model) {
		int LoginId = ((UserBean) session.getAttribute("user")).getId();
		model.addAttribute("LoginUser",userRepository.findOne(LoginId));
		return "order/regist/address_input";
	}

	/**
	 * @author 渡邊悠希
	 * 支払い方法入力画面へ遷移するメソッド
	 */
	@RequestMapping(path="/order/regist/payment/input",method=RequestMethod.POST)
	public String PaymentInput(@Valid @ModelAttribute AddressForm addressForm,BindingResult result
									,Model model) {

		if(result.hasErrors()) {
			return AddressInput(addressForm, model);
		}

		UserBean userBean = (UserBean)session.getAttribute("user");
		User user = userRepository.findOne(userBean.getId());

		OrderForm orderForm = new OrderForm();
		orderForm.setPostalCode(addressForm.getPostalCode());
		orderForm.setAddress(addressForm.getAddress());
		orderForm.setName(addressForm.getName());
		orderForm.setPhoneNumber(addressForm.getPhoneNumber());
		orderForm.setCardNo(user.getCardNo());
		orderForm.setCardPeriodMonth(user.getCardPeriodMonth());
		orderForm.setCardPeriodYear(user.getCardPeriodYear());
		orderForm.setCardSecurityCode(user.getCardSecurityCode());
		orderForm.setCardType(user.getCardType());
		model.addAttribute("orderForm", orderForm);

		String[] month = {"01","02","03","04","05","06","07","08","09","10","11","12"};
		session.setAttribute("months", month);
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssZ");
		String stringDate = sdf.format(new Date());
		String stringYear = stringDate.substring(0, 2);
		int year = Integer.parseInt(stringYear);
		String[] years = new String[20];
		for(int i = 0; i < 20; i++){
			if(year + i < 10){
				years[i] = "0" + (year + 1);
			}else{
				years[i] = "" + (year + i);
			}
		}
		session.setAttribute("years", years);

		return "order/regist/payment_input";
	}

	/**
	 * @author 渡邊悠希
	 * 注文確認画面へ遷移するメソッド
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(path="/order/regist/order/check",method=RequestMethod.POST)
	public String OrderCheck(@Valid @ModelAttribute OrderForm orderForm,BindingResult result, Model model) {

		if(orderForm.getPayMethod() == 1) {
			boolean errorFlag = false;
			if(result.hasErrors()) {
				errorFlag = true;
			}
			if(DateCheck.isBeforeDate(orderForm.getCardPeriodYear(), orderForm.getCardPeriodMonth())){
				model.addAttribute("dateError", "有効期限が切れています");
				errorFlag = true;
			}

			if(errorFlag){
				return "order/regist/payment_input";
			}
		}

		//OrderBeanリスト作成、合計金額
		List<OrderItemBean> orderItemBeans = new ArrayList<OrderItemBean>();
		int total = 0;
		for(BasketBean basketBean: (List<BasketBean>)(session.getAttribute("basket"))){
			OrderItemBean orderItemBean = new OrderItemBean();
			Item item = itemRepository.findOne(basketBean.getId());
			orderItemBean.setId(basketBean.getId());
			orderItemBean.setName(basketBean.getName());
			orderItemBean.setPrice(item.getPrice());
			orderItemBean.setImage(item.getImage());
			orderItemBean.setOrderNum(basketBean.getOrderNum());
			orderItemBean.setSubtotal(item.getPrice() * basketBean.getOrderNum());
			orderItemBeans.add(orderItemBean);

			total += orderItemBean.getSubtotal();
		}

		//カード番号を下4桁だけ表示するように設定する
		StringBuilder stringBuilder = new StringBuilder(orderForm.getCardNo());
		StringBuilder cardNo = stringBuilder.replace(0, 12, "************");
		model.addAttribute("cardNo", cardNo);

		model.addAttribute("items", orderItemBeans);
		model.addAttribute("total", total);
		return "order/regist/order_check";
	}

	/**
	 * @author 渡邊悠希
	 * 注文完了画面へ遷移するメソッド
	 */
	@RequestMapping(path="/order/regist/order/complete",method=RequestMethod.POST)
	public String OrderComplete(@ModelAttribute OrderForm orderForm,Model model) {
		if(session.getAttribute("basket") == null){
			return "redirect:/item/list/1/1";
		}

		//ログインしているユーザーの会員IDを受け取る
		Integer userId = ((UserBean) session.getAttribute("user")).getId();

		Order order = new Order();

		//ログインユーザーの会員IDが入っているレコードを取得する
		User user = userRepository.findOne(userId.intValue());


		//注文テーブルに情報を登録
		String postalCode = orderForm.getPostalCode();
		if(!(postalCode.substring(3, 3).equals("-"))){
			postalCode = postalCode.substring(0, 3) + "-" + postalCode.substring(3);
		}
		order.setPostalCode(postalCode);
		order.setAddress(orderForm.getAddress());
		order.setName(orderForm.getName());
		order.setPhoneNumber(orderForm.getPhoneNumber());
		order.setPayMethod(orderForm.getPayMethod());
		order.setUser(user);
		order.setDeleteFlag(0);
		Order orderId = orderRepository.save(order);


		//会員テーブルにカードに関する情報を登録
		if(orderForm.getPayMethod()==1){
			user.setCardNo(orderForm.getCardNo());
			user.setCardPeriodMonth(orderForm.getCardPeriodMonth());
			user.setCardPeriodYear(orderForm.getCardPeriodYear());
			user.setCardSecurityCode(orderForm.getCardSecurityCode());
			user.setCardType(orderForm.getCardType());
			userRepository.save(user);
		}

		//注文商品テーブルに注文情報を登録
		for(BasketBean basketBean: (List<BasketBean>)(session.getAttribute("basket"))){
			Item item = itemRepository.findOne(basketBean.getId());
			OrderItem orderItem = new OrderItem();
			orderItem.setQuantity(basketBean.getOrderNum());
			orderItem.setItem(item);
			item.setStock(item.getStock() - basketBean.getOrderNum());
			orderItem.setOrder(orderId);

			orderItemRepository.save(orderItem);
			itemRepository.save(item);
		}

		session.removeAttribute("basket");
		return "order/regist/order_complete";
	}
}
