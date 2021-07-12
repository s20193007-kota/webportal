package jp.ac.hcs.s3a104.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	public UserEntity selectAll(String user_id) {
		UserEntity userEntity;
		try {
			userEntity = userRepository.selectAll();
		}catch (DataAccessException e) {
			e.printStackTrace();
			userEntity = null;
		}
		return userEntity;
	}


	/**
	 * ユーザー情報を1件追加する
	 * @param userData 追加するユーザー情報(パスワードは平文)
	 * @return 処理結果(成功：true,失敗：false)
	 */
	public boolean insertOne(UserData userData) {
		int rowNumber;
		try {
			rowNumber = userRepository.insertOne(userData);
		}catch (DataAccessException e) {
			e.printStackTrace();
			rowNumber = 0;
		}
		return rowNumber > 0;
	}

	UserData refillToData(UserForm form) {
		UserData data = new UserData();
		data.setUser_id(form.getUser_id());
		data.setPassword(form.getPassword());
		data.setUser_name(form.getUser_name());
		data.setDarkmode(form.isDarkmode());
		data.setRole(form.getRole());
		//初期は有効とする
		data.setEnabled(true);
		return data;
	}

	public UserData selectOne(String user_id) {
		UserData data;
		try {
			data = userRepository.selectOne(user_id);
		}catch (DataAccessException e) {
			e.printStackTrace();
			data = null;
		}
		return data;
	}
}
