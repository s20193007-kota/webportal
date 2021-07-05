package jp.ac.hcs.s3a104.task;

import java.io.IOException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.ac.hcs.s3a104.WebConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class TaskController {

	@Autowired
	private TaskService taskService;

	/**
	 * タスクを表示する
	 * @param principal ログイン情報
	 * @param model
	 * @return 結果画面 - タスク情報
	 */
	@GetMapping("/task/tasklist")
	public String getTaskList(Principal principal,Model model) {

		log.info("[" + principal.getName() + "]タスク検索");

		TaskEntity taskEntity = taskService.selectAll(principal.getName());
		if(taskEntity == null) {
			log.info("[" + principal.getName() + "]タスク検索：エラーが発生しました");
		}
		model.addAttribute("taskEntity",taskEntity);

		return "task/task";
	}

	@PostMapping("task/insert")
	public String insertTask(@RequestParam("comment") String comment,
			@RequestParam("limitday") String limitday,
			Principal principal,Model model) {

		TaskEntity taskEntity = taskService.insert(principal.getName(),comment,limitday);
		log.info("[" + principal.getName() + "]タスク追加：" + "コメント：" + comment + "期限日：" + limitday);
		return getTaskList(principal, model);
	}

	/**
	 * 自分の全てのタスク情報をCSVファイルとしてダウンロードさせる.
	 * @param principal ログイン情報
	 * @param model
	 * @return タスク情報のCSVファイル
	 */
	@PostMapping("/task/csv")
	public ResponseEntity<byte[]> getTaskCsv(Principal principal, Model model) {


		final String OUTPUT_FULLPATH = WebConfig.OUTPUT_PATH + WebConfig.FILENAME_TASK_CSV;

		log.info("[" + principal.getName() + "]CSVファイル作成:" + OUTPUT_FULLPATH);

		// CSVファイルをサーバ上に作成
		taskService.taskListCsvOut(principal.getName());

		// CSVファイルをサーバから読み込み
		byte[] bytes = null;
		try {
			bytes = taskService.getFile(OUTPUT_FULLPATH);
			log.info("[" + principal.getName() + "]CSVファイル読み込み成功:" + OUTPUT_FULLPATH);
		} catch (IOException e) {
			log.warn("[" + principal.getName() + "]CSVファイル読み込み失敗:" + OUTPUT_FULLPATH);
			e.printStackTrace();
		}

		// CSVファイルのダウンロード用ヘッダー情報設定
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "text/csv; charset=UTF-8");
		header.setContentDispositionFormData("filename", WebConfig.FILENAME_TASK_CSV);

		// CSVファイルを端末へ送信
		return new ResponseEntity<byte[]>(bytes, header, HttpStatus.OK);
	}
}