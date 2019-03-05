package com.github.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class FamilyDoctorService {

	@Value("${data}") private String data;
	@Resource private RestTemplate restTemplate;

    @PostConstruct
    public void postConstruct() {
	    data = new String(data.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }

	public JSONObject getUser(String idNumber) {

		String requestUrl = "http://182.150.40.144:8092/pcn-core/*.jsonRequest";

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Service-Method", "signVerification");
		headers.set("X-Service-Id", "pcn.myResidentDoctorService");
		headers.set("X-Access-Token", "5b3ae688-3ee8-4aea-84eb-64697aeb1873");

		List<String> body = new ArrayList<>();
		body.add(idNumber);
		body.add("01");

		HttpEntity httpEntity = new HttpEntity(body, headers);
		JSONObject jsonObject = restTemplate.postForObject(requestUrl, httpEntity, JSONObject.class);

		return jsonObject;

	}

	public JSONObject sign(String body) {

		String requestUrl = "http://182.150.40.144:8092/pcn-core/*.jsonRequest";

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Service-Method", "addSignResident");
		headers.set("X-Service-Id", "pcn.myResidentDoctorService");
		headers.set("X-Access-Token", "5b3ae688-3ee8-4aea-84eb-64697aeb1873");

		HttpEntity httpEntity = new HttpEntity(JSON.parseArray(body), headers);
		JSONObject jsonObject = restTemplate.postForObject(requestUrl, httpEntity, JSONObject.class);

		return jsonObject;

	}

	@Async
	public void importUser(List<String> user) {

		String idCard = user.get(3);
		JSONObject userInfo = this.getUser(idCard);
		boolean sign = "1".equals(userInfo.getJSONObject("body").getString("isSign"));
		if (sign) {
			return;
		}

		String name = user.get(1);
		String sex = user.get(2);
		String sexType = "男".equals(sex) ? "1" : "2";

		String birthday = idCard.substring(6, 14);
		birthday = birthday.substring(0, 4) + "-" + birthday.substring(4, 6) + "-" + birthday.substring(6);

		String parent = user.get(4);
		String phone = user.get(5);
		String address = user.get(6);
		address = address.replace("四川省", "").replace("四川", "");
		address = address.replace("成都市", "").replace("成都", "");
		address = address.replace("成华区", "").replace("成华", "");


		String body = data.replace("@{address}", address);
		body = body.replace("@{birthday}", birthday);
		body = body.replace("@{idCard}", idCard);
		body = body.replace("@{name}", name);
		body = body.replace("@{phone}", phone);
		body = body.replace("@{sex}", sex);
		body = body.replace("@{sexType}", sexType);

		try {
			this.sign(body);
		} catch (Exception e) {
			System.err.println("用户身份证号码错误: " + user);
		}
	}



}
