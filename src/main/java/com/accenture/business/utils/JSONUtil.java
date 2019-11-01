package com.accenture.business.utils;

import com.accenture.business.exception.MustNotNullException;
import com.accenture.business.handler.reflect.FieldIgnore;
import com.accenture.business.handler.reflect.FieldNotNull;
import com.accenture.business.handler.reflect.TypeReflect;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;


public class JSONUtil {
    private JSONUtil(){};

    private static final Logger logger = LoggerFactory.getLogger(JSONUtil.class);

    public static <T> T StringToDTO(String str,T obj,Class<T> clazz)  {
        JsonObject jsonObject = new JsonParser().parse(str).getAsJsonObject();
        Field[] fields = clazz.getDeclaredFields();
        Method[] methods = clazz.getDeclaredMethods();
        Method getMethod = null;
        Method setMethod = null;
        TypeReflect typeReflect;
        for (Field field:fields) {
            for(Method method:methods){
                if(method.getName().equalsIgnoreCase("get" + field.getName())){
                    getMethod = method;
                }
                if(method.getName().equalsIgnoreCase("set" + field.getName())){
                    setMethod = method;
                }
            }
            try {
                String name = field.getName();
                typeReflect = field.getAnnotation(TypeReflect.class);
                if(field.getAnnotation(FieldIgnore.class) == null &&
                        getMethod.getAnnotation(FieldIgnore.class) == null &&
                        setMethod.getParameters()[0].getAnnotation(FieldIgnore.class) == null){
                    if (typeReflect != null) {
                        name = typeReflect.value();
                    }
                    if (jsonObject.get(name) != null) {
                        field.setAccessible(true);
                        Type type = field.getGenericType();
                        JsonElement fieldElement = jsonObject.get(name);
                        if (fieldElement.isJsonPrimitive()) {
                            field.set(obj, fieldElement.getAsString());
                        }
                        if (fieldElement.isJsonObject() && type instanceof Class) {
                            field.set(obj, StringToDTO(fieldElement.toString(), ((Class) type).getConstructor().newInstance(), (Class) type));
                        }
                    } else {
                        if (field.getAnnotation(FieldNotNull.class) != null ||
                                getMethod.getAnnotation(FieldNotNull.class) != null ||
                                setMethod.getParameters()[0].getAnnotation(FieldNotNull.class) != null) {
                            throw new MustNotNullException();
                        }
                    }
                }
            } catch (IllegalAccessException | NoSuchMethodException e){
                logger.error("catch IllegalAccessException or NoSuchMethodException");
            } catch (InvocationTargetException e) {
                logger.error("catch InvocationTargetException");
            } catch (InstantiationException e) {
                logger.error("catch InstantiationException");
            }
        }
        return obj;
    }

}
