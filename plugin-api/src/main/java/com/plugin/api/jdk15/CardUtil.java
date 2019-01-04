package com.plugin.api.jdk15;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.plugin.api.PathUtil;
/**
 * 自动打卡
 */
public class CardUtil implements Callable{

	private static Properties prop = new Properties();
	private static ExecutorService exec = Executors.newCachedThreadPool();
	private static String logPath;
	
	static {
		try {
			//prop.load(CardUtil.class.getResourceAsStream("CardData.txt"));
			prop.load(PathUtil.getInputStream(CardUtil.class, "CardData.txt"));
			
			String path = PathUtil.getClassesRoot();
			logPath = path.replaceFirst("/", "") + "CardResult.txt";
			File file = new File(logPath);
			
			if (!file.exists()){
				file.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
			log(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		String ids = (String)prop.get("CardPhisicalNo");
		System.out.println("===卡号集合："+ids);
		if(ids != null && !"".equals(ids)){
			String[] idArray = ids.split(",");
			if(idArray.length==1){
				System.out.println("===卡号1："+ids);
				autoCard();
			}else{
				CardUtil card = new CardUtil();
				for (int i = 0; i < idArray.length; i++) {
					String cardPhisicalNo = idArray[i];
					Object cardTime = card.autoCards(cardPhisicalNo);
					if (cardTime == null) {
						log(card.getFormat(new Date()) + " 打卡失败！");
					} else {
						log("卡号"+i+1+"："+cardPhisicalNo+"  打卡成功,您的打卡时间为：" + cardTime);
					}
				}
			}
		}
	}
	
	/**
	 * 自动打卡(多张)
	 */
	private String autoCards(String cardPhisicalNo){
		Calendar cal = Calendar.getInstance();
		boolean isInRegion = region1(cal) || region2(cal) || region3(cal) || region4(cal);
		if (!isInRegion) {
			log("不在打卡时间范围内");
			return null;
		}
		try {
			if (region1(cal)) {
				cal.set(Calendar.HOUR_OF_DAY, 8);
				cal.set(Calendar.MINUTE, 05);
			}
			if (region2(cal)) {
				cal.set(Calendar.HOUR_OF_DAY, 12);
				cal.set(Calendar.MINUTE, 00);
			}
			if (region3(cal)) {
				cal.set(Calendar.HOUR_OF_DAY, 14);
				cal.set(Calendar.MINUTE, 0);
			}
			if (region4(cal)) {
				cal.set(Calendar.HOUR_OF_DAY, 17);
				cal.set(Calendar.MINUTE, 30);
			}
			String resultXml = card(cal,cardPhisicalNo); //调用打卡
			if (!dealResult(resultXml)) {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log(e.getMessage());
			return null;
		}
		return getFormat(cal.getTime());
	}
	
	/**
	 * 自动打卡（单张）
	 */
	private static void autoCard() {
		CardUtil card = new CardUtil();
		Future future = exec.submit(card);
		Object cardTime = null;
		try {
			cardTime = future.get(10, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
			log(e.getMessage());
		}
		if (cardTime == null) {
			log(card.getFormat(new Date()) + " 打卡失败！");
		} else {
			log("打卡成功,您的打卡时间为：" + cardTime);
		}
		exec.shutdown();
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 打卡规则,8点10分运行,刷8:05的卡,12:10运行,刷12:00的卡,2:10运行,刷2:00的卡,17:35运行,刷17:30的卡
	 */
	public Object call() throws Exception {
		Calendar cal = Calendar.getInstance();
		boolean isInRegion = region1(cal) || region2(cal) || region3(cal) || region4(cal);
		if (!isInRegion) {
			log("不在打卡时间范围内");
			return null;
		}
		try {
			if (region1(cal)) {
				cal.set(Calendar.HOUR_OF_DAY, 8);
				cal.set(Calendar.MINUTE, 05);
			}
			if (region2(cal)) {
				cal.set(Calendar.HOUR_OF_DAY, 12);
				cal.set(Calendar.MINUTE, 00);
			}
			if (region3(cal)) {
				cal.set(Calendar.HOUR_OF_DAY, 14);
				cal.set(Calendar.MINUTE, 0);
			}
			if (region4(cal)) {
				cal.set(Calendar.HOUR_OF_DAY, 17);
				cal.set(Calendar.MINUTE, 30);
			}
			String resultXml = card(cal,(String)prop.get("CardPhisicalNo")); //调用打卡
			if (!dealResult(resultXml)) {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log(e.getMessage());
			return null;
		}
		return getFormat(cal.getTime());
	}

	//校验打卡结果
	private boolean dealResult(String resultXml) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new ByteArrayInputStream(resultXml.getBytes()));
			NodeList nodeList = doc.getElementsByTagName("errMsg");
			if (nodeList.getLength() > 0) {
				Node node = nodeList.item(0);
				String textContent = node.getTextContent();
				if (textContent != null && !textContent.equals("")) {
					log("打卡失败，返回结果为：" + textContent);
					return false;
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}

	/**
	 * 格式化日期
	 */
	private String getFormat(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}

	/**
	 * 打卡
	 */
	private String card(Calendar cal,String cardPhisicalNo) throws Exception {
		String path = "http://61.154.9.84:6000/ECYKShellService/ECYKShellService.asmx";
		String result = readFromURL(path, getXMLContent(cal,cardPhisicalNo));
		return result;
	}

	/**
	 * 打卡程序(实现)
	 */
	private static String readFromURL(String path, String xml) throws MalformedURLException, IOException {
		URL url = new URL(path);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setDoOutput(true);
		con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; MS Web Services Client Protocol 2.0.50727.42)");
		con.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
		// con.setRequestProperty("SOAPAction",
		// "\"http://tempuri.org/TestConnectDb\"");
		con.setRequestProperty("SOAPAction", "\"http://tempuri.org/SaveRollBookData\"");
		con.setRequestProperty("Expect", "100-continue");
		con.connect();
		BufferedOutputStream out = new BufferedOutputStream(con.getOutputStream());
		out.write(xml.getBytes());
		out.close();

		BufferedInputStream in = new BufferedInputStream(con.getInputStream());
		byte[] results = new byte[in.available()];
		in.read(results);
		in.close();
		con.disconnect();
		return new String(results);
	}

	/**
	 * 传递的数据
	 */
	private String getXMLContent(Calendar cal,String cardPhisicalNo) {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		sb.append("<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">");
		sb.append("<soap12:Header>");
		sb.append("<MyHeader xmlns=\"http://tempuri.org/\">");
		sb.append("<key>fjtelecom</key>");
		sb.append("</MyHeader>");
		sb.append("</soap12:Header>");
		sb.append("<soap12:Body>");
		sb.append("<SaveRollBookData xmlns=\"http://tempuri.org/\">");
		sb.append("<infos>");
		sb.append("<CRollBookInfo>");
		sb.append("<ID>1</ID>");
		sb.append("<CardPhisicalNo>");
		sb.append(cardPhisicalNo);
		sb.append("</CardPhisicalNo>");
		sb.append("<RollTime>" + getRollTime(cal) + "</RollTime>");
		sb.append("<TerminalNo>XM001</TerminalNo>");
		sb.append("<IP>192.168.0.43</IP>");
		sb.append("<RollBookResult>true</RollBookResult>");
		sb.append("</CRollBookInfo>");
		sb.append("</infos>");
		sb.append("<errMsg></errMsg>");
		sb.append("</SaveRollBookData>");
		sb.append("</soap12:Body>");
		sb.append("</soap12:Envelope>");
		return sb.toString();
	}

	/**
	 * 返回类似格式字符串:2009-07-09T18:03:43.2324
	 */
	private String getRollTime(Calendar cal) {
		String template = "yyyy-MM-ddThh:mm:ss.XXXX";
		template = template.replace("yyyy-MM-dd", getFormat(cal.getTime()).substring(0, 10));
		if (cal.get(Calendar.HOUR_OF_DAY) == 12 || cal.get(Calendar.HOUR_OF_DAY) == 14)
			cal.add(Calendar.MINUTE, getRandom10());
		else
			cal.add(Calendar.MINUTE, getRandom5());
		cal.add(Calendar.SECOND, new Random().nextInt(60));
		template = template.replace("hh:mm:ss", getFormat(cal.getTime()).substring(11, 19));
		template = template.replace("XXXX", getXXXX());
		return template;
	}

	private int getRandom10() {
		return (int) Math.round(Math.random() * 10);
	}

	private int getRandom5() {
		return getRandom10() % 5;
	}

	private String getXXXX() {
		String result = getRandom10() + "" + getRandom10() + "" + getRandom10() + "" + getRandom10();
		return result.substring(0, 4);
	}

	/**
	 * 产生日志
	 */
	private static void log(String message) {
		System.out.println("===产生日志");
		try {
			StringBuilder sb = new StringBuilder();
			BufferedReader in = new BufferedReader(new FileReader(logPath));
			while (in.ready()) {
				System.out.println(in.readLine());
				sb.append(in.readLine());
				sb.append(System.getProperty("line.separator"));
			}
			in.close();
			sb.append(message);
			sb.append(System.getProperty("line.separator"));
			BufferedWriter out = new BufferedWriter(new FileWriter(logPath));
			out.write(sb.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 07:50-8:15
	 */
	private boolean region1(Calendar current) {
		Calendar begin = Calendar.getInstance();
		begin.set(Calendar.HOUR_OF_DAY, 7);
		begin.set(Calendar.MINUTE, 50);
		Calendar end = Calendar.getInstance();
		end.set(Calendar.HOUR_OF_DAY, 8);
		end.set(Calendar.MINUTE, 15);
		return current.compareTo(begin) >= 0 && current.compareTo(end) <= 0;
	}

	/**
	 * 11:50-12:30
	 */
	private boolean region2(Calendar current) {
		Calendar begin = Calendar.getInstance();
		begin.set(Calendar.HOUR_OF_DAY, 11);
		begin.set(Calendar.MINUTE, 50);
		Calendar end = Calendar.getInstance();
		end.set(Calendar.HOUR_OF_DAY, 12);
		end.set(Calendar.MINUTE, 30);
		return current.compareTo(begin) >= 0 && current.compareTo(end) <= 0;
	}

	/**
	 * 14:00-14:30
	 */
	private boolean region3(Calendar current) {
		Calendar begin = Calendar.getInstance();
		begin.set(Calendar.HOUR_OF_DAY, 14);
		begin.set(Calendar.MINUTE, 0);
		Calendar end = Calendar.getInstance();
		end.set(Calendar.HOUR_OF_DAY, 14);
		end.set(Calendar.MINUTE, 30);
		return current.compareTo(begin) >= 0 && current.compareTo(end) <= 0;
	}

	/**
	 * 17:30-19:00
	 */
	private boolean region4(Calendar current) {
		Calendar begin = Calendar.getInstance();
		begin.set(Calendar.HOUR_OF_DAY, 17);
		begin.set(Calendar.MINUTE, 30);
		Calendar end = Calendar.getInstance();
		end.set(Calendar.HOUR_OF_DAY, 19);
		end.set(Calendar.MINUTE, 0);
		return current.compareTo(begin) >= 0 && current.compareTo(end) <= 0;
	}

}
