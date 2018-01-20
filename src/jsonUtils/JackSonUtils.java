package jsonUtils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.VO;
import utils.DisposeEntityUtil;

/**
 * 
 * <strong>类描述：</strong> jackson 工具类<br/>
 * <strong>创建者：</strong> ZZF<br/>
 * <strong>项目名称：</strong> jsonUtils<br/>
 * <strong>创建时间：</strong> 2018年1月18日 上午10:02:08<br/>
 * <strong>版本号：</strong> v1.0<br/>
 */
public class JackSonUtils {
	private static ObjectMapper mapper = null;

	public static ObjectMapper getInstance() {
		if (mapper == null) {
			mapper = new ObjectMapper();
		}
		return mapper;
	}

	/**
	 * 
	 * <strong>方法描述 ：</strong> java 转 json<br/>
	 * <strong>创建时间：</strong> 2018年1月18日 上午10:01:51<br/>
	 * 
	 * @param obj
	 * @return
	 * @throws JsonProcessingException
	 */
	public static String writeValueAsString(Object obj) throws JsonProcessingException {
		// 这段代码会把 Null 对象也会变成空字符串
		// getInstance().getSerializerProvider().setNullValueSerializer(new
		// JsonSerializer<Object>() {
		// @Override
		// public void serialize(Object arg0, JsonGenerator arg1, SerializerProvider
		// arg2)
		// throws IOException, JsonProcessingException {
		// arg1.writeString("");
		// }
		// });
		return getInstance().writeValueAsString(obj);
	}

	/**
	 * 
	 * <strong>方法描述 ：</strong> json 转 JAVA<br/>
	 * <strong>创建时间：</strong> 2018年1月18日 上午10:01:35<br/>
	 * 
	 * @param json
	 * @param t
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static Object readerValue(String json, Class<?> t)
			throws JsonParseException, JsonMappingException, IOException {
		return getInstance().readValue(json, t);
	}

	// 测试
	public static void main(String[] args) {
		VO v1 = new VO();
		v1.setName("name1");
		try {
			System.out.println(writeValueAsString(v1));
			VO v2 = (VO) readerValue(writeValueAsString(v1), VO.class);
			// 用来格式化返回实体中 String 类型属性为空，转化为空字符串
			new DisposeEntityUtil<VO>().disposeEmpty(v2);
			System.out.println(v2.getName());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
