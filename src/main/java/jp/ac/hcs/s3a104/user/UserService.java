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
}
