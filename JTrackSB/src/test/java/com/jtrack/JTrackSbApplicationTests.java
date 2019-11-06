package com.jtrack;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.jtrack.dao.UserDao;
import com.jtrack.model.Timesheet;
import com.jtrack.model.User;
import com.jtrack.service.JobService;
import com.jtrack.service.TimesheetService;
import com.jtrack.service.UserService;

@SpringBootTest
public class JTrackSbApplicationTests {

	@Autowired
	private UserService userService;

	@MockBean
	private UserDao userDao;
	
	@Autowired
	private TimesheetService timesheetService;
	
	@Autowired
	private JobService jobService;
	
	@Test
	public void getUsersTest() {
		
		List<User> usersList = new ArrayList<User>();
		usersList.add(new User("ADMIN", "Kan", "Ranganathan", 1, null, "ADMIN", null, null));
		usersList.add(new User("D2161", "Tharani", "Ranganathan", 1, null, "ADMIN", null, "D2161"));
		usersList.add(new User("D2162", "Tharani", "Ranganathan", 1, null, "D2161", null, null));
		
		when(userDao.findAll()).thenReturn(usersList);
		assertEquals(3, userService.getUserAll().size());
	}
	
	@Test
	public void getUserTest() {
		
		Optional<User> user = Optional.of(new User("ADMIN", "Kan", "Ranganathan", 1, null, "ADMIN", null, null));
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
	
	@Test
	public void addUserTest() {
		
		User user = new User("KAN", "Kan", "Ranganathan", 1, null, "ADMIN", null, null);
		
		when(userDao.save(user)).thenReturn(user);
		assertEquals(user, userService.addUser(user));
	}

	@Test
	public void deleteUserTest() {
		
		User user = new User("KAN", "Kan", "Ranganathan", 1, null, "ADMIN", null, null);
		
		userService.deleteUser(user);
		verify(userDao, times(1)).delete(user);
	}
	
	@Test
	public void updateUserTest() {
		
		User user = new User("KAN", "Kan", "Ranganathan", 1, null, "ADMIN", null, null);
		
		when(userDao.save(user)).thenReturn(user);
		assertEquals(user, userService.updateUser(user));
	}
	
//	@Test
	public void getTimesheetTest() throws ParseException {
		Date dateWorked =new SimpleDateFormat("dd/MM/yyyy").parse("04/11/2019"); 
		Timesheet timesheet = timesheetService.getTimesheet("ADMIN", 1, dateWorked);
		System.out.println(timesheet);
		assertNotEquals(new Timesheet(), timesheet);
	}
	
//	@Test
	public void getTimesheetAllTest() throws ParseException { 
	    Date dateFrom =new SimpleDateFormat("dd/MM/yyyy").parse("03/11/2019");  
	    Date dateTo =new SimpleDateFormat("dd/MM/yyyy").parse("04/11/2019");  
		
		List<Timesheet> timesheetList = timesheetService.getTimesheetAll("ADMIN", dateFrom, dateTo);
		assertEquals(1, timesheetList.size());
	}
}
