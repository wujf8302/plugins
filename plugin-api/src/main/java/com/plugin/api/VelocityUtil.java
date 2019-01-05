package com.plugin.api;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 * 模版技术.
 * @author wujf
 * @version Revision 1.0.0
 */
public class VelocityUtil {
    
    /**
     * 测试代码.
     */
    private static void simpleEvalAsStringTest() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("uerId", "10002");
        String template = "select (select r.kf_org_id from region_trans_tbl r where r.aas_usergroup_id = a.org_id)org_id from aas_user_tbl a where a.aas_user_id = $uerId";
        System.out.println(replace(template, map));
    }
    
    private static void getParamKeyTest() {
        String queryParam = "LOCALNET=$LOCALNET AND LOCALNET_CN=$LOCALNET_CN AND ORG=$ORG AND ORG_CN=$LOCALNET_CN AND ORG_LEVEL=$ORG";
        List<String> keys = getParamKey(queryParam);//调用java方法
        if (keys != null && keys.size() > 0) {
            for (int i = 0; i < keys.size(); i++) {
                String key = (String)keys.get(i);
                System.out.println(key);
            }
        }
        /*
        LOCALNET
        LOCALNET_CN
        ORG
        LOCALNET_CN
        ORG
         */
    }
    
    /**
     * JDBC方式查询SQM_IND_PARAM_TBL的SOURCE_SQL字段值.
     */
    private static String getSourceSql(String key){
        String sourceSql = "";
        String sql = "select p.SOURCE_SQL as sourceSql form SQM_IND_PARAM_TBL p where p.key='" + key + "'";
        //@todo使用jdbc
        return sourceSql;
    }
    
    /**
     * 处理动态查询条件(存储过程).
     * @param userId 用户id
     * @param queryParam 参数模板
     * @return
     */
    private static String getQueryParam(Long userId, String queryParam) {
        if (queryParam != null && !"".equals(queryParam)) {
            
            List<String> keys = getParamKey(queryParam);//调用java方法
            
            Map<String, String> map = new HashMap<String, String>();
            if (keys != null && keys.size() > 0) {
                for (int i = 0; i < keys.size(); i++) {
                    String key = (String)keys.get(i);

                    Map<String, Object> args = new HashMap<String, Object>();
                    args.put("uerId", userId);
                    
                    String value = null;
                    String sourceSql = getSourceSql(key);//使用jdbc查询
                    if(sourceSql != null && !"".equals(sourceSql)){
                        value = "("+ replace(sourceSql, args) + ")";//调用java的simpleEvalAsString方法
                    }
                    
                    map.put(key, value);
                }
                if (map.size() > 0) {
                    queryParam = replace(queryParam,map);//调用java的simpleEvalAsString方法
                }
            }
        } else {
            queryParam = "";
        }
        
        return queryParam;
    }
    
    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("uerId", "10002");
        String template = "select (select r.kf_org_id from region_trans_tbl r where r.aas_usergroup_id = a.org_id)org_id from aas_user_tbl a where a.aas_user_id = $uerId";
        System.out.println(replace(template, map));
        
        //simpleEvalAsStringTest();
        //getParamKeyTest();
    }
    
    /**
     * 字符处理
     */
    public static String replace(String template, Map<String, ?> args) {
        VelocityContext context = new VelocityContext(args);
        StringWriter writer = new StringWriter();
        Velocity.evaluate(context, writer, "Debug", template);
        return writer.getBuffer().toString();
    }
    
    /**
     * 获得维度中含有的key.
     * @author 刘隽.
     * @param queryParam.
     */
    public static List<String> getParamKey(String queryParam) {
        List<String> list = new ArrayList<String>();
        if (queryParam != null && !"".equals(queryParam)) {
            Pattern rowPattern = Pattern.compile("\\$(\\S+)\\s*");
            Matcher m = rowPattern.matcher(queryParam);
            while (m.find()) {
                for (int i = 0; i < m.groupCount(); i++) {
                    list.add(m.group(i + 1));
                }
            }
        }
        return list;
    }
}
