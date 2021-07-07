package jp.ac.hcs.s3a104.user;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * ユーザー情報をまとめて管理する為のエンティティクラス
 */
@Data
public class UserEntity {

	/**ユーザー情報のリスト*/
	 private List<UserData> userlist = new ArrayList<UserData>();
}
