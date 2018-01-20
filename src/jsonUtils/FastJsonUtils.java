package jsonUtils;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 
 * <strong>类描述：</strong> fastjson工具类<br/>
 * <strong>创建者：</strong> ZZF<br/>
 * <strong>项目名称：</strong> jsonUtils<br/>
 * <strong>创建时间：</strong> 2018年1月17日 上午11:05:59<br/>
 * <strong>版本号：</strong> v1.0<br/>
 */
public class FastJsonUtils {

	private FastJsonUtils() {
	}

	/**
	 * 
	 * <strong>方法描述 ：</strong> 实体转 String<br/>
	 * <strong>创建时间：</strong> 2018年1月17日 上午11:05:04<br/>
	 * 
	 * @param T
	 * @return
	 */
	public static String toJSONString(Object obj) {
		String jsonString = JSON.toJSONString(obj, SerializerFeature.WriteNullStringAsEmpty,
				SerializerFeature.WriteNullListAsEmpty);
		return jsonString;
	}

	/**
	 * 
	 * <strong>方法描述 ：</strong> json字符串转成实体<br/>
	 * <strong>创建时间：</strong> 2018年1月17日 上午11:04:30<br/>
	 * 
	 * @param json
	 * @param T
	 */
	public static Object parseObject(String json, Class<?> T) {
		return JSON.parseObject(json, T);
	}

	/**
	 * 
	 * <strong>方法描述 ：</strong> json 字符串转实体（调用异常） list<br/>
	 * <strong>创建时间：</strong> 2018年1月17日 上午11:05:21<br/>
	 * 
	 * @param str
	 *            实例： List<VO> jsonList = JSON.parseObject(toJSONString(list), new
	 *            TypeReference<List<VO>>(){});
	 */
	public static <T> List<T> parseObjectList(String str, Class<T> T) {
		return JSON.parseArray(str, T);
	}
}
