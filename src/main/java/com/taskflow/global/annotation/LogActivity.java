package com.taskflow.global.annotation;

import com.taskflow.domain.activitylog.entity.ActivityType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // 이 애노테이션은 메서드에만 붙힐 수 있다는 뜻입니다!!
@Retention(RetentionPolicy.RUNTIME) // 런타임에도 유지되어, AOP나 리플렉션으로 읽을 수 있음
public @interface LogActivity {
    ActivityType value();
    String target() default  "";
}
