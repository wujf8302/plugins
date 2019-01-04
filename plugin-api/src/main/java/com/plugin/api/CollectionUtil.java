package com.plugin.api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.IteratorUtils;
import org.apache.log4j.Logger;

public class CollectionUtil {
    
    private static Logger log = Logger.getLogger(CollectionUtil.class);

	/**
	 * 获取一个集合的字集
	 * @param iterator       迭代器
	 * @param startIndex     起初
	 * @param numberOfItems  数量
	 * @return
	 */
	private static List getSubList(Iterator iterator, int startIndex,int numberOfItems) {
		List croppedList = new ArrayList(numberOfItems);
		int skippedRecordCount = 0;
		int copiedRecordCount = 0;
		do {
			//-------------------
			Object object = null;
			do {
				if (!iterator.hasNext()) {
					break;
				}
				object = iterator.next();
			} while (++skippedRecordCount <= startIndex);
			croppedList.add(object);
			//-------------------
		} while (numberOfItems == 0 || ++copiedRecordCount < numberOfItems);
		return croppedList;
	}

	public static List getListFromObject(Object iterableObject, int startIndex,int numberOfItems) {
		if (iterableObject instanceof List) {
			List list = (List) iterableObject;
			int lastRecordExclusive = numberOfItems > 0 ? startIndex+ numberOfItems : list.size();
			if (lastRecordExclusive > list.size())
				lastRecordExclusive = list.size();
			if (startIndex < list.size())
				return list.subList(startIndex, lastRecordExclusive);
		}
		Iterator iterator = IteratorUtils.getIterator(iterableObject);
		return getSubList(iterator, startIndex, numberOfItems);
	}
}
