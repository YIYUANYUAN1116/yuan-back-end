package com.yuan.ai.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import com.yuan.common.idempotent.annotation.RepeatSubmit;
import com.yuan.common.log.annotation.Log;
import com.yuan.common.web.core.BaseController;
import com.yuan.core.page.PageQuery;
import com.yuan.common.core.domain.R;
import com.yuan.common.core.validate.AddGroup;
import com.yuan.common.core.validate.EditGroup;
import com.yuan.common.log.enums.BusinessType;
import com.yuan.common.excel.utils.ExcelUtil;
import com.yuan.common.doc.annotation.PathId;
import com.yuan.common.doc.annotation.PathIds;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.yuan.ai.domain.vo.ChatMessageVo;
import com.yuan.ai.domain.bo.ChatMessageBo;
import com.yuan.ai.service.ChatMessageService;
import com.yuan.core.page.TableDataInfo;

/**
 * chat_message
 *
 * @author yuan
 * @date Thu Feb 26 21:44:39 CST 2026
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/ai/chatMessage")
@Tag(name = "ChatMessageController",description = "chat_message")
public class ChatMessageController extends BaseController {

    private final ChatMessageService chatMessageService;

/**
 * 查询chat_message列表
 */
@SaCheckPermission("ai:chatMessage:list")
@GetMapping("/list")
@Operation(summary = "查询chat_message列表",operationId = "ChatMessage_list")
    public TableDataInfo<ChatMessageVo> list(ChatMessageBo bo, PageQuery pageQuery) {
        return chatMessageService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出chat_message列表
     */
    @SaCheckPermission("ai:chatMessage:export")
    @Log(title = "chat_message", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @Operation(summary = "导出chat_message列表",operationId = "ChatMessage_export")
    public void export(ChatMessageBo bo, HttpServletResponse response) {
        List<ChatMessageVo> list = chatMessageService.queryList(bo);
        ExcelUtil.exportExcel(list, "chat_message", ChatMessageVo.class, response);
    }

    /**
     * 获取chat_message详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("ai:chatMessage:query")
    @GetMapping("/{id}")
    @Operation(summary = "获取chat_message详细信息",operationId = "ChatMessage_getInfo")
    public R<ChatMessageVo> getInfo(@NotNull(message = "主键不能为空")
                                         @PathId @PathVariable Long id) {
        return R.ok(chatMessageService.queryById(id));
    }

    /**
     * 新增chat_message
     */
    @SaCheckPermission("ai:chatMessage:add")
    @Log(title = "chat_message", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    @Operation(summary = "新增chat_message",operationId = "ChatMessage_add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ChatMessageBo bo) {
        return toAjax(chatMessageService.insertByBo(bo));
    }

    /**
     * 修改chat_message
     */
    @SaCheckPermission("ai:chatMessage:edit")
    @Log(title = "chat_message", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    @Operation(summary = "修改chat_message",operationId = "ChatMessage_edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ChatMessageBo bo) {
        return toAjax(chatMessageService.updateByBo(bo));
    }

    /**
     * 删除chat_message
     *
     * @param ids 主键串
     */
    @SaCheckPermission("ai:chatMessage:remove")
    @Log(title = "chat_message", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除chat_message",operationId = "ChatMessage_remove")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable @PathIds Long[] ids) {
        return toAjax(chatMessageService.deleteWithValidByIds(List.of(ids), true));
    }
}
