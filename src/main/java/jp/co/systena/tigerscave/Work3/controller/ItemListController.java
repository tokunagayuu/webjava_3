package jp.co.systena.tigerscave.Work3.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jp.co.systena.tigerscave.Work3.model.Cart;
import jp.co.systena.tigerscave.Work3.model.DeleteForm;
import jp.co.systena.tigerscave.Work3.model.Item;
import jp.co.systena.tigerscave.Work3.model.ListForm;
import jp.co.systena.tigerscave.Work3.model.Order;
import jp.co.systena.tigerscave.Work3.service.ListService;

/**
 * The Class ShoppingController.
 */
@Controller // Viewあり。Viewを返却するアノテーション
public class ItemListController {

  @Autowired
  HttpSession session; // セッション管理

  @Autowired
  ListService listService; // サービスクラス

  @Autowired
  JdbcTemplate jdbcTemplate;

  /**
   * 商品一覧画面表示
   *
   * @param mav the mav
   * @return the model and view
   */
  @RequestMapping(value = "/list", method = RequestMethod.GET) // URLとのマッピング
  public ModelAndView list(ModelAndView mav) {

	// 商品一覧取得
	    List<Map<String, Object>> itemList = listService.getItemList();

	    // テンプレートに渡す内容を設定
	    mav.addObject("itemList", itemList);

    // Viewのテンプレート名を設定
    mav.setViewName("ListView");

    return mav;
  }

  /**
   * 注文内容をカートに追加する
   *
   * @param mav the mav
   * @param listForm the list form
   * @param bindingResult the binding result
   * @return the model and view
   */
  @RequestMapping(value = "/list", method = RequestMethod.POST) // URLとのマッピング
  public ModelAndView order(ModelAndView mav, @Validated ListForm listForm,
      BindingResult bindingResult) {

    if (bindingResult.getAllErrors().size() > 0) {
      // リクエストパラメータにエラーがある場合は商品一覧画面を表示

    	List<Map<String, Object>> itemListMap = listService.getItemList();
      mav.addObject("itemList", itemListMap);

      // Viewのテンプレート名を設定
      mav.setViewName("ListView");

      return mav;

    }

    // 注文内容をカートに追加
    Cart cart = getCart();
    cart.addOrder(listForm.getItem_id(), listForm.getItem_name());

    // データをセッションへ保存
    session.setAttribute("cart", cart);

    return new ModelAndView("redirect:/cart"); // リダイレクト
  }

  /**
   * カートの内容を表示する
   *
   * @param mav the mav
   * @return the model and view
   */
  @RequestMapping(value = "/cart", method = RequestMethod.GET) // URLとのマッピング
  public ModelAndView cart(ModelAndView mav) {

    // セッションからカートを取得し、テンプレートに渡す
    Cart cart = getCart();
    mav.addObject("orderList", cart.getOrderList());

    // 商品一覧をテンプレートに渡す。※商品名、価格を表示するため
    List<Map<String, Object>> itemListMap = listService.getItemList();
    mav.addObject("itemList", itemListMap);

    // 合計金額計算
    int totalPrice = 0;
    int iPrice = 0;
    int array[] = new int[10];
    for (Order order : cart.getOrderList()) {
      System.out.println(order);
      iPrice = 0;
      for (int i=0;i<itemListMap.size();i++) {
    	  if(order.getItemId() == Integer.parseInt(itemListMap.get(i).get("item_id").toString())) {
    		  iPrice = Integer.parseInt(itemListMap.get(i).get("price").toString());
    		  array[order.getItemId()]=i;
    		  break;
    	  }

      }

      totalPrice += iPrice  * order.getNum();
    }
    mav.addObject("array",array);
    mav.addObject("totalPrice", totalPrice);

    // Viewのテンプレート名設定
    mav.setViewName("CartView");

    return mav;
  }

  /**
   * 注文内容削除する
   *
   * @param mav the mav
   * @param deleteForm the delete form
   * @param bindingResult the binding result
   * @return the model and view
   */
  @RequestMapping(value = "/cart", method = RequestMethod.POST) // URLとのマッピング
  public ModelAndView deleteOrder(ModelAndView mav, @Validated DeleteForm deleteForm,
      BindingResult bindingResult) {

    if (bindingResult.getAllErrors().size() == 0) {
      //リクエストパラメータにエラーがなければ、削除処理行う

      // カートから商品を削除
      Cart cart = getCart();
      cart.deleteOrder(deleteForm.getItemId());

      // データをセッションへ保存
      session.setAttribute("cart", cart);
    }

    return new ModelAndView("redirect:/cart"); // リダイレクト
  }

  /**
   * セッションからカートを取得する
   *
   * @return the cart
   */
  private Cart getCart() {
    Cart cart = (Cart) session.getAttribute("cart");
    if (cart == null) {
      cart = new Cart();
      session.setAttribute("cart", cart);
    }

    return cart;
  }

//  /**
//   * 更新する(update)
//   *
//   * @param mav the mav
//   * @return the model and view
//   */
//  @RequestMapping(value = "/update", method = RequestMethod.POST) // URLとのマッピング
//  public ModelAndView update(ModelAndView mav, update update,BindingResult bindingResult) {
//      List<Map<String, Object>> itemListMap = listService.getItemList();
//	  if (itemListMap != null) {
//	          //1行分の値でデータベースをUPDATEする
//	          //item_idをキーに名称と価格を更新する
//	          //SQL文字列中の「?」の部分に、後ろで指定した変数が埋め込まれる
//	      	  int updateCount = jdbcTemplate.update(
//	                  "UPDATE items SET item_name = ?, price = ? WHERE item_id = ?",
//	                  update.getItemId(),
//	                  Integer.parseInt(update.getItemPrice()),
//	                  Integer.parseInt(update.getItemId()));
//
//	  }
//	    mav.addObject("itemList", itemListMap);
//
//	    // Viewのテンプレート名を設定
//	    mav.setViewName("ListView");
//	    return mav;
//
//  }

  /**
   * 「削除」リンク押下時の処理
   *
   * パラメータで受け取ったアイテムIDのデータを削除する
   *
   * @param itemId
   * @param model
   * @return
   */
  @RequestMapping(value = "/deleteitem", method = RequestMethod.GET) // URLとのマッピング
  public String update(@RequestParam(name = "item_id", required = true) String itemId,
      Model model) {

      // パラメータで受けとったアイテムIDのデータを削除する
    // SQL文字列中の「?」の部分に、後ろで指定した変数が埋め込まれる
    int deleteCount = jdbcTemplate.update("DELETE FROM items WHERE item_id = ?", Integer.parseInt(itemId));


    return "redirect:/list";

  }

  /**
   * データベースからアイテムデータ一覧を取得する
   *
   * @return
   */
  private List<Item> getItemList() {

    //SELECTを使用してテーブルの情報をすべて取得する
    List<Item> list = jdbcTemplate.query("SELECT * FROM items ORDER BY item_id", new BeanPropertyRowMapper<Item>(Item.class));

    return list;




  }

}
