package com.model2.mvc.service.kakaopay;

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

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.model2.mvc.service.domain.KakaoPayApprove;
import com.model2.mvc.service.domain.KakaoPayReadyResponse;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;

@Service("kakaoPayService")
public class KakaoPayService {
	
	//field
	static final String cid = "TC0ONETIME";		// 테스트 가맹점 Id
	static final String adminKey = "6f84ad0bc3d8e74774a1e679aa2634da";	//admin key
	private KakaoPayReadyResponse kakaoReady;		//kakaopay ready domain
	
	//constructor
	public KakaoPayService() {
	}
	
	
//	 public String kakaoPayReady() throws Exception {
//		// 카카오 url 설정
//					String kakaoPayhost = "https://kapi.kakao.com/v1/payment/ready";
//					System.out.println("됐냐0");
//					URL url = new URL(kakaoPayhost);
//					
//					System.out.println("됐냐1");
//					//카카오페이 서버와 연동
//					HttpURLConnection connection = (HttpURLConnection)url.openConnection();
//					connection.setRequestMethod("POST");
//					connection.setRequestProperty("Authorization", "KakaoAK 6f84ad0bc3d8e74774a1e679aa2634da");  //admin key 사용
//					connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//					connection.setDoOutput(true);
//					System.out.println("됐냐2");
//					String parameter = "cid=TC0ONETIME"
//							+"&partner_order_id=partner_order_id"
//							+"&partner_user_id=partner_user_id"
//							+"&item_name=apple"//+purchase.getPurchaseProd().getProdName()
//							+"&quantity=1"
//							+"&total_amount=3000"
//							+"&vat_amount=200"
//							+"&tax_free_amount=0"
//							+"&approval_url=http://127.0.0.1:8080/kakaoPay/json/success" // 결제 성공시 화면
//							+"&fail_url=http://127.0.0.1:8080/kakaoPay/json/fail"	// 결제 실패시
//							+"&cancel_url=http://127.0.0.1:8080/kakaoPay/json/cancel"; // 결제 취소시
//					System.out.println("됐냐2-1");
//					
//					//Data 보낼준비 완료
//					OutputStream send = connection.getOutputStream();
//					System.out.println("됐냐2-2");
//					DataOutputStream dataSend = new DataOutputStream(send);
//					System.out.println("됐냐2-3");
//					dataSend.writeBytes(parameter);
//					dataSend.close();
//					System.out.println("됐냐3");
//					//전송 유무 번호 확인
//					int sendResult = connection.getResponseCode();
//					System.out.println("잘 보내졌는지 : "+sendResult);
//					
//					InputStream response;
//					if(sendResult == 200) {
//						System.out.println("잘 보내졌는지 : "+sendResult);
//						response = connection.getInputStream();
//					}else {
//						response = connection.getErrorStream();
//						System.out.println("안된다잉");
//					}
//					
//					// JSON Data 읽기
//					InputStreamReader read = new InputStreamReader(response);
//					BufferedReader change = new BufferedReader(read);
//					
//					//System.out.println(change.readLine());
//					System.out.println("RestController의 readyKakaoPay() 종료");
//					
//					Map <String, Object> map =  new HashMap<>();
//					
//					String jsonData = change.readLine();
//					System.out.println(change.readLine());
//					/*
//					 * System.out.println("jsonData : "+jsonData); map.put("JSON", jsonData);
//					 * System.out.println("map에서 꺼내기 : "+map.get("JSON"));
//					 */
//					
//					return jsonData;
//	 }
	 
	 public KakaoPayReadyResponse kakaoPayReady(Product product) throws Exception{
		
		 HttpHeaders httpHeaders = new HttpHeaders();
		 httpHeaders.set("Authorization", "KakaoAK "+adminKey);
		 httpHeaders.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		 
		
		 MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
		 parameters.add("cid", cid);
		 parameters.add("partner_order_id", "partner_order_id");
	     parameters.add("partner_user_id", "partner_user_id");
	     parameters.add("item_name", product.getProdName() );
	     parameters.add("quantity", "1");
	     parameters.add("total_amount", product.getPrice());
	     parameters.add("vat_amount", "200");
	     parameters.add("tax_free_amount", "0");
	     parameters.add("approval_url", "http://127.0.0.1:8080/kakaoPay/json/success"); // 성공 시 redirect url
	     parameters.add("cancel_url", "http://127.0.0.1:8080/kakaoPay/json/fail"); // 취소 시 redirect url
	     parameters.add("fail_url", "http://127.0.0.1:8080/kakaoPay/json/cancel"); // 실패 시 redirect url
		 
	     HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(parameters, httpHeaders);
	     
	     RestTemplate restTemplate = new RestTemplate();
	     
	     kakaoReady = restTemplate.postForObject("https://kapi.kakao.com/v1/payment/ready", requestEntity, KakaoPayReadyResponse.class);
		 return kakaoReady;
	 }
	 
	 public KakaoPayApprove kakaoPayAprove(String pgToken) {
		 
		 System.out.println("kakaoPayAprove() 실행");
		 HttpHeaders httpHeaders = new HttpHeaders();
		 httpHeaders.set("Authorization", "KakaoAK "+adminKey);
		 httpHeaders.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		 
		 MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		 parameters.add("cid", cid);
		 parameters.add("tid", kakaoReady.getTid());
		 System.out.println("tid :"+kakaoReady.getTid());
		 parameters.add("partner_order_id", "partner_order_id");
	     parameters.add("partner_user_id", "partner_user_id");
	     parameters.add("pg_token", pgToken);
	     
	     HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, httpHeaders);
	     RestTemplate restTemplate = new RestTemplate();
		 
	     KakaoPayApprove kakaoPayApprove = restTemplate.postForObject("https://kapi.kakao.com/v1/payment/approve", requestEntity, KakaoPayApprove.class);
	     
	     System.out.println("kakaoPayAprove() 종료");
		 return kakaoPayApprove;
	 }
	 
	
	
}
