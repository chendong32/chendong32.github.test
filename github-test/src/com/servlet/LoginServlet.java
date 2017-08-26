package com.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.business.UserBusiness;
import com.pojo.UserVO;

@WebServlet("/loginServlet.do")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Map<String, String[]> parameterMap = req.getParameterMap();
		UserVO userVO = new UserVO();
		try {
			BeanUtils.copyProperties(userVO, parameterMap);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		String userdb = req.getSession().getServletContext().getRealPath("");
		UserBusiness userBusiness = new UserBusiness();
		List<UserVO> list = new ArrayList<UserVO>();
		try {
			list = userBusiness.findUserInfo(userdb);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		if (null != list && list.contains(userVO)) {
			resp.sendRedirect("show.jsp");
			System.out.println("用户登录成功");
		} else {
			resp.sendRedirect("login.jsp");
			System.out.println("此用户信息不存在");
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
