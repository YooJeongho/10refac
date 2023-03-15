package com.model2.mvc.web.product;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.user.UserService;


//==> ��ǰ���� Controller
@Controller
@RequestMapping("/product/*")
public class ProductController {
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//setter Method ���� ����
		
	public ProductController(){
		System.out.println(this.getClass());
	}
	
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml ���� �Ұ�
	//==> �Ʒ��� �ΰ��� �ּ��� Ǯ�� �ǹ̸� Ȯ�� �Ұ�
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
//	@PostMapping("addProduct")
//	public String addProduct( @ModelAttribute("product") Product product,
//									@RequestParam("file") MultipartFile file) throws Exception {
//
//		System.out.println("/addUser.do");
//		System.out.println("product domain��ü�� binding �Ǿ����� : "+product);
//		String date = product.getManuDate().replaceAll("-", "");
//		product.setManuDate(date);
//		System.out.println(product.toString());
//		
//		// file���� ��� ����
//		String temDir = "C:\\Users\\Bitcamp\\git\\07.Model2MVCShop(URI,pattern)\\07.Model2MVCShop(URI,pattern)\\src\\main\\webapp\\images\\uploadFiles";
//		
//		if(file != null && file.getSize()>0) {
//			// file�� ���� �̸� �����ͼ� Random�� �̸����� ����
//			UUID uuid = UUID.randomUUID();
//			String originalName = file.getOriginalFilename();
//			System.out.println("originalName : "+originalName);
//			String convertFileName = uuid.toString()+"_"+file.getOriginalFilename();
//			
//			// ������ ��� ���� �� ����
//			file.transferTo(new File((temDir)+"\\"+convertFileName));
//			System.out.println("file name: "+convertFileName);
//			
//			product.setFileName(convertFileName);
//		}
		
	
		//multi file upload
		@PostMapping("addProduct")
		public String addProduct( @ModelAttribute("product") Product product,
										@RequestParam("file") List<MultipartFile> files) throws Exception {

			System.out.println("/addUser.do");
			System.out.println("product domain��ü�� binding �Ǿ����� : "+product);
			String date = product.getManuDate().replaceAll("-", "");
			product.setManuDate(date);
			System.out.println(product.toString());
			
			// file���� ��� ����
			String temDir = "C:\\Users\\Bitcamp\\git\\07.Model2MVCShop(URI,pattern)\\07.Model2MVCShop(URI,pattern)\\src\\main\\webapp\\images\\uploadFiles";
			String prodTemp = "";
			UUID uuid = UUID.randomUUID();
			int temp = 1;
			System.out.println("jsp���� �Ѿ�� �̹��� ����Ʈ : "+files);
			
			if(files != null && files.size() != 0) {
				
				
				for(MultipartFile multi : files) {
					
					String originalName = multi.getOriginalFilename();
					System.out.println("original File name : "+originalName);
					
					String convertFileName = uuid.toString()+temp+"_"+originalName;
					prodTemp= prodTemp+convertFileName+((temp<files.size()) ? "," : "");
					
					// ��ο� ����
					multi.transferTo(new File((temDir)+"\\"+convertFileName));
					temp++;
					System.out.println("originalName : "+originalName);
					System.out.println("convertName : "+convertFileName);
				}
				// ������ ��� ���� �� ����
				
				System.out.println("product domain�� ������ ���� : "+prodTemp);
				
				product.setFileName(prodTemp);
			}
		
		
		//Business Logic
		productService.addProduct(product);
		
		
		return "forward:/product/addProduct.jsp";
	}
	
	@GetMapping("getProduct")
	public String getProduct( @RequestParam(value="menu", required = false) String menu,
								 	@RequestParam("prodNo") int prodNo,
									Model model ) throws Exception {
		
		System.out.println("/getProduct");
		
		System.out.println("prodNo �Ѿ������ : "+prodNo);
		System.out.println("menu �Ѿ������ : "+menu);
		
		if (menu == null) {
			menu = "search";
		}
		
		//Business Logic
		Product product = productService.getProduct(prodNo);
		System.out.println("FileName : "+product.getFileName());
		
		//null�� ó��
		if (product.getFileName() == null) {
			product.setFileName("");
			System.out.println("FileName : "+product.getFileName());
		}
		
		String[] files = product.getFileName().split("[,]");

		// Model �� View ����
		model.addAttribute("product", product);
		model.addAttribute("menu", menu);
		model.addAttribute("files", files);
		//model.addAttribute("filename", product.getFileName());
		// if������ manage type���� ������ٸ��� ������
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
		System.out.println("prodNo �Ѿ������ : "+prodNo);
		System.out.println("menu �Ѿ������ : "+menu);
		
		//Business Logic
		Product product  = productService.getProduct(prodNo);
		// Model �� View ����
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
		System.out.println("product�� binding�� ���� : "+product.toString());
		System.out.println("updateProduct�� menu type : "+menu);
		
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
		
		// Business logic ����
		Map<String , Object> map=productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		List<Product> list = (List)(map.get("list"));
		List <String> fileName = new ArrayList();
		
		for(Product prod : list) {
			System.out.println("listProd���� ������ prod �� filename : "+prod.getFileName());
			if (prod.getFileName() == null) {
				prod.setFileName("");
				System.out.println("FileName : "+prod.getFileName());
			}
			
			String[] files = prod.getFileName().split("[,]");
			fileName.add(files[0]);
		} 
		
		// Model �� View ����
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		model.addAttribute("menu", menu);
		model.addAttribute("fileName", fileName);
		return "forward:/product/listProduct.jsp";
	}
}