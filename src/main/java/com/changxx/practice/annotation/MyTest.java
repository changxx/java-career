package com.changxx.practice.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@MyAnnotation(hello = "beijing", world = "shanghai", array = {}, style = Integer.class)
public class MyTest {

    @MyAnnotation(world = "shanghai", array = { 1, 2, 3 })
    public void output() {
        System.out.println("output something!");
    }

    public static void main(String[] args) throws Exception {
        MyTest myTest = new MyTest();
        Class<MyTest> c = MyTest.class;
        Method method = c.getMethod("output", new Class[] {});

        if (MyTest.class.isAnnotationPresent(MyAnnotation.class)) {
            System.out.println("have annotation");
        }

        if (method.isAnnotationPresent(MyAnnotation.class)) {
            method.invoke(myTest, null);

            MyAnnotation myAnnotation = method.getAnnotation(MyAnnotation.class);
            String hello = myAnnotation.hello();
            String world = myAnnotation.world();

            System.out.println(hello + ", " + world);
            System.out.println(myAnnotation.array().length);
            System.out.println(myAnnotation.style());


        }

        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println(annotation.annotationType().getName());
        }
    }
}
