package com.accenture.business.utils;

import com.accenture.business.exception.MustNotNullException;
import com.accenture.business.handler.reflect.FieldIgnore;
import com.accenture.business.handler.reflect.FieldNotNull;
import com.accenture.business.handler.reflect.TypeReflect;
import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;


public class JSONUtil {
    private JSONUtil(){}

    private static final Logger logger = LoggerFactory.getLogger(JSONUtil.class);

    public static <T> JsonObject DTOToJsonObject(JsonObject jsonObject,T obj,Class<T> clazz){
        Field[] fields = clazz.getDeclaredFields();
        Method[] methods = clazz.getDeclaredMethods();
        Method getMethod = null;
        Method setMethod = null;
        TypeReflect typeReflect;
        for (Field field:fields) {
            getMethod = findGetter(methods,field.getName());
            setMethod = findSetter(methods,field.getName());
            try {
                String name = field.getName();
                typeReflect = field.getAnnotation(TypeReflect.class);
                if(field.getAnnotation(FieldIgnore.class) == null &&
                        getMethod.getAnnotation(FieldIgnore.class) == null &&
                        setMethod.getParameters()[0].getAnnotation(FieldIgnore.class) == null){
                    if (field.getAnnotation(TypeReflect.class) != null) {
                        name = typeReflect.value();
                    }else if (getMethod.getAnnotation(TypeReflect.class) != null){
                        name = getMethod.getAnnotation(TypeReflect.class).value();
                    } else if(setMethod.getParameters()[0].getAnnotation(TypeReflect.class) != null){
                        name = setMethod.getParameters()[0].getAnnotation(TypeReflect.class).value();
                    }
                    if (field.get(obj) != null) {
                        field.setAccessible(true);
                        Type type = field.getGenericType();
                        if (((Class) type).isPrimitive()) {
                            JsonPrimitive jsonPrimitive = new JsonPrimitive(field.get(obj).toString());
                            jsonObject.add(name,jsonPrimitive);
                        }
                        if (((Class) type).isSynthetic()) {
                            JsonObject newJsonObject;
                            newJsonObject = DTOToJsonObject(jsonObject,field.get(obj),((Class) type));
                            jsonObject.add(field.getName(),newJsonObject);
                        }
                    } else {
                        if (field.getAnnotation(FieldNotNull.class) != null ||
                                getMethod.getAnnotation(FieldNotNull.class) != null ||
                                setMethod.getParameters()[0].getAnnotation(FieldNotNull.class) != null) {
                            throw new MustNotNullException();
                        }
                    }
                }
            } catch (IllegalAccessException e){
                logger.error("catch IllegalAccessException or NoSuchMethodException");
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
        Method getMethod = null;
        Method setMethod = null;
        TypeReflect typeReflect;
        for (Field field:fields) {
            getMethod = findGetter(methods,field.getName());
            setMethod = findSetter(methods,field.getName());
            try {
                String name = field.getName();
                typeReflect = field.getAnnotation(TypeReflect.class);
                if(field.getAnnotation(FieldIgnore.class) == null &&
                        getMethod.getAnnotation(FieldIgnore.class) == null &&
                        setMethod.getParameters()[0].getAnnotation(FieldIgnore.class) == null){
                    if (field.getAnnotation(TypeReflect.class) != null) {
                        name = typeReflect.value();
                    }else if (getMethod.getAnnotation(TypeReflect.class) != null){
                        name = getMethod.getAnnotation(TypeReflect.class).value();
                    } else if(setMethod.getParameters()[0].getAnnotation(TypeReflect.class) != null){
                        name = setMethod.getParameters()[0].getAnnotation(TypeReflect.class).value();
                    }
                    if (jsonObject.get(name) != null) {
                        field.setAccessible(true);
                        Type type = field.getGenericType();
                        JsonElement fieldElement = jsonObject.get(name);
                        if (fieldElement.isJsonPrimitive()) {
                            field.set(obj, fieldElement.getAsString());
                        }
                        if (fieldElement.isJsonObject() && type instanceof Class) {
                            field.set(obj, stringToDTO(fieldElement.toString(), ((Class) type).getConstructor().newInstance(), (Class) type));
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

}
