package io.ark.engine.web.core.i18n;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Locale;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

/**
 * @Description: 优先取 lang 请求参数，其次取 Accept-Language Header，fallback zh_CN @Author: Noah Zhou
 */
public class ArkLocaleResolver implements LocaleResolver {
  public static final String LANG_PARAM = "lang";

  @Override
  @NonNull
  public Locale resolveLocale(HttpServletRequest request) {
    String lang = request.getParameter(LANG_PARAM);
    if (StringUtils.hasText(lang)) {
      return StringUtils.parseLocale(lang);
    }
    Locale locale = request.getLocale();
    if (locale != null && StringUtils.hasText(locale.getLanguage())) {
      return locale;
    }
    return Locale.SIMPLIFIED_CHINESE;
  }

  @Override
  public void setLocale(
      @NonNull HttpServletRequest request, HttpServletResponse response, Locale locale) {
    // 无状态实现，不持久化
  }
}
