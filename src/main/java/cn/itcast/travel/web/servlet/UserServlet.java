package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

	//声明Service
	private UserService service=new UserServiceImpl();
	/**
	     注册方法
	 */
	public void register(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		//对验证码进行判断
		String check=request.getParameter("check");
		HttpSession session=request.getSession();
		String checkCode=(String) session.getAttribute("CHECKCODE_SERVER");
		//进行移除，确保验证码只被使用一次
		session.removeAttribute("CHECKCODE_SERVER");
		//验证码错误(注意：验证码需要区分大小写)
		if(checkCode==null || !checkCode.equalsIgnoreCase(check)){
			ResultInfo resultInfo=new ResultInfo();
			resultInfo.setFlag(false);
			resultInfo.setErrorMsg("验证码错误!");

			ObjectMapper mapper=new ObjectMapper();
			String json=mapper.writeValueAsString(resultInfo);
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().write(json);
			return;
		}

		//获取数据
		Map<String,String[]> map=request.getParameterMap();
		//进行数据封装
		User user=new User();
		try {
			//map中数据属性映射到bean中，将数据封装到bean中
			BeanUtils.populate(user,map);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		//UserService service=new UserServiceImpl();
		boolean flag=service.register(user);
		ResultInfo resultInfo=new ResultInfo();
		//判断注册是否成功，进行响应
		if(flag){
			resultInfo.setFlag(true);
		}
		else{
			resultInfo.setFlag(false);
			resultInfo.setErrorMsg("注册失败!");
		}
		//序列化成json
		ObjectMapper mapper=new ObjectMapper();
		String json=mapper.writeValueAsString(resultInfo);
		//把json重新写回数据端
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(json);
	}
	/**
	     登录方法
	 */
	public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//对验证码进行判断
		String check=request.getParameter("check");
		HttpSession session=request.getSession();
		String checkCode=(String) session.getAttribute("CHECKCODE_SERVER");
		//进行移除，确保验证码只被使用一次
		session.removeAttribute("CHECKCODE_SERVER");
		//验证码错误(注意：验证码需要区分大小写)
		if(checkCode==null || !checkCode.equalsIgnoreCase(check)){
			ResultInfo resultInfo=new ResultInfo();
			resultInfo.setFlag(false);
			resultInfo.setErrorMsg("验证码错误!");

			ObjectMapper mapper=new ObjectMapper();
			String json=mapper.writeValueAsString(resultInfo);
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().write(json);
			return;
		}
		//获取数据
		Map<String,String[]> map=request.getParameterMap();
		//进行数据封装
		User user=new User();
		try {
			//map中数据属性映射到bean中，将数据封装到bean中
			BeanUtils.populate(user,map);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		//UserService service=new UserServiceImpl();
		User u=service.login(user);
		ResultInfo resultInfo=new ResultInfo();

		if(u==null){
			resultInfo.setFlag(false);
			resultInfo.setErrorMsg("用户名或者密码错误!");
		}
		else {
			resultInfo.setFlag(true);
			//登录成功后需要保存Session
			request.getSession().setAttribute("user",u);
		}
		//序列化成json并重新写回数据端
		/*ObjectMapper mapper=new ObjectMapper();
		response.setContentType("application/json;charset=utf-8");
		mapper.writeValue(response.getOutputStream(),resultInfo);*/
		writeValue(resultInfo,response);
	}
	/**
	     查找登录用户
	 */
	public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Object user=request.getSession().getAttribute("user");
		/*ObjectMapper mapper=new ObjectMapper();
		response.setContentType("application/json;charset=utf-8");
		mapper.writeValue(response.getOutputStream(),user);*/
		writeValue(user,response);
	}
	/**
	     用户退出
	 */
	public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//销毁Session
		request.getSession().invalidate();
		//跳转至登录页面
		response.sendRedirect(request.getContextPath()+"/login.html");
	}

}



