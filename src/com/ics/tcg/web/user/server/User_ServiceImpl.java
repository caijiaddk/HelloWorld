package com.ics.tcg.web.user.server;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.ics.tcg.database.UserDAO;
import com.ics.tcg.database.bean.User;
import com.ics.tcg.web.user.client.db.User_Client;
import com.ics.tcg.web.user.client.remote.User_Service;


/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class User_ServiceImpl extends RemoteServiceServlet implements
		User_Service {
	InitialContext ctx = null;
	UserDAO userdao = null;

	public User_ServiceImpl() {

		Properties props = new Properties();
		props.setProperty("java.naming.factory.initial",
				"org.jnp.interfaces.NamingContextFactory");
		props.setProperty("java.naming.provider.url", "localhost:1099");
		props.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming");
		try {
			ctx = new InitialContext(props);
			userdao = (UserDAO) ctx.lookup("UserDAOBean/remote");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public User_Client getUser(int userid) {

		User user;
		user = userdao.getUserByID(userid);
		User_Client userItem = new User_Client();
		userItem.account = user.getAccount();
		userItem.age = user.getAge();
		userItem.birthday = user.getBirthday();
		userItem.email = user.getEmail();
		userItem.password = user.getPassword();
		userItem.sex = user.getSex();
		userItem.tel = user.getTel();
		userItem.userid = user.getUserid();
		userItem.bymail = user.isBymail();
		userItem.bymobile = user.isBymobile();
		return userItem;

	}

	@Override
	public String saveUser(User_Client userC) {
		User user = new User();
		user.setUserid(userC.getUserid());
		user.setAccount(userC.getAccount());
		user.setAge(userC.getAge());
		user.setBirthday(userC.getBirthday());
		user.setBymail(userC.isBymail());
		user.setBymobile(userC.isBymobile());
		user.setEmail(userC.getEmail());
		user.setSex(userC.getSex());
		user.setTel(userC.getTel());
		user.setPassword(userC.getPassword());
		
		userdao.mergeUser(user);
		return "success";
		
	}

	@Override
	public String updateUser(User_Client userC) {
		User user = new User();
		user.setUserid(userC.getUserid());
		user.setAccount(userC.getAccount());
		user.setAge(userC.getAge());
		user.setBirthday(userC.getBirthday());
		user.setBymail(userC.isBymail());
		user.setBymobile(userC.isBymobile());
		user.setEmail(userC.getEmail());
		user.setSex(userC.getSex());
		user.setTel(userC.getTel());
		user.setPassword(userC.password);
		
		userdao.mergeUser(user);
		return "success";
	}
}
