package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.OffsetDateTime;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.DemoApplication;
import com.example.demo.model.Role;
import com.example.demo.model.Users;
import com.example.demo.util.DateUtil;


@SpringBootTest(classes = DemoApplication.class)
public class UserRepositoryTest {

	@Autowired
	UserRepository crud;
	@Autowired
	UserQueryRepository query;

	@Test
	void testCrud() {
		
		String testID = "test";

		Users repo = crud.findById(testID).orElse(null);
		assertNull(repo);

		Users user = new Users();
		user.setId(testID);
		user.setName("テスト");
		user.setPassword("Password");
		user.setRole("user");
		Date date = DateUtil.parse("2009-11-10 23:00:00");
		OffsetDateTime time = DateUtil.zone(date, "UTC");
		user.setExpiry(time);

		user.setRegister(true);
		//新規登録
		assertDoesNotThrow(() ->crud.save(user));

		final Users find = crud.findById(user.getId()).orElse(null);
		assertNotNull(find);
	
		assertEquals(find.getId(),testID);
		assertEquals(find.getName(),"テスト");
		assertEquals(find.getPassword(),"Password");
		assertEquals(find.getRole(),"user");
		assertEquals(find.getExpiry(),time);
	
		//更新
		user.setName("テスト２");
		user.setUpdated(null);
		assertDoesNotThrow(() ->crud.save(user));

		final Users find2 = crud.findById(user.getId()).orElse(null);
		assertNotNull(find2);
		assertEquals(find2.getName(),"テスト２");

		//削除
		assertDoesNotThrow(() -> crud.delete(find));
		//削除後検索
		repo = crud.findById(user.getId()).orElse(null);
		assertNull(repo);

		//存在しないロールでinsert
		Users notRole = new Users();
		notRole.setId("test2");
		notRole.setName("テスト2");
		notRole.setPassword("Password");
		notRole.setRole("nothing role");
		notRole.setExpiry(time);
		notRole.setRegister(true);

		//例外が発生するはず
		assertThrows(RuntimeException.class,() ->crud.save(notRole));
	}
	

	@Test
	void testFindByPassword() {
		Users user = query.findByPassword("user","passw0rd");
		assertNotNull(user);
		assertEquals(user.getName(),"山田太郎");

		user = query.findByPassword("user","password");
		assertNull(user);
	}

	@Test
	void testJoinRole() {
		Users user = query.joinRole("user");
		assertNotNull(user);
		assertEquals(user.getName(),"山田太郎");

		/* 関連性を実装
		Role role = user.getRoleObj();
		assertNotNull(role);
		assertEquals(role.getName(),"一般ユーザ");
		*/

	}
}
