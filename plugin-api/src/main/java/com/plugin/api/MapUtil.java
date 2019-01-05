package com.plugin.api;

/**
 * 
 * @author wujf
 */
public class MapUtil {
    /**
     *   public static Map group(List<IndCustomAlarm> alarms){
        Map alarmsMap = new HashMap();
        for (IndCustomAlarm alarm : alarms) {
            Long key = alarm.getIndId();
            if(key != null){
                List<IndCustomAlarm> temp = null;
                if(alarmsMap.get(key) != null){
                    temp = (ArrayList<IndCustomAlarm>)alarmsMap.get(key);
                    temp.add(alarm);
                }else{
                    temp = new ArrayList<IndCustomAlarm>();
                    temp.add(alarm);
                }
                alarmsMap.put(key, temp);
            }
        }
        return alarmsMap; 
    }
    
     */
}
