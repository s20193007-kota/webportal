package jp.ac.hcs.s3a104.user;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * ユーザー管理一覧画面を表示する
	 * @param model
	 * @return ユーザー一覧画面
	 */
	@GetMapping("/user/list")
	public String getManagement(Principal principal,Model model) {

		UserEntity userEntity = userService.selectAll(principal.getName());
		model.addAttribute("userEntity",userEntity);

		return "user/userList";
	}

	/**
	 * ユーザー登録画面(管理者用)を表示する
	 * @param form
	 * @param model
	 * @return ユーザー登録画面(管理者用)
	 */
	@GetMapping("/user/insert")
	public String getUserInsert(UserForm form,Model model) {
		return "user/insert";
	}

	/**
	 * 	1件分のユーザー情報をデータベースの登録する
	 * @param form 登録するユーザー情報を(パスワードは平文)
	 * @param bindingResult データバインド実施結果
	 * @param principal ログイン情報
	 * @param model
	 * @return ユーザー一覧画面
	 */
	@PostMapping("/user/insert")
	public String getUserInsert(@ModelAttribute @Validated UserForm form,
			BindingResult bindingResult,
			Principal principal,Model model) {

		//入力チェックに引っかかった場合、前の画面に戻る
		if(bindingResult.hasErrors()) {
			return getUserInsert(form, model);
		}

		UserData data = userService.refillToData(form);
		boolean result = userService.insertOne(data);
		if(result) {
			model.addAttribute("message","ユーザーを登録しました");
		}else {
			model.addAttribute("errorMessage","ユーザー登録に失敗しました。操作をやり直してください");
		}
		return getManagement(principal,model);
	}

	@GetMapping("/user/detail/{id}")
	public String getUserDetail(@PathVariable("id") String user_id,
			Principal principal,Model model) {

		UserData data = userService.selectOne(user_id);
		model.addAttribute("userData",data);
		return "user/detail";
	}
}
