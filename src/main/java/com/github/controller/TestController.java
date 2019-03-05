package com.github.controller;

import com.github.service.FamilyDoctorService;
import com.github.util.POIExcelRead;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@Controller
@RequestMapping("t")
public class TestController {

	private AtomicInteger count = new AtomicInteger();
	@Resource private FamilyDoctorService familyDoctorService;
	@Resource private ThreadPoolTaskExecutor threadPoolTaskExecutor;


	@ResponseBody
	@GetMapping({"", "/"})
	public Object index() throws IOException {

		List<List<String>> userList = POIExcelRead.ReadXLSX(new File("/Users/imiracle/Desktop/import.xlsx"));
		for (final List<String> user : userList) {

			count.incrementAndGet();
			familyDoctorService.importUser(user);
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
