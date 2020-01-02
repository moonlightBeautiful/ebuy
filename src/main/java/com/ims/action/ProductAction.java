package com.ims.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.stereotype.Controller;

import com.ims.entity.PageBean;
import com.ims.entity.Product;
import com.ims.service.ProductService;
import com.ims.util.NavUtil;
import com.ims.util.PageUtil;
import com.ims.util.StringUtil;
import com.opensymphony.xwork2.ActionSupport;

@Controller
public class ProductAction extends ActionSupport implements ServletRequestAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource
	private ProductService productService;
	
	private HttpServletRequest request;
	
	private List<Product> productList;
	private Product s_product;
	
	private String page;
	private Long total;
	private String pageCode;
	private String mainPage;
	private String navCode;
	public List<Product> getProductList() {
		return productList;
	}
	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
	public Product getS_product() {
		return s_product;
	}
	public void setS_product(Product s_product) {
		this.s_product = s_product;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public String getPageCode() {
		return pageCode;
	}
	public void setPageCode(String pageCode) {
		this.pageCode = pageCode;
	}
	public String getMainPage() {
		return mainPage;
	}
	public void setMainPage(String mainPage) {
		this.mainPage = mainPage;
	}
	public String getNavCode() {
		return navCode;
	}
	public void setNavCode(String navCode) {
		this.navCode = navCode;
	}
	@Override
	public String execute() throws Exception {
		if(StringUtil.isEmpty(page)){
			page="1";
		}
		PageBean pageBean=new PageBean(Integer.parseInt(page),8);
		productList=productService.findProductList(s_product, pageBean);
		total=productService.getProductCount(s_product);
		StringBuffer param=new StringBuffer();
		if(s_product!=null){
			if(s_product.getBigType()!=null){
				param.append("s_product.bigType.id="+s_product.getBigType().getId());
			}
		}
		pageCode=PageUtil.genPagination(request.getContextPath()+"/product.action", total, Integer.parseInt(page), 8, param.toString());
		navCode=NavUtil.genNavCode("商品列表");
		mainPage="product/productList.jsp";
		return super.execute();
	}
	@Override
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request=request;
	}
	
	
	
}
