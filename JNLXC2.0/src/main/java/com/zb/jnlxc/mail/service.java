package com.zb.jnlxc.mail;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class service {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	public void send() {
		String lastip = "";
		while (true) {
			try {
				URL url = new URL("http://www.ip138.com/ip2city.asp");
				URLConnection uc = url.openConnection();
				InputStream is = uc.getInputStream();
				byte[] bs = new byte[1024];
				is.read(bs);
				String ip = new String(bs);
				is.close();
				if (!ip.equals(lastip)) {
					lastip = ip;
					// 这个类主要是设置邮件
					MailSenderInfo mailInfo = new MailSenderInfo();
					mailInfo.setMailServerHost("smtp.163.com");
					mailInfo.setMailServerPort("25");
					mailInfo.setValidate(true);
					mailInfo.setUserName("stevenbobo1@163.com");
					mailInfo.setPassword("stevenbobo01123");// 您的邮箱密码
					mailInfo.setFromAddress("stevenbobo1@163.com");
					mailInfo.setToAddress("279360619@qq.com");
					mailInfo.setSubject("IP地址");
					mailInfo.setContent(ip);
					// 这个类主要来发送邮件
					SimpleMailSender sms = new SimpleMailSender();
					sms.sendHtmlMail(mailInfo);// 发送html格式

					logger.info(ip);

					Thread.sleep(1000 * 60);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
