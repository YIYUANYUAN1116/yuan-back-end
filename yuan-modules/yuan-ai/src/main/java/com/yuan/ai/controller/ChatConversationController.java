package com.yuan.ai.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yuan.ai.domain.bo.ChatConversationBo;
import com.yuan.ai.domain.vo.ChatConversationVo;
import com.yuan.ai.service.ChatConversationService;
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
 * chat_conversation
 *
 * @author yuan
 * @date Thu Feb 26 21:44:35 CST 2026
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/ai/chatConversation")
@Tag(name = "ChatConversationController",description = "chat_conversation")
public class ChatConversationController extends BaseController {

    private final ChatConversationService chatConversationService;

/**
 * 查询chat_conversation列表
 */
@SaCheckPermission("ai:chatConversation:list")
@GetMapping("/list")
@Operation(summary = "查询chat_conversation列表",operationId = "ChatConversation_list")
    public TableDataInfo<ChatConversationVo> list(ChatConversationBo bo, PageQuery pageQuery) {
        return chatConversationService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出chat_conversation列表
     */
    @SaCheckPermission("ai:chatConversation:export")
    @Log(title = "chat_conversation", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @Operation(summary = "导出chat_conversation列表",operationId = "ChatConversation_export")
    public void export(ChatConversationBo bo, HttpServletResponse response) {
        List<ChatConversationVo> list = chatConversationService.queryList(bo);
        ExcelUtil.exportExcel(list, "chat_conversation", ChatConversationVo.class, response);
    }

    /**
     * 获取chat_conversation详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("ai:chatConversation:query")
    @GetMapping("/{id}")
    @Operation(summary = "获取chat_conversation详细信息",operationId = "ChatConversation_getInfo")
    public R<ChatConversationVo> getInfo(@NotNull(message = "主键不能为空")
                                         @PathId @PathVariable Long id) {
        return R.ok(chatConversationService.queryById(id));
    }

    /**
     * 新增chat_conversation
     */
    @SaCheckPermission("ai:chatConversation:add")
    @Log(title = "chat_conversation", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    @Operation(summary = "新增chat_conversation",operationId = "ChatConversation_add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ChatConversationBo bo) {
        return toAjax(chatConversationService.insertByBo(bo));
    }

    /**
     * 修改chat_conversation
     */
    @SaCheckPermission("ai:chatConversation:edit")
    @Log(title = "chat_conversation", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    @Operation(summary = "修改chat_conversation",operationId = "ChatConversation_edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ChatConversationBo bo) {
        return toAjax(chatConversationService.updateByBo(bo));
    }

    /**
     * 删除chat_conversation
     *
     * @param ids 主键串
     */
    @SaCheckPermission("ai:chatConversation:remove")
    @Log(title = "chat_conversation", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除chat_conversation",operationId = "ChatConversation_remove")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable @PathIds Long[] ids) {
        return toAjax(chatConversationService.deleteWithValidByIds(List.of(ids), true));
    }
}
