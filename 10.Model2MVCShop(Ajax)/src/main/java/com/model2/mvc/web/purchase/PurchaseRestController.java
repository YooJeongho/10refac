package com.model2.mvc.web.purchase;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.user.UserService;


//==> 판매관리 RestController
@RestController
@RequestMapping("/purchase/*")
public class PurchaseRestController {
	
	///Field
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	
	//setter Method 구현 않음
	public PurchaseRestController(){
		System.out.println(this.getClass());
	}

	//method
	@RequestMapping("json/addPurchaseKakao")
	public String addPurchaseKakao() {
		System.out.println("카카오페이로 결제 준비 시작");
		
		/*
		 * //Product product = productService.getProduct(prodNo); User user =
		 * (User)session.getAttribute("user"); //purchase.setPurchaseProd(product);
		 * purchase.setBuyer(user); purchaseService.addPurchase(purchase);
		 */
		
		try {
			// 카카오 url 설정
			String kakaoPayhost = "https://kapi.kakao.com/v1/payment/ready";
			System.out.println("됐냐0");
			URL url = new URL(kakaoPayhost);
			
			System.out.println("됐냐1");
			//카카오페이 서버와 연동
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Authorization", "KakaoAK 6f84ad0bc3d8e74774a1e679aa2634da");  //admin key 사용
			connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
			connection.setDoOutput(true);
			System.out.println("됐냐2");
			String parameter = "cid=TC0ONETIME"
					+"&partner_order_id=partner_order_id"
					+"&partner_user_id=partner_user_id"
					+"&item_name=apple"//+purchase.getPurchaseProd().getProdName()
					+"&quantity=1"
					+"&total_amount=3000"
					+"&vat_amount=200"
					+"&tax_free_amount=0"
					+"&approval_url=http://127.0.0.1:8080/success" // 결제 성공시 화면
					+"&fail_url=http://127.0.0.1:8080/fail"	// 결제 실패시
					+"&cancel_url=http://127.0.0.1:8080/cancel"; // 결제 취소시
			System.out.println("됐냐2-1");
			
			//Data 보낼준비 완료
			OutputStream send = connection.getOutputStream();
			System.out.println("됐냐2-2");
			DataOutputStream dataSend = new DataOutputStream(send);
			System.out.println("됐냐2-3");
			dataSend.writeBytes(parameter);
			dataSend.close();
			System.out.println("됐냐3");
			//전송 유무 번호 확인
			int sendResult = connection.getResponseCode();
			System.out.println("잘 보내졌는지 : "+sendResult);
			
			InputStream response;
			if(sendResult == 200) {
				response = connection.getInputStream();
			}else {
				response = connection.getErrorStream();
				System.out.println("안된다잉");
			}
			
			// JSON Data 읽기
			InputStreamReader read = new InputStreamReader(response);
			BufferedReader change = new BufferedReader(read);
			System.out.println(change.readLine());
			return change.readLine();
			
		}catch(MalformedURLException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
		System.out.println("카카오페이로 결제 준비 종료");
		return "";
		
	}
	
}