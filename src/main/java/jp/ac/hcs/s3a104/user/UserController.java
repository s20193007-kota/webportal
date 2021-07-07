package jp.ac.hcs.s3a104.user;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 *
	 * @param principal
	 * @param model
	 * @return
	 */
	@GetMapping("/user/list")
	public String getManagement(Principal principal,Model model) {

		UserEntity userEntity = userService.selectAll(principal.getName());
		model.addAttribute("userEntity",userEntity);

		return "user/userList";
	}
}
