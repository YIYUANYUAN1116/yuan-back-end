package com.yuan.system.controller;

import cn.dev33.satoken.secure.BCrypt;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import com.yuan.common.core.domain.R;
import com.yuan.common.core.utils.StringUtils;
import com.yuan.common.core.utils.file.MimeTypeUtils;
import com.yuan.common.log.annotation.Log;
import com.yuan.common.log.enums.BusinessType;
import com.yuan.common.oss.core.client.OssClient;
import com.yuan.common.satoken.utils.LoginHelper;
import com.yuan.common.web.core.BaseController;
import com.yuan.system.domain.bo.SysUserBo;
import com.yuan.system.domain.bo.SysUserPasswordBo;
import com.yuan.system.domain.bo.SysUserProfileBo;
import com.yuan.system.domain.vo.AvatarVo;
import com.yuan.system.domain.vo.ProfileVo;
import com.yuan.system.domain.vo.SysUserVo;
import com.yuan.system.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

/**
 * 个人信息 业务处理
 *
 * @author Lion Li
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/user/profile")
@Tag(name = "SysProfileController",description = "个人信息")
public class SysProfileController extends BaseController {

    private final SysUserService userService;
    private final OssClient ossClient;


    /**
     * 个人信息
     */
    @GetMapping
    @Operation(summary = "个人信息",operationId="getProfile")
    public R<ProfileVo> profile() {
        SysUserVo user = userService.selectUserById(LoginHelper.getUserId());
        ProfileVo profileVo = new ProfileVo();
        profileVo.setUser(user);
        profileVo.setRoleGroup(userService.selectUserRoleGroup(user.getUserId()));
        profileVo.setPostGroup(userService.selectUserPostGroup(user.getUserId()));
        return R.ok(profileVo);
    }

    /**
     * 修改用户
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping
    @Operation(summary = "修改用户",operationId="updateProfile")
    public R<Void> updateProfile(@RequestBody SysUserProfileBo profile) {
        SysUserBo user = BeanUtil.toBean(profile, SysUserBo.class);
        if (StringUtils.isNotEmpty(user.getPhonenumber()) && !userService.checkPhoneUnique(user)) {
            return R.fail("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        if (StringUtils.isNotEmpty(user.getEmail()) && !userService.checkEmailUnique(user)) {
            return R.fail("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setUserId(LoginHelper.getUserId());
        if (userService.updateUserProfile(user) > 0) {
            return R.ok();
        }
        return R.fail("修改个人信息异常，请联系管理员");
    }

    /**
     * 重置密码
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping("/updatePwd")
    @Operation(summary = "重置密码",operationId="updatePwd")
    public R<Void> updatePwd(@Validated @RequestBody SysUserPasswordBo bo) {
        SysUserVo user = userService.selectUserById(LoginHelper.getUserId());
        String password = user.getPassword();
        if (!BCrypt.checkpw(bo.getOldPassword(), password)) {
            return R.fail("修改密码失败，旧密码错误");
        }
        if (BCrypt.checkpw(bo.getNewPassword(), password)) {
            return R.fail("新密码不能与旧密码相同");
        }

        if (userService.resetUserPwd(user.getUserId(), BCrypt.hashpw(bo.getNewPassword())) > 0) {
            return R.ok();
        }
        return R.fail("修改密码异常，请联系管理员");
    }

    /**
     * 头像上传
     *
     * @param avatarFile 用户头像
     */
    @Log(title = "用户头像", businessType = BusinessType.UPDATE)
    @PostMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "个人信息",operationId="avatar")
    public R<AvatarVo> avatar(@RequestPart("avatarFile") MultipartFile avatarFile) {
        if (!avatarFile.isEmpty()) {
            String extension = FileUtil.extName(avatarFile.getOriginalFilename());
            if (!StringUtils.equalsAnyIgnoreCase(extension, MimeTypeUtils.IMAGE_EXTENSION)) {
                return R.fail("文件格式不正确，请上传" + Arrays.toString(MimeTypeUtils.IMAGE_EXTENSION) + "格式");
            }
            userService.editAvatar(avatarFile);
        }
        return R.fail("上传图片异常，请联系管理员");
    }

}
