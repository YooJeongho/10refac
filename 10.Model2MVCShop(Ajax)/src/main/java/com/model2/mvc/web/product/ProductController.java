package com.model2.mvc.web.product;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.user.UserService;


//==> 상품관리 Controller
@Controller
@RequestMapping("/product/*")
public class ProductController {
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//setter Method 구현 않음
		
	public ProductController(){
		System.out.println(this.getClass());
	}
	
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml 참조 할것
	//==> 아래의 두개를 주석을 풀어 의미를 확인 할것
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	
	//@RequestMapping("/addProductView.do")
	@GetMapping("addProduct")
	public String addProduct() throws Exception {

		System.out.println("/addProductView");
		
		return "redirect:/product/addProductView.jsp";
	}
	
	//@RequestMapping("/addProduct")
	@PostMapping("addProduct")
	public String addProduct( @ModelAttribute("product") Product product ) throws Exception {

		System.out.println("/addUser.do");
		System.out.println("바뀌기 전 product manudate : "+product.getManuDate());
		String date = product.getManuDate().replaceAll("-", "");
		product.setManuDate(date);
		System.out.println(product.toString());
		//Business Logic
		productService.addProduct(product);
		
		
		return "forward:/product/addProduct.jsp";
	}
	
	@GetMapping("getProduct")
	public String getProduct( @RequestParam(value="menu", required = false) String menu,
								 	@RequestParam("prodNo") int prodNo,
									Model model ) throws Exception {
		
		System.out.println("/getProduct");
		
		System.out.println("prodNo 넘어오는지 : "+prodNo);
		System.out.println("menu 넘어오는지 : "+menu);
		
		if (menu == null) {
			menu = "search";
		}
		
		//Business Logic
		Product product = productService.getProduct(prodNo);
		System.out.println("select 결과 잘 넘어오는지 : "+product.toString());
		
		// Model 과 View 연결
		model.addAttribute("product", product);
		model.addAttribute("menu", menu);
		
		// if문으로 manage type마다 결과값다르게 보내기
		if(menu.equals("manage")) {
			return "forward:/product/updateProduct?prodNo="+prodNo;
		} else {
			return  "forward:/product/getProduct.jsp";
		}
	}
	
	//@RequestMapping("/updateProductView.do")
	@GetMapping("updateProduct")
	public String updateProduct( @RequestParam("prodNo") int prodNo,
											@RequestParam("menu") String menu,
											Model model ) throws Exception{

		System.out.println("/updateProductView.do");
		System.out.println("prodNo 넘어오는지 : "+prodNo);
		System.out.println("menu 넘어오는지 : "+menu);
		
		//Business Logic
		Product product  = productService.getProduct(prodNo);
		// Model 과 View 연결
		model.addAttribute("product", product);
		model.addAttribute("menu", menu);
				
		return "forward:/product/updateProduct.jsp";
				
	}
	
	@PostMapping("updateProduct")
	public String updateProduct( @ModelAttribute("product") Product product,
										@RequestParam("menu") String menu,
										Model model) throws Exception{

		System.out.println("/updateProduct.do");
		String date = product.getManuDate().replaceAll("-", "");
		product.setManuDate(date);
		//Business Logic
		System.out.println("product에 binding된 내용 : "+product.toString());
		System.out.println("updateProduct의 menu type : "+menu);
		
		productService.updateProduct(product);
		
		return "redirect:/product/getProduct?prodNo="+product.getProdNo();
	}
	
	
	@RequestMapping("listProduct")
	public String listProduct( @ModelAttribute("search") Search search,
									@RequestParam("menu") String menu,
									Model model 
									) throws Exception{
		
		System.out.println("/listProduct.do");
		
		System.out.println("menu type : "+menu);
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		// Business logic 수행
		Map<String , Object> map=productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		model.addAttribute("menu", menu);
		List<Product> product =(List)map.get("list");
		for(Product product1 : product ) {
			System.out.println(product1);
		}
		
		return "forward:/product/listProduct.jsp";
	}
}