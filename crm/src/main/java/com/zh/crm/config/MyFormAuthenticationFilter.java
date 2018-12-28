package com.zh.crm.config;


import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class   MyFormAuthenticationFilter extends FormAuthenticationFilter {
    protected boolean onLoginSuccess(AuthenticationToken token,
        Subject subject,
        ServletRequest request,
        ServletResponse response) throws Exception {
        String successUrl = "/index";
        WebUtils.issueRedirect(request,response,successUrl);
        return false;
        }


}
