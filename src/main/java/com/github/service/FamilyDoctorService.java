package com.github.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

@Service
public class FamilyDoctorService {

	@Resource private RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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


	public JSONObject getSyncLog(Integer version) {

		logger.info("Start Pull Passport Server's Synclog, Version: {}", version);

		String requestUrl = "https://passport2-api.chaoxing.com/api/synclog?version={version}&enc={sign}";
		Map<String, Object> uriVariables = new HashMap<>();
		uriVariables.put("version", version);
		uriVariables.put("sign", DigestUtils.md5Hex(version + "uWwjeEKsri"));

		String responseBody = restTemplate.getForObject(requestUrl, String.class, uriVariables);
		JSONObject jsonObject = JSON.parseObject(responseBody);

		return jsonObject;
	}

	public Integer getSyncLogVersion() {

		String requestUrl = "https://passport2.chaoxing.com/adminapi/getmaxversiosn";
		String responseBody = restTemplate.getForObject(requestUrl, String.class);
		return JSON.parseObject(responseBody).getInteger("version");
	}






	public JSONObject getUserInfo(Integer uid) {

		String requestUrl = "http://passport2.chaoxing.com/api/userinfo?uid={uid}&enc={sign}";
		Map<String, Object> uriVariables = new HashMap<>();
		uriVariables.put("uid", uid);
		uriVariables.put("sign", DigestUtils.md5Hex(uid + "uWwjeEKsri"));

		String responseBody = restTemplate.getForObject(requestUrl, String.class, uriVariables);
		return JSON.parseObject(responseBody);
	}

	public JSONObject getOrganization(Integer fid) {
//https://passport2-api.chaoxing.com/api/schoolinfo?schoolid=30575&enc=0120f40294e51663dec5da0659f9d015

		String requestUrl = "https://passport2.chaoxing.com/api/schoolinfo?schoolid={fid}&enc={sign}";
		Map<String, Object> uriVariables = new HashMap<String, Object>();
		uriVariables.put("fid", fid);
		uriVariables.put("sign", DigestUtils.md5Hex(fid + "uWwjeEKsri"));

		String responseBody = restTemplate.getForObject(requestUrl, String.class, uriVariables);
		return JSON.parseObject(responseBody);
	}





	public void searchUnit(String keyword) {
		String requestUrl = "http://passport2.chaoxing.com/api/searchunits";
		MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
		body.add("keyword", keyword);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		HttpEntity httpEntity = new HttpEntity(body, headers);
		String responseBody = restTemplate.postForObject(requestUrl, httpEntity, String.class);
		System.err.println(responseBody);
	}


	public static void main(String[] args) throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));


		MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
		body.add("memo", "成都");

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8");
		headers.add(HttpHeaders.USER_AGENT, "iMiracle");
		HttpEntity<?> request = new HttpEntity<Object>(body, headers);

		String content = restTemplate.postForObject("http://baidu.com", request, String.class);
		System.err.println(content);


		RequestEntity requestEntity = RequestEntity.post(new URI("http://teacher.chaoxing.com")).accept(MediaType.ALL).body(body);

		ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
		System.err.println(responseEntity.getBody());
		System.err.println(responseEntity.getHeaders());
		System.err.println(responseEntity.getHeaders().get("Set-Cookie"));


		String requestUrl = "https://passport2.chaoxing.com/api/searchunits";
		body = new LinkedMultiValueMap<String, String>();
		body.add("keyword", "成都");
		HttpEntity httpEntity = new HttpEntity(body);
		Map map = restTemplate.postForObject(requestUrl, httpEntity, Map.class);
		System.err.println(map);
	}









	public boolean login(Integer fid, String userName, HttpServletResponse response) {
		String requestUrl = "http://passport2.chaoxing.com/api/login";
		MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
		body.add("schoolid", String.valueOf(fid));
		body.add("name", userName);
		body.add("enc", DigestUtils.md5Hex(fid + userName + DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd") + "jsDyctOCn7qHzRvrtcJ6"));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		HttpEntity httpEntity = new HttpEntity(body, headers);
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(requestUrl, httpEntity, String.class);
		JSONObject jsonObject = JSON.parseObject(responseEntity.getBody());
		if ("0".equals(jsonObject.getString("status"))) {
			List<String> cookieStringList = responseEntity.getHeaders().get("Set-Cookie");
			for (String cookieString : cookieStringList) {
				Cookie cookie = parseCookie(cookieString);
				response.addCookie(cookie);
			}
			return true;
		}

		return false;
	}

	public static Cookie parseCookie(String cookieString) {

		Cookie cookie = null;
		StringTokenizer tokenizer = new StringTokenizer(cookieString, ";");

		String nameValuePair = tokenizer.nextToken();
		int index = nameValuePair.indexOf('=');
		if (index != -1) {
			String name = nameValuePair.substring(0, index).trim();
			String value = nameValuePair.substring(index + 1).trim();
			cookie = new Cookie(name, value);
		} else {
			throw new IllegalArgumentException("Invalid cookie name-value pair");
		}

		// remaining name-value pairs are cookie's attributes
		while (tokenizer.hasMoreTokens()) {
			nameValuePair = tokenizer.nextToken();
			index = nameValuePair.indexOf('=');
			String name, value;
			if (index != -1) {
				name = nameValuePair.substring(0, index).trim();
				value = nameValuePair.substring(index + 1).trim();
			} else {
				name = nameValuePair.trim();
				value = null;
			}

			switch (name) {
				case "Domain":
					cookie.setDomain(value);
					break;
				case "Path":
					cookie.setPath(value);
					break;
				case "HttpOnly":
					cookie.setHttpOnly(true);
					break;
				case "Expires":
//					cookie.setMaxAge(Integer.MAX_VALUE);
					break;
				default:
					// ignore...
			}
		}

		return cookie;
	}





    /*
    public static List getOrganizationUserList(Integer fid) {
        String requestUrl = "http://passport2.chaoxing.com/api/unitusers?fid=%s&enc=%s&start=11&size=1000";
        requestUrl = String.format(requestUrl, fid, DigestUtils.md5Hex(String.valueOf(fid) + DateFormatUtils.format(new Date(), "yyyy-MM-dd") + "jsDyctOCn7qHzRvrtcJ6"));
        System.err.println(requestUrl);
        System.err.println(HttpClientJdk.httpRequestGet(requestUrl));
        return null;
    }

    public static void search() {
        String requestUrl = "http://passport2.chaoxing.com/api/searchunits?keyword=%s";
        try {
            requestUrl = String.format(requestUrl, URLEncoder.encode("成都", "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//        System.err.println(HttpClientUtils.post(requestUrl, null));
        System.err.println(HttpClientJdk.httpRequest(requestUrl, "POST", ""));
    }

    /*public static void login(Integer fid, String userName, HttpServletResponse response, HttpServletRequest request) {
        String loginUrl = "http://passport2.chaoxing.com/api/login?schoolid=%s&name=%s&enc=%s";
        loginUrl = String.format(loginUrl, fid, userName, DigestUtils.md5Hex(String.valueOf(fid) + userName + DateFormatUtils.format(new Date(), "yyyy-MM-dd") + "jsDyctOCn7qHzRvrtcJ6"));
        String result = HttpClientUtils.get(loginUrl, true, response);
        LoginResultBean loginResultBean = JSON.parseObject(result, LoginResultBean.class);
        if(loginResultBean.isResult()){
            Student student = new Student();
            student.setUid(loginResultBean.getUid());
            student.setRealName(loginResultBean.getRealname());
            student.setMail(loginResultBean.getEmail());
            student.setLoginName(loginResultBean.getUname());
            student.setRoleId(loginResultBean.getRoleid());
            request.getSession().setAttribute(Constants.SESSION_USER, student);
        }
    }*/


}
