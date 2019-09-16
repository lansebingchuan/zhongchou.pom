package com.aisouji.manager.controller.advertisement;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.aisouji.bean.Advertisement;
import com.aisouji.bean.User;
import com.aisouji.manager.service.AdvertisementService;
import com.aisouji.util.BaseController;
import com.aisouji.util.Constent;
import com.aisouji.util.Page;

@Controller
@RequestMapping("/ment")
public class AdvertisementController extends BaseController{

	@Autowired
	AdvertisementService advertisementService;

	@RequestMapping("/toaddAdvertisement")//广告新增页面
	public String toAddAdv() {
		return "/operation/advertisement/addAdvertisement";
	}
	@RequestMapping("/editAdvertisement")//广告修改页面
	public String toEdit() {
		return "/operation/advertisement/editAdvertisement";
	}
	@ResponseBody
	@RequestMapping("/getData")//获取修改的数据
	public Object getData(Integer id) {
		start();
		try {
			Advertisement advertisement = advertisementService.getAdvertisementById(id);
			put("adv", advertisement);
			message("获取数据成功，请更改！");
			flage(true);
			return end();
		} catch (Exception e) {
			message("获取数据失败，请稍后重试！");
			flage(false);
			return end();
		}
	}

	@ResponseBody
	@RequestMapping("/initAdvertisement")//获取广告数据
	public Object initAdvertisement(@RequestParam(value="pagePoint" ,required= false,defaultValue="0")Integer pagePoint ,
			@RequestParam(value="pageSize" ,required= false ,defaultValue="5")Integer pageSize ,
			String queryText ,Map<String, Object> map ) {
		Page page = null;
		start();
		try {
			map.put("pagePoint", pagePoint);
			map.put("pageSize", pageSize);
			if (queryText != null && !queryText.equals("")) {
				if (queryText.contains("%")) {//特殊字符处理
					queryText = queryText.replace("%", "\\%");
				}
				map.put("queryText", queryText);
			}
			page = advertisementService.queryPage(map);
			message("请求数据成功！");
			flage(true);
			put("page", page);
			return end();
		} catch (Exception e) {
			// TODO: handle exception
			message("请求数据失败！");
			flage(false);
			return end();
		}
	}
	@ResponseBody
	@RequestMapping("/upDate")//获取需要更新的数据
	public boolean upDate(HttpServletRequest servletRequest , Advertisement advertisement , HttpSession session) {
		boolean flage = saveAndUpdate(servletRequest , advertisement , session );
		if (flage) {//图片保存完毕
			Integer integer = advertisementService.updateAdvertisement(advertisement);
			if (integer == 1) {
				return true;
			}
		}else {
			return false;
		}
		return false;
	}
	
	private boolean saveAndUpdate(HttpServletRequest servletRequest, Advertisement advertisement, HttpSession session) {
		User user =(User) session.getAttribute(Constent.USER);
		if (advertisement.getStatus().equals(Constent.STATUS_0)) {
			advertisement.setStatus("0");
		}else {
			advertisement.setStatus("1");
		}
		advertisement.setUserid(user.getId());//修改人id
		String icoPath = null;//数据库文件路径
		MultipartFile multipartFile  = null;
		File picFile = null;//需要保存的文件
		try {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)servletRequest;//Dispatcher内部进行判断是否有文件，然后包装servletRequest
			multipartFile = multipartHttpServletRequest.getFile("file");//获取文件的操作对象
			String filename = multipartFile.getOriginalFilename();//获取文件名
			String substring = filename.substring(filename.lastIndexOf("."));//获取后缀
			String newFileName = UUID.randomUUID().toString() + substring;//新的文件名
			ServletContext servletContext = session.getServletContext();
			String realPath = servletContext.getRealPath("/tipc");//获取对应文件夹的路径
			icoPath = "/adv/" + newFileName;//页面展示路径
			String filePath = realPath + icoPath;//保存路径
			picFile = new File(filePath);
			advertisement.setIconpath(icoPath);
			multipartFile.transferTo(picFile);//保存图片
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("不更新图片");
			return true;
		}
	}
	@ResponseBody
	@RequestMapping("addAdvertisement")
	public boolean addAdvertisement(HttpServletRequest servletRequest , Advertisement advertisement , HttpSession session) {
		boolean flage = saveAndUpdate(servletRequest , advertisement , session );
		if (flage) {//图片保存完毕
			Integer integer = advertisementService.saveAdvertisement(advertisement);
			if (integer == 1) {
				return true;
			}
		}else {
			return false;
		}
		return false;
	}
	

	@RequestMapping("removeAdv")
	@ResponseBody
	public Object removeAdv(Integer id) {
		start();
		try {
			Integer integer = advertisementService.removeAdvertisementById(id);
			if (integer == 1) {
				message("广告移除成功！");
				flage(true);
				return end();
			}else {
				message("广告移除失败！");
				flage(false);
				return end();
			}
		} catch (Exception e) {
			message("数据库错误！");
			flage(false);
			return end();
		}
	}
}
