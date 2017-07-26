package jp.co.sss.shop.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import jp.co.sss.shop.validator.LoginValidator;

/**
 * ログインチェックの独自アノテーションクラス
 *
 * @author System Shared
 *
 */
@Target({ java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { LoginValidator.class })
public @interface LoginCheck {
	String message() default "メールアドレス、もしくはパスワードが間違っています。";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String fieldEmail() default "email";

	String fieldPassword() default "password";
}
