package com.yuan.system.helper;

import com.yuan.common.core.constant.TenantConstants;
import com.yuan.common.satoken.utils.LoginHelper;

import java.util.Collections;
import java.util.List;

public class MenuScopHelper {
    public static List<String> getAssignableMenuScopes() {

        if (LoginHelper.isSuperAdmin()) {
            return List.of(
                    TenantConstants.MENU_SCOP_PLAT_ADMIN,
                    TenantConstants.MENU_SCOP_TENANT_ADMIN,
                    TenantConstants.MENU_SCOP_BOTH_ADMIN
            );
        }

        if (LoginHelper.isPlatAdmin()) {
            return List.of(
                    TenantConstants.MENU_SCOP_PLAT_ADMIN,
                    TenantConstants.MENU_SCOP_BOTH_ADMIN
            );
        }

        if (LoginHelper.isTenantAdmin()) {
            return List.of(
                    TenantConstants.MENU_SCOP_TENANT_ADMIN,
                    TenantConstants.MENU_SCOP_BOTH_ADMIN
            );
        }

        return Collections.emptyList();
    }
}
