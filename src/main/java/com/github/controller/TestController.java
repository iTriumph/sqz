package com.github.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.service.FamilyDoctorService;
import com.github.util.POIExcelRead;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@Controller
@RequestMapping("t")
public class TestController {

	@Value("${data}")
	private String data;
	@Resource
	private FamilyDoctorService familyDoctorService;
	@Resource
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;
	private AtomicInteger count = new AtomicInteger();


	@ResponseBody
	@GetMapping({"", "/"})
	public Object index() throws IOException {

		data = new String(data.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

		List<List<String>> userList = POIExcelRead.ReadXLSX(new File("/Users/baby/Desktop/import.xlsx"));
		for (final List<String> user : userList) {

			threadPoolTaskExecutor.execute(
					new Thread() {
						@Override
						public void run() {

							String idCard = user.get(3);
							JSONObject userInfo = familyDoctorService.getUser(idCard);
							boolean sign = "1".equals(userInfo.getJSONObject("body").getString("isSign"));
							if (sign) {
								count.incrementAndGet();
								return;
							}

							if (userInfo.getJSONObject("body").getString("residentInfo") == null) {
								System.err.println("用户身份证号码错误: " + user);
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
							address = address.replace("四川", "").replace("四川省", "");
							address = address.replace("成都市", "").replace("成都", "");
							address = address.replace("成华区", "").replace("成华", "");

							String body = data.replace("@{address}", address);
							body = body.replace("@{birthday}", birthday);
							body = body.replace("@{idCard}", idCard);
							body = body.replace("@{name}", name);
							body = body.replace("@{phone}", phone);
							body = body.replace("@{sex}", sex);
							body = body.replace("@{sexType}", sexType);

							System.err.println(familyDoctorService.sign(body));
						}
					}
			);
		}

		return "success";
	}

	@ResponseBody
	@GetMapping("count")
	public Object count() {
		return count.get();
	}

	@ResponseBody
	@GetMapping("id")
	public Object id() {
//		return familyDoctorService.getUser("510108201208030914");
		return familyDoctorService.getUser("510129198712185828");
	}

	public static void main(String[] args) {
		String idCard = "510108201208030914";
	}

}
