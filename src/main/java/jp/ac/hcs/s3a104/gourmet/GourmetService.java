package jp.ac.hcs.s3a104.gourmet;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Transactional
@Service
public class GourmetService {

	@Autowired
	RestTemplate restTemplate;

	/** グルメサーチAPI リクエストURL */
	private static final String URL ="http://webservice.recruit.co.jp/hotpepper/gourmet/v1/"
			+ "?key={API_KEY}&name={shopname}&large_service_area={large_service_area}&format=json";

	/**自分のAPIキー*/
	private static final String API_KEY ="fca5cd92a1324a87";

	public ShopEntity getShop(String shopname,String large_service_area) {
		//APIへアクセスして、結果を取得
		String json = restTemplate.getForObject(URL,String.class,API_KEY,shopname,large_service_area);

		//エンティティクラスを生成
		ShopEntity shopEntity = new ShopEntity();
		shopEntity.setShopseachword(shopname);

		//jsonクラスへの変換失敗のため、例外処理
		try {
			//変換クラスを生成し、文字列からjsonクラスへ変換する
			ObjectMapper mapper = new ObjectMapper();
			JsonNode node = mapper.readTree(json);

			//shoplistパラメータの抽出
			for(JsonNode shop : node.get("results").get("shop")) {
				//データクラスに生成(results1件分)
				ShopData shopData = new ShopData();

				shopData.setId(shop.get("id").asText());
				shopData.setName(shop.get("name").asText());
				shopData.setLogo_image(shop.get("logo_image").asText());
				shopData.setName_kana(shop.get("name_kana").asText());
				shopData.setAddress(shop.get("address").asText());
				shopData.setAccess(shop.get("access").asText());
				shopData.setUrl(shop.get("urls").get("pc").asText());
				shopData.setImage(shop.get("photo").get("mobile").get("l").asText());

				//可変長配列の末尾に追加
				shopEntity.getResults().add(shopData);

			}
		}catch(IOException e) {
			//例外発生時は、エラーメッセージの詳細を標準エラー出力
			e.printStackTrace();
		}
		return shopEntity;
	}
}
