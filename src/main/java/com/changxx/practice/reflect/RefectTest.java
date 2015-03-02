package com.changxx.practice.reflect;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author changxiangxiang
 * @date 2014年9月2日 上午9:53:54
 * @description
 * @since sprint2
 */
public class RefectTest {

    public static void main(String[] args) throws SecurityException, NoSuchFieldException, IntrospectionException, IllegalArgumentException, IllegalAccessException,
            InvocationTargetException {
        User user = new User();

        Field[] fields = User.class.getDeclaredFields();

        for (Field field : fields) {

            System.out.println(field.getName());

            if (field.getName().equals("name")) {

                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), user.getClass());

                Method method = pd.getWriteMethod();

                method.invoke(user, "10129");

                System.out.println(user.getName());
            }
        }
    }
}
