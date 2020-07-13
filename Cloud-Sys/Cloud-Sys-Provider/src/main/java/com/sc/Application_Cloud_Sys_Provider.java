package com.sc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableCircuitBreaker
@RestController
public class Application_Cloud_Sys_Provider {

	public static void main(String[] args) {
		SpringApplication.run(Application_Cloud_Sys_Provider.class, args);
	}

	@Bean
	public ServletRegistrationBean<HystrixMetricsStreamServlet> getServlet() {
		HystrixMetricsStreamServlet hystrixMetricsStreamServlet = new HystrixMetricsStreamServlet();
		ServletRegistrationBean<HystrixMetricsStreamServlet> servletRegistrationBean = new ServletRegistrationBean<HystrixMetricsStreamServlet>();
		servletRegistrationBean.setServlet(hystrixMetricsStreamServlet);
		servletRegistrationBean.setLoadOnStartup(10);
		servletRegistrationBean.addUrlMappings("/hystrix.stream");
		servletRegistrationBean.setName("HystrixMetricsStreamServlet");
		return servletRegistrationBean;
	}

	@GetMapping("hello")
	public String hello() {
		return "生产者调用成功！";
	}

//	@Autowired
//	AdminLogMapper adminLogMapper;
//	@Autowired
//	private RebloomUtil rebloomUtil;
//
//	@PostConstruct
//	public void init() {
//		List<Object> ids = adminLogMapper.selectObjs(null);
//		if (ids.size() > 0) {
//			Client client = rebloomUtil.createClient();
//			String key = rebloomUtil.getRebloomKey(AdminLog.class, Constant.REBLOOM_PRE_SYS);
//			for (int i = 0; i < ids.size() / 10000; i++) {
//				int count = (i + 1) * 10000 < ids.size() ? (i + 1) * 10000 : ids.size();
//				String[] strs = new String[count - i * 10000];
//				for (int j = i * 10000; j < count; j++) {
//					strs[j - i * 10000] = ids.get(j).toString();
//				}
//				client.addMulti(key, strs);
//			}
//			int errorCount = 0;
//			for (int i = 10002; i < 20001; i++) {
//				if (client.exists(key, i + "")) {
//					errorCount++;
//				}
//			}
//			System.out.println(errorCount);
//			client.close();
//		}
//
//	}

}
