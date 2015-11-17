package com.changxx.practice.javassist;

import javassist.*;

/**
 * @author changxx on 15/5/27.
 */
public class JavassistGenerator {

    public static void main(String[] args) throws Exception {
        // 如果一个CtClass对象通过 writeFile(), toClass(), 或toBytecode() 方法被转换为类文件，Javassist就冻结了此对象。对此CtClass对象的后续修改都是不允许的
        ClassPool classPool = ClassPool.getDefault();
        CtClass cc = classPool.get("com.changxx.practice.javassist.User");
        // 如果 ClassPool.doPruning()方法设置为true，javassist可以优化调整一个被冻结的CtClass对象的数据结构
        cc.stopPruning(true);

        // 添加方法
        CtMethod mthd = CtNewMethod.make("public String getExt() { return \"invoke ext method\"; }", cc);
        cc.addMethod(mthd);

        // 添加属性
        CtField f = new CtField(CtClass.intType, "i", cc);
        cc.addField(f);

        CtMethod[] ctMethods = cc.getDeclaredMethods();

        for (CtMethod ctMethod : ctMethods) {
            ctMethod.insertAfter("System.out.println(\"insert method hello:\"+this.name);");
        }

        System.out.println("-------1-----");

        Class cls = cc.toClass();

        User user = (User) cls.getConstructor().newInstance();

        System.out.println("-------2-----");

        user.setName("changxx");

        System.out.println("-------3-----");

        System.out.println(user.getName());

        cc.writeFile();

        System.out.println("-------4-----");

        // 新增的属性
        System.out.println(user.getClass().getDeclaredField("i"));

        System.out.println("-------5-----");

        // 新增的方法
        System.out.println(user.getClass().getDeclaredMethod("getExt"));

        // 调用方法
        System.out.println(user.getClass().getDeclaredMethod("getExt").invoke(user));

        System.out.println("-------6-----");

        ClassPool classPool1 = ClassPool.getDefault();
        // 创建新类
        CtClass mkClass = classPool1.makeClass("Point");
        CtMethod mthd1 = CtNewMethod.make("public String getExt() { return \"invoke ext method\"; }", mkClass);
        mkClass.addMethod(mthd1);

        Class mkc = mkClass.toClass();
        System.out.println(mkc.getDeclaredMethod("getExt").invoke(mkc.newInstance()));
    }

}
