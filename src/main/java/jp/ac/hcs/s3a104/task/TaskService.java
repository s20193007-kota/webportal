package jp.ac.hcs.s3a104.task;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * タスク情報を操作する
 */
@Transactional
@Service
public class TaskService {

	@Autowired
	TaskRepository taskRepository;

	/**
	 * 指定したユーザーIDのタスク情報を全件取得する
	 * @param userId ユーザーID
	 * @return TaskEntity
	 */
	public TaskEntity selectAll(String userId) {
		TaskEntity taskEntity;
		try {
			taskEntity = taskRepository.selectAll(userId);
		}catch(DataAccessException e) {
			e.printStackTrace();
			taskEntity = null;
		}
		return taskEntity;
	}

	/**
	 * タスクを追加する
	 * @param userId
	 * @param comment
	 * @param limitday
	 * @return TaskEntity
	 */
	public TaskEntity insert(String userId, String comment, String limitday) {
		TaskEntity taskEntity = new TaskEntity();
		TaskData taskData = new TaskData();

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		Date due;
		try {
			if(comment != ""&& comment.length() < 51 && limitday != "") {
				due = dateFormat.parse(limitday);
				taskData.setUser_id(userId);
				taskData.setComment(comment);
				taskData.setLimitday(due);
			}
		}catch(ParseException e) {
			e.printStackTrace();
		}catch(NullPointerException e) {
			e.printStackTrace();
		}

		int rowNumber = taskRepository.insertOne(taskData);
		return taskEntity;
	}

	/**
	 * 指定されたタスクのIDを削除する
	 * @param id 指定されたID
	 * @return 1件以上あればtrue、0件ならfalse
	 */
	public boolean delete(int id) {
		int count;
		try {
			count = taskRepository.deleteOne(id);
		}catch(DataAccessException e) {
			e.printStackTrace();
			count = 0;
		}
		return count > 0;

	}

	/**
	 * タスク情報をCSVファイルとしてサーバに保存する.
	 * @param user_id ユーザID
	 * @throws DataAccessException
	 */
	public void taskListCsvOut(String user_id) throws DataAccessException {
		taskRepository.tasklistCsvOut(user_id);
	}

	/**
	 * サーバーに保存されているファイルを取得して、byte配列に変換する.
	 * @param fileName ファイル名
	 * @return ファイルのbyte配列
	 * @throws IOException ファイル取得エラー
	 */
	public byte[] getFile(String fileName) throws IOException {
		FileSystem fs = FileSystems.getDefault();
		Path p = fs.getPath(fileName);
		byte[] bytes = Files.readAllBytes(p);
		return bytes;
	}
}
