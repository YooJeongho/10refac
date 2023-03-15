package com.model2.mvc.web.kakaopay;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.service.domain.KakaoPayApprove;
import com.model2.mvc.service.domain.KakaoPayReadyResponse;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.kakaopay.KakaoPayService;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.user.UserService;

@RestController
@RequestMapping("/kakaoPay/*")
public class KakaoPayRestController {
	
	//field
	@Autowired
	@Qualifier("kakaoPayService")
	private KakaoPayService kakaoPayService;
	
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	private static Purchase purchase;
	
	//construcor
	public KakaoPayRestController() {
	}
	
	
	//method
	@PostMapping("json/addPurchaseKakaoReady/{prodNo}")
	public KakaoPayReadyResponse readyKakaoPay(@ModelAttribute("purchase") Purchase purchase, @PathVariable int prodNo, HttpSession session) throws Exception  {
		System.out.println("KakaoPayRestController�� readyKakaoPay() ����");
		String userId = ((User)session.getAttribute("user")).getUserId();
		
		//purchase�� �������� �� ���� ���� ����
		User user = userService.getUser(userId);
		Product product = productService.getProduct(prodNo);
		//purchase ��ü ����
		purchase.setBuyer(user);
		purchase.setPurchaseProd(product);
		purchase.setTranCode("1");
		KakaoPayRestController.purchase = purchase;
		KakaoPayReadyResponse jsonData = kakaoPayService.kakaoPayReady(product);
		
		System.out.println("KakaoPayRestController�� readyKakaoPay() ����");
		return jsonData;
		
	}
	
	@RequestMapping("json/success")
	public ModelAndView kakaoPayApprove(@RequestParam("pg_token") String pgToken) throws Exception {
		System.out.println("KakaoPayRestController�� kakaoPayApprove() ����");
		KakaoPayApprove kakoPayApprove = kakaoPayService.kakaoPayAprove(pgToken);
		System.out.println("�߱޹��� pgToken : "+pgToken);
		
		System.out.println("KakaoPayRestController�� kakaoPayApprove() ����");
		
		//���� �߰�
		purchaseService.addPurchase(purchase);
		System.out.println("addPurchase ���� ����");
		System.out.println("purchase domain instance�� ����� product ���� : "+purchase.getPurchaseProd());
		//ȭ����ȯ
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/purchase/addPurchase.jsp");
		modelAndView.addObject("purchase", purchase);
		
		return modelAndView;
		
	}
	
	

}
