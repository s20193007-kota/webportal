package jp.ac.hcs.s3a104.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PortalController {

	/**
	 * ログインに成功したら、メイン画面を表示する
	 * @return メイン画面
	 */

	@RequestMapping("/")
	public String index() {
		return "index";
	}
}
