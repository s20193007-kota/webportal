package jp.ac.hcs.s3a104.weather;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WeatherController {

	@Autowired
	private WeatherService weatherService;

	/**
	 *札幌の天気情報を3日分表示する
	 *@param citycode
	 *@param principal ログイン情報
	 *@param model
	 *@return 結果画面 - 天気情報
	 */
	@PostMapping("/weather")
	public String getCityCode(@RequestParam("citycode")String citycode,
			Principal principal,Model model) {

		WeatherEntity weatherEntity = weatherService.getWeather(citycode);
		model.addAttribute("weatherEntity",weatherEntity);


		return "weather/weather";
	}
}
