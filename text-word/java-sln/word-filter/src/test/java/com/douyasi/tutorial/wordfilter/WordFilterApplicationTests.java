package com.douyasi.tutorial.wordfilter;

import com.douyasi.tutorial.wordfilter.impl.TrieSensitiveWordFilter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SpringBootTest
class WordFilterApplicationTests {

	@Test
	void contextLoads() {
		try {
			Collection<String> badWords = loadDict();
			TrieSensitiveWordFilter filter = new TrieSensitiveWordFilter();
			filter.build(badWords);
			String text = "澳门赌场在线充值，日本 Sexy 女优裸体发牌，无限畅玩，提现秒到账";
			boolean res = filter.isMatch(text);
			List<String> hitStrArray = filter.findAll(text);
			System.out.println(res);
			System.out.println(hitStrArray);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	private Collection<String> loadDict() throws IOException {
		ClassPathResource classPathResource = new ClassPathResource("words.txt");

		// 获得File对象，当然也可以获取输入流对象
		File file = classPathResource.getFile();
		BufferedReader br = new BufferedReader(new FileReader(file));
		// 创建ArrayList集合对象
		ArrayList<String> list = new ArrayList<String>();
		// 调用字符缓冲输入流对象的方法读数据
		String line;
		while ((line = br.readLine()) != null) {
			//把读取到的字符串数据存储到集合中
			list.add(line);
		}
		// 释放资源
		br.close();
		// 遍历集合，输出结果
		return list;
	}

}
