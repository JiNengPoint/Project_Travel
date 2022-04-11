package cn.itcast.travel.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//完成方法的分发
		//获取请求路径
		String uri=req.getRequestURI();
		//获得方法名称
		String methodName=uri.substring(uri.lastIndexOf('/')+1);

		try {
			//获取方法对象
			Method method=this.getClass().getMethod(methodName,HttpServletRequest.class,HttpServletResponse.class);
			//执行方法
			method.invoke(this,req,resp);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	     序列化成json，直接返回客户端
	 */
	public void writeValue(Object obj,HttpServletResponse response) throws IOException {
		ObjectMapper mapper=new ObjectMapper();
		response.setContentType("application/json;charset=utf-8");
		mapper.writeValue(response.getOutputStream(),obj);
	}
	/**
	     序列化成json，返回字符串
	 */
	public String writeValueAsString(Object obj) throws JsonProcessingException {
		ObjectMapper mapper=new ObjectMapper();
		return mapper.writeValueAsString(obj);
	}
}



