package io.ark.engine.security.core.token;

import java.time.Duration;
import lombok.Data;

/**
 * @Description: JWT配置属性 @Author: Noah Zhou
 */
@Data
public class JwtProperties {

  /** JWT签名密钥，建议32位以上 */
  private String secret;

  /** AccessToken 过期时间，默认15分钟 */
  private Duration accessTokenExpire = Duration.ofMinutes(15);

  /** RefreshToken 过期时间，默认7天 */
  private Duration refreshTokenExpire = Duration.ofDays(7);
}
