package jp.co.sss.shop.controller.order;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.sss.shop.bean.OrderBean;
import jp.co.sss.shop.bean.OrderItemBean;
import jp.co.sss.shop.entity.Order;
import jp.co.sss.shop.entity.OrderItem;
import jp.co.sss.shop.form.OrderForm;
import jp.co.sss.shop.repository.OrderRepository;
import jp.co.sss.shop.util.PriceCalc;

@Controller
public class OrderDeleteAdminController {

    @Autowired
    OrderRepository orderRepository;

    @RequestMapping(path = "/order/delete/check", method = RequestMethod.POST)
    public String deleteCheck(Model model, @ModelAttribute OrderForm form) {

        Order order = orderRepository.findOne(form.getId());

        OrderBean orderBean = new OrderBean();
        BeanUtils.copyProperties(order, orderBean);
        orderBean.setInsertDate(order.getInsertDate().toString());
        orderBean.setUserName(order.getUser().getName());


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

        return "order/delete/order_delete_check";
    }

    @RequestMapping(path = "/order/delete/complete", method = RequestMethod.POST)
    public String deletetComplete(@ModelAttribute OrderForm form) {

        Order order = orderRepository.findOne(form.getId());
        order.setDeleteFlag(1);
        orderRepository.save(order);

        return "order/delete/order_delete_complete";
    }
}
