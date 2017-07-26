package jp.co.sss.shop.controller.order;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.sss.shop.bean.OrderBean;
import jp.co.sss.shop.bean.OrderItemBean;
import jp.co.sss.shop.bean.UserBean;
import jp.co.sss.shop.entity.Order;
import jp.co.sss.shop.entity.OrderItem;
import jp.co.sss.shop.form.OrderShowForm;
import jp.co.sss.shop.repository.OrderRepository;
import jp.co.sss.shop.util.Constant;
import jp.co.sss.shop.util.PriceCalc;

@Controller
public class OrderShowController {

	@Autowired
	private HttpSession session;
	@Autowired
	OrderRepository oRepository;

	/**
	 * 過去の注文履歴の一覧を表示する
	 * @author 伊藤匠真
	 */
	@RequestMapping("/order/{index}")
	public String showOrderList(@PathVariable int index, Model model){
		UserBean userBean = (UserBean)(session.getAttribute("user"));
		if (userBean.getAuthority() != 2) {
			return "redirect:/order/list/admin";
		}

		List<Order> orderList = (List<Order>)oRepository.findByUserIdAndDeleteFlagOrderByInsertDateDesc(((UserBean)(session.getAttribute("user"))).getId(), Constant.NOT_DELETED);
		if(orderList.size() == 0){
			return "order/list/order_list";
		}

		int totalOrder = orderList.size();
		int showNum;
		if(totalOrder % 10 == 0){
			showNum = 10;
		}else{
			showNum = totalOrder % 10;
		}

		int underNum = (index - 1) * 10;
		List<OrderBean> orders = new ArrayList<OrderBean>();
		if(index == (totalOrder / 10) + 1){
			for(int i = 0; i < showNum; i++){
				Order order = orderList.get(underNum + i);
				OrderBean orderBean = new OrderBean();
				orderBean.setId(order.getId());
				orderBean.setInsertDate(order.getInsertDate().toString());
				orderBean.setPayMethod(order.getPayMethod());
				int total = PriceCalc.orderItemPriceTotal(order.getOrderItemsList());
				orderBean.setTotal(total);
				orders.add(orderBean);
			}
		}else{
			for(int i = 0; i < 10; i++){
				Order order = orderList.get(underNum + i);
				OrderBean orderBean = new OrderBean();
				orderBean.setId(order.getId());
				orderBean.setInsertDate(order.getInsertDate().toString());
				orderBean.setPayMethod(order.getPayMethod());
				int total = PriceCalc.orderItemPriceTotal(order.getOrderItemsList());
				orderBean.setTotal(total);
				orders.add(orderBean);
			}
		}



		int[] link;
		if(totalOrder % 10 == 0){
			link = new int[totalOrder / 10];
			for(int j = 1; j <= (totalOrder / 10); j++){
				link[j - 1] = j;
			}
		}else{
			link = new int[(totalOrder / 10) + 1];
			for(int j = 1; j <= (totalOrder / 10) + 1; j++){
				link[j - 1] = j;
			}
		}


		model.addAttribute("orders", orders);
		model.addAttribute("link", link);
		model.addAttribute("index", index);
		return "order/list/order_list";
	}


	/**
	 * 過去の注文履歴の詳細を表示する
	 * @author 岡崎晴
	 */
	@RequestMapping(path = "/order/detail", method = RequestMethod.GET)
	public String showOrder(Model model, @ModelAttribute OrderShowForm form, HttpSession session) {

		UserBean userBean = ((UserBean) session.getAttribute("user"));

		Order order = oRepository.findOne(form.getId());

		OrderBean orderBean = new OrderBean();
		BeanUtils.copyProperties(order, orderBean);
		orderBean.setInsertDate(order.getInsertDate().toString());
		if (userBean.getAuthority() == 2) {
			orderBean.setUserName(userBean.getName());
		}else{
			orderBean.setUserName(order.getUser().getName());
		}

		List<OrderItemBean> orderItemBeanList = new ArrayList<OrderItemBean>();
		for (OrderItem orderItem : order.getOrderItemsList()) {
			OrderItemBean orderItemBean = new OrderItemBean();
			orderItemBean.setName(orderItem.getItem().getName());
			orderItemBean.setPrice(orderItem.getItem().getPrice());
			orderItemBean.setOrderNum(orderItem.getQuantity());
			int subtotal = orderItem.getItem().getPrice() * orderItem.getQuantity();
			orderItemBean.setSubtotal(subtotal);
			orderItemBeanList.add(orderItemBean);
		}
		int total = PriceCalc.orderItemPriceTotal(order.getOrderItemsList());

		model.addAttribute("order", orderBean);
		model.addAttribute("orderItemBeans", orderItemBeanList);
		model.addAttribute("total", total);

		return "order/detail/order_detail";
	}
}
