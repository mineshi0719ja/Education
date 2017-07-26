package jp.co.sss.shop.controller.order;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.sss.shop.bean.OrderBean;
import jp.co.sss.shop.bean.OrderItemBean;
import jp.co.sss.shop.bean.UserBean;
import jp.co.sss.shop.entity.Order;
import jp.co.sss.shop.entity.OrderItem;
import jp.co.sss.shop.form.OrderShowForm;
import jp.co.sss.shop.repository.OrderRepository;
import jp.co.sss.shop.util.PriceCalc;

@Controller
public class OrderShowAdminController {

	@Autowired
	OrderRepository orderRepository;

	@RequestMapping(path = "/order/list/admin", method = RequestMethod.GET)
	public String showOrderList(Model model, @ModelAttribute OrderShowForm form, HttpSession session) {

		UserBean userBean = ((UserBean) session.getAttribute("user"));

		List<Order> orderList = new ArrayList<Order>();
		if (userBean.getAuthority() == 2) {
			orderList = orderRepository.findByUserIdAndDeleteFlagOrderByInsertDateDesc(userBean.getId(), 0);
		} else {
			orderList = orderRepository.findByDeleteFlagOrderByInsertDateDesc(0);
		}

		List<OrderBean> orderBeanList = new ArrayList<OrderBean>();
		for (Order order : orderList) {
			OrderBean orderBean = new OrderBean();
			orderBean.setId(order.getId());
			orderBean.setUserName(order.getUser().getName());
			orderBean.setInsertDate(order.getInsertDate().toString());
			orderBean.setPayMethod(order.getPayMethod());
			int total = PriceCalc.orderItemPriceTotal(order.getOrderItemsList());
			orderBean.setTotal(total);
			orderBeanList.add(orderBean);
		}

		model.addAttribute("orders", orderBeanList);

		return "order/list/order_list_admin";

	}

	@RequestMapping(path = "/order/detail/admin", method = RequestMethod.GET)
	public String showOrder(Model model, @ModelAttribute OrderShowForm form, HttpSession session) {

		UserBean userBean = ((UserBean) session.getAttribute("user"));

		Order order = orderRepository.findOne(form.getId());

		OrderBean orderBean = new OrderBean();
		BeanUtils.copyProperties(order, orderBean);
		orderBean.setInsertDate(order.getInsertDate().toString());
		if (userBean.getAuthority() != 2) {
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

		return "order/detail/order_detail_admin";
	}

}
