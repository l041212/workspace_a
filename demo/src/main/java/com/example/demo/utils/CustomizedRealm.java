package com.example.demo.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.aspects.DataSourceAspect;
import com.example.theatre.entity.Permission;
import com.example.theatre.entity.Role;
import com.example.theatre.entity.User;
import com.example.theatre.service.PermissionService;
import com.example.theatre.service.RoleService;
import com.example.theatre.service.UserService;

public class CustomizedRealm extends AuthorizingRealm {

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PermissionService permissionService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String username = String.valueOf(principals.getPrimaryPrincipal());
		DataSourceAspect.targetTrasnfer("library2");
		User user = userService.getItem(username);
		List<Role> roles = roleService.findItemsByUserId(user.getId());
		List<Permission> permissions = permissionService.findItemsByRoleId(roles);
		DataSourceAspect.targetCleaner();
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		simpleAuthorizationInfo.addRoles(roles.stream().map(role -> role.getCode()).collect(Collectors.toList()));
		simpleAuthorizationInfo.addStringPermissions(permissions.stream().map(permission -> permission.getCode()).collect(Collectors.toList()));
		return simpleAuthorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
		DataSourceAspect.targetTrasnfer("library2");
		User user = userService.getItem(usernamePasswordToken.getUsername());
		DataSourceAspect.targetCleaner();
		if (user == null) {
			throw new UnknownAccountException("unknown account !");
		}
		if (!new String(usernamePasswordToken.getPassword()).equals(user.getPassword())) {
			throw new IncorrectCredentialsException("incorrect credentials !");
		}
		if (user.getDeactivated()) {
			throw new LockedAccountException("account locked !");
		}
		return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), this.getName());
	}

}
