package jp.co.systena.tigerscave.Work3.model;

/**
 * The Class Item.
 */
public class Item {

  /** 商品ID */
  private int itemId;

  /** 商品名 */
  private String name;

  /** 価格 */
  private int price;

  public Item(int itemId, String name, int price) {
    this.itemId = itemId;
    this.name = name;
    this.price = price;
  }

  public int getItemId() {
    return itemId;
  }

  public void setItemId(int itemId) {
    this.itemId = itemId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

}
