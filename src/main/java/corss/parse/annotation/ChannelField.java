package corss.parse.annotation;

import java.lang.annotation.*;

/**
 * Created by lianrongfa on 2018/5/18.
 * 用来标明那个字段为通道
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface ChannelField {
}
