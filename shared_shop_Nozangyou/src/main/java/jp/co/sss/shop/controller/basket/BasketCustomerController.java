package jp.co.sss.shop.controller.basket;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sss.shop.bean.BasketBean;
import jp.co.sss.shop.entity.Item;
import jp.co.sss.shop.form.BasketForm;
import jp.co.sss.shop.repository.ItemRepository;

@Controller
public class BasketCustomerController {
	// TODO ここに処理を追加してください
	@Autowired
	private HttpSession session;
	@Autowired
	private ItemRepository iRepository;

	/**
	 * 買い物かご内の商品の一覧を表示する
	 * @author 伊藤匠真
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/basket/index/{index}")
	public String index(@PathVariable int index, Model model, BasketForm form){
		if(session.getAttribute("basket") == null){
			return "basket/shopping_basket";
		}
		if(((List<BasketBean>)(session.getAttribute("basket"))).size() == 0){
			return "basket/shopping_basket";
		}

		if(form.getItemId() != null){
			setOrderNum(form);
		}
		List<BasketBean> basket = (List<BasketBean>)session.getAttribute("basket");

		return basketPaging(basket, index, model);
	}

	/**
	 * 商品を買い物籠へ追加する
	 * @return 商品一覧へリダイレクト
	 * @author 伊藤匠真
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/basket/add/{id}")
	public String addBasket(@PathVariable Integer id){
		Item item = iRepository.findOne(id);
		BasketBean basketBean = new BasketBean(item.getId(), item.getName(), item.getStock());

		//List なければ新規作成、あればセッションから取得
		List<BasketBean> basket;
		if(session.getAttribute("basket") == null){
			basket = new ArrayList<BasketBean>();
		}else{
			basket = (List<BasketBean>)session.getAttribute("basket");
			//買い物かごに既に商品があるか
			for(BasketBean bb: basket){
				if(bb.getId() == id){
					// 不正アクセスの場合、ログイン画面にリダイレクト
					if (session.getAttribute("user") == null) {
						return "redirect:/login";
					}

					return "redirect:/basket/index/1";
				}
			}
		}

		basket.add(basketBean);
		session.setAttribute("basket", basket);

		// 不正アクセスの場合、ログイン画面にリダイレクト
		if (session.getAttribute("user") == null) {
			return "redirect:/login";
		}

		return "redirect:/basket/index/1";
	}

	/**
	 * 買い物かご内の商品を削除する
	 * @author 伊藤匠真
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/basket/delete/{pageIndex}/{index}")
	public String deleteFromBasket(@PathVariable int pageIndex, @PathVariable int index){
		List<BasketBean> basket = (List<BasketBean>)session.getAttribute("basket");

		int underNum = (pageIndex - 1) * 10;
		basket.remove(underNum + index);
		if(basket.size() == 0){
			session.setAttribute("basket", null);
		}

		return "redirect:/basket/index/" + pageIndex;
	}
	/**
	 * 買い物かご内の商品の全削除
	 * @author 伊藤匠真
	 */
	@RequestMapping("/basket/allDelete")
	public String allDelete(){
		session.setAttribute("basket", null);

		return "redirect:/basket/index/1";
	}

	/**
	 * 届け先入力画面への遷移
	 * @author 伊藤
	 */
	@RequestMapping("/setOrderNum")
	public String goAdressInput(BasketForm form){
		setOrderNum(form);
		return "redirect:/adress_input";
	}

	/**
	 * 注文数セット
	 * @author 伊藤
	 */
	@SuppressWarnings("unchecked")
	public void setOrderNum(BasketForm form){
		for(BasketBean basket: (List<BasketBean>)session.getAttribute("basket")){
			for(int i = 0; i < form.getItemId().length;i++){
				if(basket.getId().equals(form.getItemId()[i])){
					basket.setOrderNum(form.getOrderNum()[i]);
				}
			}
		}
	}

	/**
	 * ページング用
	 *
	 * @author 伊藤
	 */
	private String basketPaging(List<BasketBean> basket, int index, Model model){
		model.addAttribute("index", index);

		int totalItem = basket.size();
		if(totalItem == 0){
			return "basket/shopping_basket";
		}

		//@param showNum ページに表示する商品数
		int showNum;
		if(totalItem % 10 == 0){
			showNum = 10;
		}else{
			showNum = totalItem % 10;
		}

		int underNum = (index - 1) * 10;
		List<BasketBean> items = new ArrayList<BasketBean>();
		//最後のページかで場合わけ
		if(index == (totalItem / 10) + 1){
			for(int i = 0; i < showNum; i++){
				items.add(basket.get(underNum + i));
			}
		}else{
			for(int i = 0; i < 10; i++){
				items.add(basket.get(underNum + i));
			}
		}

		//下に表示するリンクの数値配列
		int[] link;
		if(totalItem % 10 == 0){
			link = new int[totalItem / 10];
			for(int j = 1; j <= (totalItem / 10); j++){
				link[j - 1] = j;
			}
		}else{
			link = new int[(totalItem / 10) + 1];
			for(int j = 1; j <= (totalItem / 10) + 1; j++){
				link[j - 1] = j;
			}
		}

		model.addAttribute("basket", items);
		model.addAttribute("link", link);
		model.addAttribute("index", index);
		return "basket/shopping_basket";
	}
}
