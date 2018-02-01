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
 * <strong>类描述：</strong> gson 解析 json 工具类<br/>
 * <strong>创建者：</strong> ZZF<br/>
 * <strong>项目名称：</strong> jsonUtils<br/>
 * <strong>创建时间：</strong> 2018年2月1日 上午10:25:05<br/>
 * <strong>版本号：</strong> v1.0<br/>
 */
public class GsonUtils {

	private GsonUtils() {
	}

	/*
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
				writer.value("");
				return;
			}
			writer.value(value);
		}
	};
	private static Gson gson = null;

	public static Gson getInstance() {
		if (gson == null) {
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.registerTypeAdapter(String.class, STRING);
			gson = gsonBuilder.create();
		}
		return gson;
	}

	public static String toJson(Class<?> clazz) {
		return getInstance().toJson(clazz);
	}

	public static Object fromJson(String json, Class<?> clazz) {
		Object obj = null;
		obj = getInstance().fromJson(json, clazz);
		return obj;
	}

	public static <T> List<T> fromJsonToList(String json, Class<T[]> clazz) {
		T[] array = getInstance().fromJson(json, clazz);
		return Arrays.asList(array);
	}

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
