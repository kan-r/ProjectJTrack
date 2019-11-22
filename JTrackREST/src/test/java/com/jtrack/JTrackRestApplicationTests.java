package com.jtrack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.jtrack.dao.UserDao;
import com.jtrack.model.User;
import com.jtrack.service.UserService;

@SpringBootTest
class JTrackRestApplicationTests {

	@Autowired
	private UserService userService;

	@MockBean
	private UserDao userDao;
	
	@Test
	public void getUserListTest() {
		
		List<User> userList = new ArrayList<User>();
		userList.add(new User("ADMIN", "Kan", "Ranganathan", true, null, "ADMIN", null, null));
		userList.add(new User("D2161", "Tharani", "Ranganathan", true, null, "ADMIN", null, "D2161"));
		userList.add(new User("D2162", "Tharani", "Ranganathan", true, null, "D2161", null, null));
		
		when(userDao.findAll()).thenReturn(userList);
		assertEquals(3, userService.getUserList().size());
	}
	
	@Test
	public void getUserTest() {
		
		Optional<User> user = Optional.of(new User("ADMIN", "Kan", "Ranganathan", true, null, "ADMIN", null, null));
		Optional<User> userEmpty = Optional.of(new User());
		
		String userId = "ADMIN";
		when(userDao.findById(userId)).thenReturn(user);
		assertEquals(user.get(), userService.getUser(userId));
		
		userId = "admin";
		when(userDao.findById(userId)).thenReturn(user);
		assertEquals(user.get(), userService.getUser(userId));
		
		userId = "admi";
		when(userDao.findById(userId)).thenReturn(userEmpty);
		assertEquals(userEmpty.get(), userService.getUser(userId));
		
		userId = "";
		when(userDao.findById(userId)).thenReturn(userEmpty);
		assertEquals(userEmpty.get(), userService.getUser(userId));
		
		userId = null;
		when(userDao.findById(userId)).thenReturn(userEmpty);
		assertEquals(userEmpty.get(), userService.getUser(userId));
	}

}
