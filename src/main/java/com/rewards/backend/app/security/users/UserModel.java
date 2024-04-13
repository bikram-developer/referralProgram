package com.rewards.backend.app.security.users;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="admin_Model")
public class UserModel implements UserDetails{
	private static final long serialVersionUID = 1L;
	@Id @JsonIgnore 
	private String id; 
	private String name;
	private String email; 
	private String password;
	private String description; 
	private String userCategory;
	private String userId; 
	private String roleName;

	@Override public Collection<? extends GrantedAuthority> getAuthorities() 
	{
		return List.of(new SimpleGrantedAuthority(roleName));
	}
	@Override 
	public String getUsername() {
		return this.email;
		}
	@Override 
	public boolean isAccountNonExpired() 
	{
		return true;
	}
	@Override 
	public boolean isAccountNonLocked() 
	{
		return true;
	}
	@Override 
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override 
	public boolean isEnabled() 
	{
		return true;
	}
	@Override 
	public String getPassword() 
	{
		return password;
	}
}