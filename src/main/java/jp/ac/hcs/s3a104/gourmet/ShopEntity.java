package jp.ac.hcs.s3a104.gourmet;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * グルメ情報検索結果の店舗情報
 * 各項目のデータ仕様については、APIの仕様を参照する
 * - https://webservice.recruit.co.jp/
 */
@Data
public class ShopEntity {

	private String shopseachword;

	/**グルメ情報のリスト*/
	private List<ShopData> results = new ArrayList<ShopData>();
}
