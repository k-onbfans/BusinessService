package com.accenture.business.utils;

import com.accenture.business.handler.log.RequestBodyLogger;
import com.accenture.business.v1.bean.Port;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;


public class JSONUtil {

    private static final Logger logger = LoggerFactory.getLogger(JSONUtil.class);

    public static <T> T StringToDTO(String str,T obj,Class<T> clazz)  {
        JsonObject jsonObject = new JsonParser().parse(str).getAsJsonObject();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field:fields) {
            try{
                field.setAccessible(true);
                String name = field.getName();
                JsonElement fieldElement = jsonObject.get(name);
                Type type = field.getGenericType();
                if(fieldElement.isJsonPrimitive()){
                    field.set(obj,fieldElement.getAsString());
                }
                if (fieldElement.isJsonObject()){
                    if(type instanceof Class){
                        field.set(obj,StringToDTO(fieldElement.toString(),((Class) type).getConstructor().newInstance(),(Class)type));
                    }
                }

            }catch (IllegalAccessException | NoSuchMethodException e){
                logger.error("catch IllegalAccessException");
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }
}
