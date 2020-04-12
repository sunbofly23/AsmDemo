//1:引入包名
package com.test.plugin

import com.android.build.gradle.AppExtension

//2:导入 Plugin和 Project
import  org.gradle.api.Plugin
import  org.gradle.api.Project

//3:实现Plugin<Project> 重写apply()
class Tests implements Plugin<Project>{

    @Override
    void apply(Project project) {
        printf "== Test Plugin=="
        //注册CustomTransform

        def  android = project.extensions.getByType(AppExtension)
        CustomTransform transform = new CustomTransform();
        android.registerTransform(transform)

    }
}

