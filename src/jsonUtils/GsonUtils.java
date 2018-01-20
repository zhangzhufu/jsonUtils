package jsonUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * 
 * <strong>类描述：</strong> gson解析json工具类<br/>
 * <strong>创建者：</strong> ZZF<br/>
 * <strong>项目名称：</strong> jsonUtils<br/>
 * <strong>创建时间：</strong> 2018年1月17日 下午2:32:45<br/>
 * <strong>版本号：</strong> v1.0<br/>
 */
public class GsonUtils {

	private GsonUtils() {
	}

	/*
	 * 自定义String适配器
	 */
	private static final TypeAdapter<String> STRING = new TypeAdapter<String>() {
		public String read(JsonReader reader) throws IOException {
			if (reader.peek() == JsonToken.NULL) {
				reader.nextNull();
				return "";
			}
			return reader.nextString();
		}
		public void write(JsonWriter writer, String value) throws IOException {
			if (value == null) {
				// 在这里处理null改为空字符串
				writer.value("");
				return;
			}
			writer.value(value);
		}
	};
	private static Gson gson = null;

	// 获取工具实例
	public static Gson getInstance() {
		if (gson == null) {
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.registerTypeAdapter(String.class, STRING);
			gson = gsonBuilder.create();
		}
		return gson;
	}

	/**
	 * 
	 * <strong>方法描述 ：</strong> javaBean转换json字符串<br/>
	 * <strong>创建时间：</strong> 2018年1月17日 下午2:34:31<br/>
	 * 
	 * @param clazz
	 * @return
	 */
	public static String toJson(Class<?> clazz) {
		return getInstance().toJson(clazz);
	}

	/**
	 * 
	 * <strong>方法描述 ：</strong> json 字符串转换javaBean实体<br/>
	 * <strong>创建时间：</strong> 2018年1月17日 下午2:35:07<br/>
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static Object fromJson(String json, Class<?> clazz) {
		Object obj = null;
		obj = getInstance().fromJson(json, clazz);
		return obj;
	}

	/**
	 * 
	 * <strong>方法描述 ：</strong> json 字符串转换javaBean实体list集合<br/>
	 * <strong>创建时间：</strong> 2018年1月17日 下午2:38:42<br/>
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> fromJsonToList(String json, Class<T[]> clazz) {
		T[] array = getInstance().fromJson(json, clazz);
		return Arrays.asList(array);
	}

	/**
	 * 
	 * <strong>方法描述 ：</strong> json 字符串转换javaBean实体ArrayList集合<br/>
	 * <strong>创建时间：</strong> 2018年1月17日 下午2:39:06<br/>
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> ArrayList<T> fromJsonToArrayList(String json, Class<T> clazz) {
		Type type = new TypeToken<ArrayList<JsonObject>>() {
		}.getType();
		ArrayList<JsonObject> jsonObjects = getInstance().fromJson(json, type);

		ArrayList<T> arrayList = new ArrayList<T>();
		for (JsonObject jsonObject : jsonObjects) {
			arrayList.add(getInstance().fromJson(jsonObject, clazz));
		}
		return arrayList;
	}

}
