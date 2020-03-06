package jp.co.systena.tigerscave.Work3.model;

import javax.validation.constraints.Min;

/**
 * The Class ListForm.
 */
public class ListForm {

	  /** 商品ID */
	  private int item_id;

	  /** 個数 */
	  @Min(1) //設定できる最小値を定義
	  private int item_name;

	  public int getItem_id() {
	    return item_id;
	  }

	  public void setItem_id(int item_id) {
	    this.item_id = item_id;
	  }

	  public int getItem_name() {
	    return item_name;
	  }

	  public void setItem_name(int item_name) {
	    this.item_name = item_name;
	  }

}
