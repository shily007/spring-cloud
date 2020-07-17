package com.sc.test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.xfvape.uid.UidGenerator;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUid {
	
	@Resource
	private UidGenerator uidGenerator;

	@Test
	public void testSerialGenerate() {
	    // Generate UID
	    long uid = uidGenerator.getUID();

	    // Parse UID into [Timestamp, WorkerId, Sequence]
	    // {"UID":"180363646902239241","parsed":{    "timestamp":"2017-01-19 12:15:46",    "workerId":"4",    "sequence":"9"        }}
	    System.out.println(uidGenerator.parseUID(uid));

	}
	
	public static void main(String[] args) {
		System.out.println(LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")));
		System.out.println(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());
		System.out.println(System.currentTimeMillis());
	}

}
