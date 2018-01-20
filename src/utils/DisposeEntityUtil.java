package utils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理实体工具类
 * 
 * @author ducl
 *
 * @param <T>
 */
public class DisposeEntityUtil<T> {

	/**
	 * 处理实体中为null的属性值
	 * 
	 * @param entity
	 * @return T
	 * @throws Exception
	 */
	public void disposeEmpty(T entity) {
		try {
			if (entity != null) {
				// 获取实体所有属性
				Field[] fieldArray = entity.getClass().getDeclaredFields();
				roundDispose(entity, fieldArray);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 循环操作
	 * 
	 * @param entity
	 * @param fieldArray
	 * @throws Exception
	 */
	private void roundDispose(T entity, Field[] fieldArray) throws Exception {
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("fieldArray", null);
		resMap.put("T", null);

		// Field[] fieldResArray = {};
		/************************** 第一重 ************************/
		for (Field field : fieldArray) {
			Class<? extends Object> fieldType = field.getType();
			field.setAccessible(true);// 给予私有属性访问权限

			// 属性为String类型
			if (fieldType.isAssignableFrom(String.class)) {
				// 为null属性重新赋值为空字符串
				fieldEmptyDispose(entity, field);
			}
			/************************** 第二重 ************************/
			// 属性为List类型
			if (fieldType.isAssignableFrom(List.class)) {
				// 获取List属性泛型类的所有属性
				List<T> valList = (List<T>) field.get(entity);
				if (valList == null) {
					continue;
				}
				for (T sonT : valList) {
					Field[] sonFieldArray = sonT.getClass().getDeclaredFields();
					for (Field sf : sonFieldArray) {
						sf.setAccessible(true);// 给予私有属性访问权限
						Class<? extends Object> sonFieldType = sf.getType();

						// 属性为String类型
						if (sonFieldType.isAssignableFrom(String.class)) {
							// 为null属性重新赋值为空字符串
							fieldEmptyDispose(sonT, sf);
						}

						/************************** 第三重 ************************/
						// 属性为List类型
						if (sonFieldType.isAssignableFrom(List.class)) {
							// 获取List属性泛型类的所有属性
							List<T> sfValList = (List<T>) sf.get(sonT);
							if (sfValList == null) {
								continue;
							}
							for (T gct : sfValList) {
								Field[] gcFieldArray = gct.getClass().getDeclaredFields();
								for (Field gcf : gcFieldArray) {
									gcf.setAccessible(true);// 给予私有属性访问权限
									Class<? extends Object> gcFieldType = gcf.getType();

									// 属性为String类型
									if (gcFieldType.isAssignableFrom(String.class)) {
										// 为null属性重新赋值为空字符串
										fieldEmptyDispose(gct, gcf);
									}

									/************************** 第四重 ************************/
									// 属性为List类型
									if (gcFieldType.isAssignableFrom(List.class)) {
										// 获取List属性泛型类的所有属性
										List<T> gcfValList = (List<T>) gcf.get(gct);
										if (gcfValList == null) {
											continue;
										}
										for (T ggs : gcfValList) {

											Field[] ggsFieldArray = ggs.getClass().getDeclaredFields();
											for (Field ggsF : ggsFieldArray) {
												ggsF.setAccessible(true);// 给予私有属性访问权限
												Class<? extends Object> ggcFieldType = ggsF.getType();

												// 属性为String类型
												if (ggcFieldType.isAssignableFrom(String.class)) {
													// 为null属性重新赋值为空字符串
													fieldEmptyDispose(ggs, ggsF);
												}

												/************************** 第五重 ************************/
												// 属性为List类型
												if (ggcFieldType.isAssignableFrom(List.class)) {
													// 获取List属性泛型类的所有属性
													List<T> ggsValList = (List<T>) ggsF.get(ggs);
													if (ggsValList == null) {
														continue;
													}
													for (T fiveT : ggsValList) {

														Field[] fiveFieldArray = fiveT.getClass().getDeclaredFields();
														for (Field fiveF : fiveFieldArray) {
															fiveF.setAccessible(true);// 给予私有属性访问权限
															Class<? extends Object> fiveFieldType = fiveF.getType();

															// 属性为String类型
															if (fiveFieldType.isAssignableFrom(String.class)) {
																// 为null属性重新赋值为空字符串
																fieldEmptyDispose(fiveT, fiveF);
															}

															// /**************************第六重************************/
															// 属性为List类型
															if (fiveFieldType.isAssignableFrom(List.class)) {
																// 获取List属性泛型类的所有属性
																List<T> sixValList = (List<T>) fiveF.get(fiveT);
																if (sixValList == null) {
																	continue;
																}
																for (T sixT : sixValList) {
																	Field[] sixField = sixT.getClass()
																			.getDeclaredFields();
																	for (Field sixF : sixField) {
																		sixF.setAccessible(true);
																		Class<? extends Object> sixFieldType = sixF
																				.getType();
																		if (sixFieldType
																				.isAssignableFrom(String.class)) {
																			// 为null属性重新赋值为空字符串
																			fieldEmptyDispose(sixT, sixF);
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 为null属性重新赋值为空字符串
	 * 
	 * @param fieldArray
	 */
	private void fieldEmptyDispose(T entity, Field field) throws Exception {
		Class<? extends Object> fieldType = field.getType();
		// 属性为String类型
		if (fieldType.isAssignableFrom(String.class)) {
			String val = String.valueOf(field.get(entity));
			// 把不合格的初始值设为空字符窜
			if (val == null || "null".equals(val)) {
				field.set(entity, "");
			}
		}
	}
}
