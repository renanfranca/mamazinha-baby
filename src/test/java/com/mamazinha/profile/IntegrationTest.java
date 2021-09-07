package com.mamazinha.profile;

import com.mamazinha.profile.ProfileApp;
import com.mamazinha.profile.config.TestSecurityConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = { ProfileApp.class, TestSecurityConfiguration.class })
public @interface IntegrationTest {
}
