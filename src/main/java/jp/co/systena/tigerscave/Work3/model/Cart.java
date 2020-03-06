package jp.co.systena.tigerscave.Work3.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class Cart.
 */
public class Cart {

  private List<Order> orderList = new ArrayList<Order>();

  public List<Order> getOrderList() {
    return orderList;
  }

  /**
   * 注文内容を追加する
   * 商品がすでに存在すれば個数を追加する。存在しなければ注文内容を作成し追加する
   *
   * @param itemId the item id
   * @param num the num
   */
  public void addOrder(int itemId, int num) {

    Order addOrder = null;
    for(Order order : this.orderList) {

      if(order.getItemId() == itemId) {
        //個数追加する商品をnewOrderに格納
        addOrder = order;
      }

    }

    if(addOrder == null) {
      //注文内容追加
      Order order = new Order(itemId, num);
      this.orderList.add(order);
    } else {
      //すでに存在する注文に個数追加
      int newNum = addOrder.getNum() + num;
      addOrder.setNum(newNum);
    }

  }

  /**
   * 注文内容削除する
   *
   * @param itemId the item id
   */
  public void deleteOrder(int itemId) {

    Order deleteOrder = null;
    for(Order order : this.orderList) {

      if(order.getItemId() == itemId) {
        //削除する商品をdeleteOrderに格納
        deleteOrder = order;
      }

    }

    if(deleteOrder != null) {
      //注文内容削除
      this.orderList.remove(deleteOrder);
    }
  }

}
