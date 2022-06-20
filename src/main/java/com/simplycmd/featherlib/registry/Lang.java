package com.simplycmd.featherlib.registry;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @param lang The language of your language file (ex: en_us, zh_cn)
 * @param name The name of your block/item
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Lang {
    String lang();
    String name();
}
