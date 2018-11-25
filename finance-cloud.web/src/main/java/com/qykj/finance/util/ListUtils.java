package com.qykj.finance.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
@SuppressWarnings({"unchecked","rawtypes"})
public class ListUtils {
	/**
     * 获取set排序后的list
     * @author wenjing 
     * @param set
     * @return
     */
   public static List<Integer> getSortListBySet(Set<Integer> set){
    	List<Integer> sortList=new ArrayList<Integer>();
        for(Integer value : set){  
        	sortList.add(value);  	  
        }  
        Collections.sort(sortList);
        return sortList;
    }
    /**
     * 将集合转换成字符串
     * @param deletes
     * @return
     * @author    wenjing
     * @see       [相关类/方法]
     * @since     V1.0.0
     */
	public static String listToStr(List deletes){
		StringBuffer buffer=new StringBuffer();
		if(deletes==null){
			return "";
		}
		for(int i=0;i<deletes.size();i++){
			if(deletes.get(i)!=null){
				if(i!=deletes.size()-1){
					buffer.append(deletes.get(i));
					buffer.append(",");
				}else{
					buffer.append(deletes.get(i));
				}
			}
		}
		return buffer.toString();
	}
	
	public static String strListToStr(List deletes){
		StringBuffer buffer=new StringBuffer();
		if(deletes==null){
			return "";
		}
		for(int i=0;i<deletes.size();i++){
			if(deletes.get(i)!=null){
				if(i!=deletes.size()-1){
					buffer.append("'"+deletes.get(i)+"'");
					buffer.append(",");
				}else{
					buffer.append("'"+deletes.get(i)+"'");
				}
			}
		}
		return buffer.toString();
	}
	
	public static String arrayToStr(Object[] array){
		return ListUtils.listToStr(ListUtils.arrayToList(array));
	}
	
	/**
	 * 把以逗号隔开的字符串转化成字符串
	 * @param str
	 * @return
	 */
	public static List<Integer> strToList(String str){
		List<Integer> ids=new ArrayList<Integer>();
		if(str!=null&&str.length()>0){
			String [] strArray=str.split(",");
			for(int i=0;i<strArray.length;i++){
				ids.add(Integer.valueOf(strArray[i]));
			}
		}
		return ids;
	}
	
	public static List<Long> strToLongList(String str){
		List<Long> ids=new ArrayList<Long>();
		if(str!=null&&str.length()>0){
			String [] strArray=str.split(",");
			for(int i=0;i<strArray.length;i++){
				ids.add(Long.valueOf(strArray[i]));
			}
		}
		return ids;
	}
	
	public static List<String> strToStrList(String str){
		List<String> ids=new ArrayList<String>();
		if(str!=null&&str.length()>0){
			String [] strArray=str.split(",");
			for(int i=0;i<strArray.length;i++){
				ids.add(strArray[i]);
			}
		}
		return ids;
	}
	/**
	 * 把以逗号隔开的字符串转化成字符串,其中字符串的格式要求如下：
	 * Res_1,Res_2,Res_3,Res_4,Res_5,Res_6,
	 * @author wenjing 2015-1-23 下午12:44:06
	 * @param str
	 * @return
	 */
	public static List<Integer> strToList2(String str){
		List<Integer> ids=new ArrayList<Integer>();
		if(StringUtils.isNotEmpty(str)){
			String [] strArray=str.split(",");
			for(int i=0;i<strArray.length;i++){
				ids.add(Integer.valueOf(strArray[i].split("_")[1]));
			}
		}
		return ids;
	}
	/**
	 * 获取两个list的差集
	 * @param first
	 * @param second
	 * @return
	 */
	public static List<Integer> diffSet(List<Integer> first,List<Integer> second){
		if(first!=null){
			List<Integer> temp=new ArrayList<Integer>();
			temp.addAll(first);
			temp.removeAll(second);
			return temp;
		}
		return null;
	}
	

	public static List arrayToList(Object [] arrary){
		List list=new ArrayList();
		if(arrary!=null){
			for(int i=0;i<arrary.length;i++){
				list.add(arrary[i]);
			}
		}else{
			return null;
		}
		return list;
	}
	/**
	 * 数据库获取返回统计list转换成map
	 * @param list
	 * @return
	 */
	
	public static Map listToMap(List list){
		Map map = new HashMap<Integer, String>();
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				Object [] obj=(Object[]) list.get(i);
				map.put(Integer.valueOf(obj[0].toString()), obj[1].toString());
			}
		}
		return map;
	}
	/**
	 * 从list中去掉重复的值
	 * @author wenjing 2014-12-9 上午10:11:27
	 * @param list
	 * @return
	 */
	public static List removeDuplicateWithOrder(List list) {
        Set set = new HashSet();
        List newList = new ArrayList();
        for (Iterator iter = list.iterator(); iter.hasNext();) {
            Object element = iter.next();
            if (set.add(element))
                newList.add(element);
        }
        return newList;
    }
	
	/**
	 * 把List<Map<String,Object>>转为map
	 * @param list
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	public static Map<String, Long> convertListToMap(List<Map<String,Object>> list) {
		Map<String, Long> result = new HashMap<String, Long>();
		if(CollectionUtils.isNotEmpty(list)){
			for (Map<String,Object> map : list) {
				List<String> tmp = new ArrayList<String>();
				if(null != map && map.size() > 0){
					for (Map.Entry<String, Object> entry : map.entrySet()) {
						tmp.add(entry.getValue() + "");					
	                }		
					result.put(tmp.get(0), Long.parseLong(tmp.get(1)));				
				}		
	        }
		}
		return result;
    }
}
