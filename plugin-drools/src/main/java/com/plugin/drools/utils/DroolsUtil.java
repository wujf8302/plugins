package com.plugin.drools.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.StatefulSession;
import org.drools.compiler.DroolsParserException;
import org.drools.compiler.PackageBuilder;
import org.drools.rule.Package;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.StatelessKnowledgeSession;
import org.drools.spi.AgendaFilter;

/**
 * Drools工具类.
 * @author wujf
 */
public class DroolsUtil {
	
	private static final Log log = LogFactory.getLog(DroolsUtil.class);

	public static void runAllRules(StatelessKnowledgeSession session, Object o) {
		if (session != null && o != null) {
			session.execute(o);
		}
	}
	
	public static void runAllRules(StatefulKnowledgeSession session, Object o) {
		if (session != null && o != null) {
			session.insert(o);
			session.fireAllRules();
			session.dispose();
		}
	}
	
	//---
	
	public static List<Reader> readRules(String[] drls){
		if (drls == null || drls.length == 0) {
			return null;
		}
		List<Reader> readers = new ArrayList<Reader>();
		for (String drl: drls) {
			readers.add(readRule(drl));
		}
		return readers;
	}
	
	public static List<Reader> readRules(List<String> drls){
		if (drls == null || drls.size() == 0) {
			return null;
		}
		List<Reader> readers = new ArrayList<Reader>();
		for (String drl: drls) {
			readers.add(readRule(drl));
		}
		return readers;
	}
	
	public static Reader readRule(String drl){
		Reader reader = null;
		try {
			// Reader source = new InputStreamReader(BillTest.class.getResourceAsStream(drl),"UTF-8");
			File file = new File(drl);
			reader = new FileReader(file);
		} catch (Exception e) {
			log.error("",e);
		}
		return reader;
	}
	
	//---
	
	public static PackageBuilder checkRuleScript(PackageBuilder builder) throws RuntimeException{
		// 检查脚本是否有问题
		if (builder.hasErrors()) {
			throw new RuntimeException(builder.getErrors().toString());
		}
		return builder;
	}
	
	//---
	
	@SuppressWarnings("rawtypes")
	public static PackageBuilder getPackageBuilder(List list){
		PackageBuilder builder = new PackageBuilder();
		if(list != null){
			for (int i = 0; i < list.size(); i++) {
				Object o = list.get(i);
				Reader reader = null;
				if(o != null){
					if(o instanceof String){
						String drl = (String)o;
						reader = readRule(drl);
					}
				}else if(o instanceof Reader){
					reader = (Reader)o;
				}
				
				if(reader != null){
					try {
						builder.addPackageFromDrl(reader);
					} catch (DroolsParserException e) {
						log.error("", e);
					} catch (IOException e) {
						log.error("", e);
					}
				}
			}
		}
		
		return checkRuleScript(builder);
	}
	
	public static PackageBuilder getPackageBuilder(Reader reader) {
		PackageBuilder builder = new PackageBuilder();
		try {
			builder.addPackageFromDrl(reader);
		} catch (DroolsParserException e) {
			log.error("", e);
		} catch (IOException e) {
			log.error("", e);
		}
		return checkRuleScript(builder);
	}

	public static PackageBuilder getPackageBuilder(InputStream in, String charset) {
		try {
			return getPackageBuilder(new InputStreamReader(in, charset));
		} catch (UnsupportedEncodingException e) {
			log.error("", e);
		}
		return null;
	}

	public static PackageBuilder getPackageBuilder(InputStream in) {
		return getPackageBuilder(in, null); 
	}
	
	//---

	public static RuleBase getRuleBase(PackageBuilder builder) {
		if (builder != null) {
			Package pkg = builder.getPackage();
			RuleBase ruleBase = RuleBaseFactory.newRuleBase();
			ruleBase.addPackage(pkg);
			return ruleBase;
		}
		return null;
	}

	public static RuleBase getRuleBase(Reader reader) {
		return getRuleBase(getPackageBuilder(reader));
	}

	public static RuleBase getRuleBase(InputStream in, String charset) {
		return getRuleBase(getPackageBuilder(in, charset));
	}

	public static RuleBase getRuleBase(InputStream in) {
		return getRuleBase(getPackageBuilder(in));
	}
	
	//---

	public static StatefulSession getStatefulSession(RuleBase ruleBase) {
		if (ruleBase != null) {
			return ruleBase.newStatefulSession();
		}
		return null;
	}

	//---
	
	public static void runAllRules(StatefulSession session, Object o) {
		if (session != null && o != null) {
			session.insert(o);
			session.fireAllRules();
			session.dispose();
		}
	}
	
	public static void runAllRules(RuleBase ruleBase, Object o) {
		StatefulSession session = getStatefulSession(ruleBase);
		runAllRules(session,o);
	}
	
	public static void runAllRules(RuleBase ruleBase, Object o,AgendaFilter filter) {
		StatefulSession session = insert(ruleBase,o);
		session.fireAllRules(filter);
		session.dispose();
	}
	
	public static StatefulSession insert(RuleBase ruleBase, Object o){
		StatefulSession session = null;
		if(ruleBase != null){
			session = ruleBase.newStatefulSession();
			session.insert(o);
		}
		return session;
	}

}
