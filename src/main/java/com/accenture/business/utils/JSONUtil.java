package com.accenture.business.utils;

import com.accenture.business.exception.MustNotNullException;
import com.accenture.business.handler.reflect.FieldIgnore;
import com.accenture.business.handler.reflect.FieldNotNull;
import com.accenture.business.handler.reflect.TypeReflect;
import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;


public class JSONUtil {
    private JSONUtil(){}

    private static final Logger logger = LoggerFactory.getLogger(JSONUtil.class);

    public static <T> JsonObject convertDTOToJsonObject(JsonObject jsonObject,T obj,Class<T> clazz){
        Field[] fields = clazz.getDeclaredFields();
        Method[] methods = clazz.getDeclaredMethods();
        for (Field field:fields) {
            try {
                Method getMethod = findGetter(methods,field.getName());
                Method setMethod = findSetter(methods,field.getName());
                String name = field.getName();
                isMethodNull(getMethod,setMethod);
                if(hasFieldIgnore(field,getMethod,setMethod)){
                    return jsonObject;
                }
                name = hasTypeReflect(name,getMethod,setMethod,field);
                if (field.get(obj) != null) {
                    jsonObject = getField(field,obj,name,jsonObject);
                } else {
                    ifNotNull(field,getMethod,setMethod);
                }
            } catch (IllegalAccessException e){
                logger.error("catch IllegalAccessException or NoSuchMethodException");
            } catch (NullPointerException e){
                logger.error("catch Exception");
            }
        }
        return jsonObject;
    }

    public static String jsonObjectToString(JsonObject jsonObject){
        return jsonObject.getAsString();
    }


    public static <T> T stringToDTO(String str,T obj,Class<T> clazz)  {
        JsonObject jsonObject = new JsonParser().parse(str).getAsJsonObject();
        Field[] fields = clazz.getDeclaredFields();
        Method[] methods = clazz.getDeclaredMethods();
        for (Field field:fields) {
            try {
                Method getMethod = findGetter(methods,field.getName());
                Method setMethod = findSetter(methods,field.getName());
                String name = field.getName();
                isMethodNull(getMethod,setMethod);
                if(hasFieldIgnore(field,getMethod,setMethod)){
                    break;
                }
                name = hasTypeReflect(name,getMethod,setMethod,field);
                if (jsonObject.get(name) != null) {
                    obj = setField(field,jsonObject,name,obj);
                } else {
                    ifNotNull(field,getMethod,setMethod);
                }
            } catch (IllegalAccessException | NoSuchMethodException e){
                logger.error("catch IllegalAccessException or NoSuchMethodException");
            } catch (InvocationTargetException e) {
                logger.error("catch InvocationTargetException");
            } catch (InstantiationException e) {
                logger.error("catch InstantiationException");
            } catch (NullPointerException e){
                logger.error("catch NullPointerException");
            }
        }
        return obj;
    }



    private static Method findGetter(Method[] methods,String name){
        for(Method method:methods) {
            if (method.getName().equalsIgnoreCase("get" + name)) {
                return method;
            }
        }
        return null;
    }

    private static Method findSetter(Method[] methods,String name){
        for(Method method:methods) {
            if (method.getName().equalsIgnoreCase("set" + name)) {
                return method;
            }
        }
        return null;
    }

    private static String hasTypeReflect(String name,Method getter,Method setter,Field field){
        if (field.getAnnotation(TypeReflect.class) != null) {
            return field.getAnnotation(TypeReflect.class).value();
        }else if (getter.getAnnotation(TypeReflect.class) != null){
            return getter.getAnnotation(TypeReflect.class).value();
        } else if(setter.getParameters()[0].getAnnotation(TypeReflect.class) != null){
            return setter.getParameters()[0].getAnnotation(TypeReflect.class).value();
        }else {
            return name;
        }
    }

    private static <T> T setField(Field field,JsonObject jsonObject,String name,T obj) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {

        field.setAccessible(true);
        Type type = field.getGenericType();
        JsonElement fieldElement = jsonObject.get(name);
        if (fieldElement.isJsonPrimitive()) {
            field.set(obj, fieldElement.getAsString());
        }
        if (fieldElement.isJsonObject() && type instanceof Class) {
            field.set(obj, stringToDTO(fieldElement.toString(), ((Class) type).getConstructor().newInstance(), (Class) type));
        }
        if(fieldElement.isJsonArray()){
            List<Object> list = new ArrayList<>();
            for(JsonElement jsonElement : fieldElement.getAsJsonArray()){
                if (jsonElement.isJsonPrimitive()) {
                    Object object = fieldElement.getAsString();
                    list.add(object);
                }
                if (jsonElement.isJsonObject()) {
                    Object object;
                    Class<T> entityClass = (Class<T>)((ParameterizedType) type).getActualTypeArguments()[0];
                    object = stringToDTO(jsonElement.toString(),entityClass.getConstructor().newInstance(),entityClass);
                    list.add(object);
                }
            }
            field.set(obj,list);
        }
        return obj;
    }

    private static boolean hasFieldIgnore(Field field,Method getter,Method setter){
        boolean fieldIgnore;
        if(field.getAnnotation(FieldIgnore.class) == null &&
                getter.getAnnotation(FieldIgnore.class) == null &&
                setter.getParameters()[0].getAnnotation(FieldIgnore.class) == null){
            fieldIgnore = false;
        }else {
            fieldIgnore = true;
        }
        return fieldIgnore;
    }

    private static void ifNotNull(Field field,Method getter,Method setter){
        try {
            if (field.getAnnotation(FieldNotNull.class) != null ||
                    getter.getAnnotation(FieldNotNull.class) != null ||
                    setter.getParameters()[0].getAnnotation(FieldNotNull.class) != null) {
                throw new MustNotNullException();
            }
        }catch (MustNotNullException e){
            logger.error("catch MustNotNullException");
        }

    }

    private static void isMethodNull(Method getter,Method setter){
        try {
            if(getter == null || setter == null){
                throw new NullPointerException();
            }
        }catch (NullPointerException e){
            logger.error("catch NullPointerException");
        }
    }

    private static <T> JsonObject getField(Field field,T obj,String name,JsonObject jsonObject) throws IllegalAccessException {
        field.setAccessible(true);
        Type type = field.getGenericType();
        if (((Class) type).isPrimitive()) {
            JsonPrimitive jsonPrimitive = new JsonPrimitive(field.get(obj).toString());
            jsonObject.add(name,jsonPrimitive);
        }
        if (((Class) type).isSynthetic()) {
            JsonObject newJsonObject;
            newJsonObject = convertDTOToJsonObject(jsonObject,field.get(obj),((Class) type));
            jsonObject.add(field.getName(),newJsonObject);
        }
        return jsonObject;
    }
}
