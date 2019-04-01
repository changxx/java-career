package com.changxx.practice.jdk18;

import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

public class OptionalTest {

    @Test
    public void test() throws IOException {
        //创建一个值为张三的String类型的Optional
        Optional<String> ofOptional = Optional.of("张三");
        //如果我们用of方法创建Optional对象时，所传入的值为null，则抛出NullPointerException如下图所示
        Optional<String> nullOptional = Optional.of(null);
    }

    /**
     * 为指定的值创建Optional对象，不管所传入的值为null不为null，创建的时候都不会报错
     */
    @Test
    public void ofNullable() throws IOException {
        //为指定的值创建Optional对象，不管所传入的值为null不为null，创建的时候都不会报错
        Optional<String> nullOptional = Optional.ofNullable(null);
        Optional<String> nullOptional1 = Optional.ofNullable("lisi");

        //创建一个空的String类型的Optional对象
        Optional<String> emptyOptional = Optional.empty();

        Optional<String> stringOptional = Optional.of("张三");
        System.out.println(stringOptional.get());
    }

    /**
     * 如果我们创建的Optional对象中有值存在则返回此值，如果没有值存在，则会抛出
     * NoSuchElementException异常
     */
    @Test
    public void get() throws IOException {
        Optional<String> emptyOptional = Optional.empty();
        System.out.println(emptyOptional.get());
    }

    /**
     * 如果创建的Optional中有值存在，则返回此值，否则返回一个默认值
     */
    @Test
    public void orElse() throws IOException {
        Optional<String> stringOptional = Optional.of("张三");
        System.out.println(stringOptional.orElse("zhangsan"));

        Optional<String> emptyOptional = Optional.empty();
        System.out.println(emptyOptional.orElse("李四"));
    }

    /**
     * 如果创建的Optional中有值存在，则返回此值，否则返回一个由Supplier接口生成的值
     */
    @Test
    public void orElseGet() throws IOException {
        Optional<String> stringOptional = Optional.of("张三");
        System.out.println(stringOptional.orElseGet(() -> "zhangsan"));

        Optional<String> emptyOptional = Optional.empty();
        System.out.println(emptyOptional.orElseGet(() -> "orElseGet"));
    }

    /**
     * 如果创建的Optional中有值存在，则返回此值，否则抛出一个由指定的Supplier接口生成的异常
     */
    @Test
    public void orElseThrow() throws IOException {
        Optional<String> stringOptional = Optional.of("张三");
        System.out.println(stringOptional.orElseThrow(CustomException::new));

        Optional<String> emptyOptional = Optional.empty();
        System.out.println(emptyOptional.orElseThrow(CustomException::new));
    }

    private static class CustomException extends RuntimeException {
        private static final long serialVersionUID = -4399699891687593264L;

        public CustomException() {
            super("自定义异常");
        }

        public CustomException(String message) {
            super(message);
        }
    }

    /**
     * 如果创建的Optional中的值满足filter中的条件，则返回包含该值的Optional对象，否则返回一个空的Optional对象
     */
    @Test
    public void filter() throws IOException {
        Optional<String> stringOptional = Optional.of("zhangsan");
        System.out.println(stringOptional.filter(e -> e.length() > 5).orElse("王五"));
        stringOptional = Optional.empty();
        System.out.println(stringOptional.filter(e -> e.length() > 5).orElse("lisi"));
    }

    /**
     * 如果创建的Optional中的值存在，对该值执行提供的Function函数调用
     */
    @Test
    public void map() throws IOException {
        //map方法执行传入的lambda表达式参数对Optional实例的值进行修改,修改后的返回值仍然是一个Optional对象
        Optional<String> stringOptional = Optional.of("zhangsan");
        System.out.println(stringOptional.map(e -> e.toUpperCase()).orElse("失败"));

        stringOptional = Optional.empty();
        System.out.println(stringOptional.map(e -> e.toUpperCase()).orElse("失败"));
    }

    /**
     * 如果创建的Optional中的值存在，就对该值执行提供的Function函数调用，返回一个Optional类型的值，否
     * 则就返回一个空的Optional对象.flatMap与map（Funtion）方法类似，区别在于flatMap中的mapper返回
     * 值必须是Optional，map方法的mapping函数返回值可以是任何类型T。调用结束时，flatMap不会对结果用Optional封装。
     */
    @Test
    public void flagMap() throws IOException {
        //map方法中的lambda表达式返回值可以是任意类型，在map函数返回之前会包装为Optional。
        //但flatMap方法中的lambda表达式返回值必须是Optionl实例
        Optional<String> stringOptional = Optional.of("zhangsan");
        System.out.println(stringOptional.flatMap(e -> Optional.of("lisi")).orElse("失败"));

        stringOptional = Optional.empty();
        System.out.println(stringOptional.flatMap(e -> Optional.empty()).orElse("失败"));
    }

    @Test
    public void ifPresent() throws IOException {
        //ifPresent方法的参数是一个Consumer的实现类，Consumer类包含一个抽象方法，该抽象方法对传入的值进行处理，只处理没有返回值。
        Optional<String> stringOptional = Optional.of("zhangsan");
        stringOptional.ifPresent(e -> System.out.println("我被处理了。。。" + e));
    }
}
