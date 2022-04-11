package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

	private RouteService routeService=new RouteServiceImpl();
	private FavoriteService favoriteService=new FavoriteServiceImpl();
	/**
	     分页查询
	 */
	public void pageQuery(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        String currentPageStr=request.getParameter("currentPage"); //获取当前页码
		String pageSizeStr=request.getParameter("pageSize"); //获取每页记录数量
		String cidStr=request.getParameter("cid");
		String rname=request.getParameter("rname");
		if("null".equals(rname)){
			rname=null;
		}else {
			rname=new String(rname.getBytes("iso-8859-1"),"utf-8");
		}

		//对获取的数据进行处理
		int cid=0;
		if(cidStr!=null && cidStr.length()>0 && !"null".equals(cidStr)){
			cid=Integer.parseInt(cidStr);
		}
        //处理每页记录数量，如果无传入值，每页记录数量默认为5
		int pageSize=0;
		if(pageSizeStr!=null && pageSizeStr.length()>0){
			pageSize=Integer.parseInt(pageSizeStr);
		}
		else {
			pageSize=5;
		}
        //处理当前页码，如果无传入值，当前页码默认为1
		int currentPage=0;
		if(currentPageStr!=null && currentPageStr.length()>0){
			currentPage=Integer.parseInt(currentPageStr);
		}
		else {
			currentPage=1;
		}
        //查询PageBean对象
		PageBean<Route> pageBean=routeService.pageQuery(cid,currentPage,pageSize,rname);
		//序列化成json
        writeValue(pageBean,response);

	}
	/**
	     查询一条线路
	 */
	public void findOne(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		String ridStr=request.getParameter("rid");
		Route route=routeService.findOne(ridStr);
		writeValue(route,response);
	}
	/**
	     判断是否收藏
	 */
	public void isFavorite(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		//获取线路id
		String ridStr=request.getParameter("rid");
		//获取当前登录用户
		User user= (User) request.getSession().getAttribute("user");
		//判断user是否为空(判断是否登录)，并设置uid
		int uid;
		if(user==null){
			uid=0;
		}else{
            uid=user.getUid();
		}
        boolean flag=favoriteService.isFavorite(ridStr,uid);
		writeValue(flag,response);

	}
	/**
	     添加收藏
	 */
	public void addFavorite(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		//获取线路id
		String ridStr=request.getParameter("rid");
		//获取当前登录用户
		User user= (User) request.getSession().getAttribute("user");
		//判断user是否为空(判断是否登录)，并设置uid
		int uid;
		if(user==null){
			return;
		}else{
			uid=user.getUid();
		}
		favoriteService.add(ridStr,uid);
	}
}



