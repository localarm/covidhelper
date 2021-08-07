package com.pavel.covhelper.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**Класс для проверки условия, может ли пользователь посещать страницу управления
 */
@Component
public class PermissionConfig {

    /**Проверяет, имеет ли пользователь доступ к странице управления
     * @param id айди управления
     * @return true, если роль пользователя позволяет зайти на странциу управления, иначе false
     */
    public boolean checkPermission(Authentication authentication, String id){
        if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_Department_viewer"))) {
            SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
            try {
                long longId = Long.parseLong(id);
                return securityUser.getDepartmentId() == longId;
            } catch (NumberFormatException nex) {
                return false;
            }
        }
        return false;
    }
}
