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
 * <strong>��������</strong> gson����json������<br/>
 * <strong>�����ߣ�</strong> ZZF<br/>
 * <strong>��Ŀ���ƣ�</strong> jsonUtils<br/>
 * <strong>����ʱ�䣺</strong> 2018��1��17�� ����2:32:45<br/>
 * <strong>�汾�ţ�</strong> v1.0<br/>
 */
public class GsonUtils {

	private GsonUtils() {
	}

	/*
	 * �Զ���String������
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
				// �����ﴦ��null��Ϊ���ַ���
				writer.value("");
				return;
			}
			writer.value(value);
		}
	};
	private static Gson gson = null;

	// ��ȡ����ʵ��
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
	 * <strong>�������� ��</strong> javaBeanת��json�ַ���<br/>
	 * <strong>����ʱ�䣺</strong> 2018��1��17�� ����2:34:31<br/>
	 * 
	 * @param clazz
	 * @return
	 */
	public static String toJson(Class<?> clazz) {
		return getInstance().toJson(clazz);
	}

	/**
	 * 
	 * <strong>�������� ��</strong> json �ַ���ת��javaBeanʵ��<br/>
	 * <strong>����ʱ�䣺</strong> 2018��1��17�� ����2:35:07<br/>
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
	 * <strong>�������� ��</strong> json �ַ���ת��javaBeanʵ��list����<br/>
	 * <strong>����ʱ�䣺</strong> 2018��1��17�� ����2:38:42<br/>
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
	 * <strong>�������� ��</strong> json �ַ���ת��javaBeanʵ��ArrayList����<br/>
	 * <strong>����ʱ�䣺</strong> 2018��1��17�� ����2:39:06<br/>
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
