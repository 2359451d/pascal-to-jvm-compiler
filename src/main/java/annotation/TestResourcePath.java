package annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value = RetentionPolicy.RUNTIME)
public @interface TestResourcePath {
    // specify the path of testing resource, default field: value
    String value();
}
