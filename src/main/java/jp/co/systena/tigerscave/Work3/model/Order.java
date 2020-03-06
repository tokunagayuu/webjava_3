package jp.co.systena.tigerscave.Work3.model;

/**
 * The Class Order.
 */
public class Order {


		  /** 商品ID */
		  private int itemId;

		  /** 個数 */
		  private int num;

		  public Order(int itemId, int num) {
		    this.itemId = itemId;
		    this.num = num;
		  }

		  public int getItemId() {
		    return itemId;
		  }

		  public void setItemId(int itemId) {
		    this.itemId = itemId;
		  }

		  public int getNum() {
		    return num;
		  }

		  public void setNum(int num) {
		    this.num = num;
		  }

}
