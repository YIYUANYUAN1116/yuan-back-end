package com.yuan.ai.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yuan.ai.domain.bo.ChatAttachmentBo;
import com.yuan.ai.domain.vo.ChatAttachmentVo;
import com.yuan.ai.service.ChatAttachmentService;
import com.yuan.common.core.domain.R;
import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;
import com.yuan.common.doc.annotation.PathId;
import com.yuan.common.doc.annotation.PathIds;
import com.yuan.common.excel.utils.ExcelUtil;
import com.yuan.common.idempotent.annotation.RepeatSubmit;
import com.yuan.common.log.annotation.Log;
import com.yuan.common.log.enums.BusinessType;
import com.yuan.common.web.core.BaseController;
import com.yuan.core.page.PageQuery;
import com.yuan.core.page.TableDataInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * chat_attachment
 *
 * @author yuan
 * @date Thu Feb 26 21:44:43 CST 2026
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/ai/chatAttachment")
@Tag(name = "ChatAttachmentController",description = "chat_attachment")
public class ChatAttachmentController extends BaseController {

    private final ChatAttachmentService chatAttachmentService;

/**
 * 查询chat_attachment列表
 */
@SaCheckPermission("ai:chatAttachment:list")
@GetMapping("/list")
@Operation(summary = "查询chat_attachment列表",operationId = "ChatAttachment_list")
    public TableDataInfo<ChatAttachmentVo> list(ChatAttachmentBo bo, PageQuery pageQuery) {
        return chatAttachmentService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出chat_attachment列表
     */
    @SaCheckPermission("ai:chatAttachment:export")
    @Log(title = "chat_attachment", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @Operation(summary = "导出chat_attachment列表",operationId = "ChatAttachment_export")
    public void export(ChatAttachmentBo bo, HttpServletResponse response) {
        List<ChatAttachmentVo> list = chatAttachmentService.queryList(bo);
        ExcelUtil.exportExcel(list, "chat_attachment", ChatAttachmentVo.class, response);
    }

    /**
     * 获取chat_attachment详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("ai:chatAttachment:query")
    @GetMapping("/{id}")
    @Operation(summary = "获取chat_attachment详细信息",operationId = "ChatAttachment_getInfo")
    public R<ChatAttachmentVo> getInfo(@NotNull(message = "主键不能为空")
                                         @PathId @PathVariable Long id) {
        return R.ok(chatAttachmentService.queryById(id));
    }

    /**
     * 新增chat_attachment
     */
    @SaCheckPermission("ai:chatAttachment:add")
    @Log(title = "chat_attachment", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    @Operation(summary = "新增chat_attachment",operationId = "ChatAttachment_add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ChatAttachmentBo bo) {
        return toAjax(chatAttachmentService.insertByBo(bo));
    }

    /**
     * 修改chat_attachment
     */
    @SaCheckPermission("ai:chatAttachment:edit")
    @Log(title = "chat_attachment", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    @Operation(summary = "修改chat_attachment",operationId = "ChatAttachment_edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ChatAttachmentBo bo) {
        return toAjax(chatAttachmentService.updateByBo(bo));
    }

    /**
     * 删除chat_attachment
     *
     * @param ids 主键串
     */
    @SaCheckPermission("ai:chatAttachment:remove")
    @Log(title = "chat_attachment", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除chat_attachment",operationId = "ChatAttachment_remove")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable @PathIds Long[] ids) {
        return toAjax(chatAttachmentService.deleteWithValidByIds(List.of(ids), true));
    }
}
