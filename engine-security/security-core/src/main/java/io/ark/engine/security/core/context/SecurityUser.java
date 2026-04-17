package io.ark.engine.security.core.context;

import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @Description: SecurityContext中存的用户信息 @Author: Noah Zhou
 */
@AllArgsConstructor
public class SecurityUser implements UserDetails {

  private final String userId;
  private final String username;
  //    private final String clientType;
  private final List<? extends GrantedAuthority> authorities;

  public String getUserId() {
    return userId;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getUsername() {
    return username;
  }
}
