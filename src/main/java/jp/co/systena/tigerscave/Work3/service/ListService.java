package jp.co.systena.tigerscave.Work3.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * The Class ListService.
 */
@Service
public class ListService {
	  @Autowired
	  JdbcTemplate jdbcTemplate;
	  /**
	   * 商品一覧取得
	   *
	   * @return 商品一覧
	   */
	  public List<Map<String, Object>> getItemList() {

	    //結果はMapのリストとして取得することもできる
	    List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT * FROM items ORDER BY item_id");

	    return list;
	  }

}
