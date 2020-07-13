package com.sc.sys.consumer.controller;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.springframework.context.annotation.Configuration;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

@Configuration
public class MyRule extends AbstractLoadBalancerRule implements Cloneable,Comparable<MyRule>,InvocationHandler {

	@Override
	public Server choose(Object key) {
		ILoadBalancer lb = getLoadBalancer();
		List<Server> servers = lb.getReachableServers();
		return servers.get(0);
	}

	@Override
	public void initWithNiwsConfig(IClientConfig clientConfig) {

	}

	@Override
	public int compareTo(MyRule o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		Callable<String> callable = new Callable<String>() {
			@Override
			public String call() throws Exception {
				System.out.println("线程名称===="+Thread.currentThread().getName());
				return "callable";
			}
		};
		ExecutorService executor = Executors.newCachedThreadPool();
		Future<String> future = executor.submit(callable);
		System.out.println(future.get());
		FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {

			@Override
			public String call() throws Exception {
				System.out.println("线程名称===="+Thread.currentThread().getName());
				return "FutureTask";
			}
		});
//		new Thread(futureTask).start();
//		executor.submit(futureTask);
//		System.out.println(futureTask.get());
		futureTask.run();
		System.out.println(futureTask.get());
	}

}
