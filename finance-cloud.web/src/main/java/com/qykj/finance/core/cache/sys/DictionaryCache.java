
package com.qykj.finance.core.cache.sys;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.qykj.finance.sys.model.Dictionary;
/**
 *  字典缓存
 *  创 建 人: wenjing8 <br/>
 *  版 本 号: V1.0.0 <br/>
 */
@Setter
@Getter
public class DictionaryCache{
	private Dictionary dict;//字典本身
	private List<Dictionary> dicts;//下级节点
}
