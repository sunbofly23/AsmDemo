package com.test.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class CustomClassVisitor extends ClassVisitor {
    private String className;
    private String superName;

    public CustomClassVisitor(ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        this.className = name;
        this.superName = superName;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        System.out.println("ClassVisitor visitMethod name-------" + name + ", superName is " + superName);
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);

        //没有使用Androidx 的Activity都继承自v7/app/AppCompatActivity
        if (superName.equals("android/support/v7/app/AppCompatActivity")) {
            if (name.equals("onCreate")) {
                //处理onCreate()方法
                return new CustomMethoedVisitor(mv, className, name);
            } else if (name.equals("onDestroy")) {
                //处理onDestory()方法
                return new CustomOnDestoryVisitor(mv, className, name);
            }
        }
        return mv;
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
    }
}