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


//==> �ǸŰ��� RestController
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
	
	
	//setter Method ���� ����
	public PurchaseRestController(){
		System.out.println(this.getClass());
	}

	//method
	@RequestMapping("json/addPurchaseKakao")
	public String addPurchaseKakao() {
		System.out.println("īī�����̷� ���� �غ� ����");
		
		/*
		 * //Product product = productService.getProduct(prodNo); User user =
		 * (User)session.getAttribute("user"); //purchase.setPurchaseProd(product);
		 * purchase.setBuyer(user); purchaseService.addPurchase(purchase);
		 */
		
		try {
			// īī�� url ����
			String kakaoPayhost = "https://kapi.kakao.com/v1/payment/ready";
			System.out.println("�Ƴ�0");
			URL url = new URL(kakaoPayhost);
			
			System.out.println("�Ƴ�1");
			//īī������ ������ ����
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Authorization", "KakaoAK 6f84ad0bc3d8e74774a1e679aa2634da");  //admin key ���
			connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
			connection.setDoOutput(true);
			System.out.println("�Ƴ�2");
			String parameter = "cid=TC0ONETIME"
					+"&partner_order_id=partner_order_id"
					+"&partner_user_id=partner_user_id"
					+"&item_name=apple"//+purchase.getPurchaseProd().getProdName()
					+"&quantity=1"
					+"&total_amount=3000"
					+"&vat_amount=200"
					+"&tax_free_amount=0"
					+"&approval_url=http://127.0.0.1:8080/success" // ���� ������ ȭ��
					+"&fail_url=http://127.0.0.1:8080/fail"	// ���� ���н�
					+"&cancel_url=http://127.0.0.1:8080/cancel"; // ���� ��ҽ�
			System.out.println("�Ƴ�2-1");
			
			//Data �����غ� �Ϸ�
			OutputStream send = connection.getOutputStream();
			System.out.println("�Ƴ�2-2");
			DataOutputStream dataSend = new DataOutputStream(send);
			System.out.println("�Ƴ�2-3");
			dataSend.writeBytes(parameter);
			dataSend.close();
			System.out.println("�Ƴ�3");
			//���� ���� ��ȣ Ȯ��
			int sendResult = connection.getResponseCode();
			System.out.println("�� ���������� : "+sendResult);
			
			InputStream response;
			if(sendResult == 200) {
				response = connection.getInputStream();
			}else {
				response = connection.getErrorStream();
				System.out.println("�ȵȴ���");
			}
			
			// JSON Data �б�
			InputStreamReader read = new InputStreamReader(response);
			BufferedReader change = new BufferedReader(read);
			System.out.println(change.readLine());
			return change.readLine();
			
		}catch(MalformedURLException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
		System.out.println("īī�����̷� ���� �غ� ����");
		return "";
		
	}
	
}