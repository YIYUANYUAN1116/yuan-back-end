package com.yuan.ai.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yuan.ai.domain.bo.ChatMessageChunkBo;
import com.yuan.ai.domain.vo.ChatMessageChunkVo;
import com.yuan.ai.service.ChatMessageChunkService;
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
 * chat_message_chunk
 *
 * @author yuan
 * @date Thu Feb 26 21:44:46 CST 2026
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/ai/chatMessageChunk")
@Tag(name = "ChatMessageChunkController",description = "chat_message_chunk")
public class ChatMessageChunkController extends BaseController {

    private final ChatMessageChunkService chatMessageChunkService;

/**
 * 查询chat_message_chunk列表
 */
@SaCheckPermission("ai:chatMessageChunk:list")
@GetMapping("/list")
@Operation(summary = "查询chat_message_chunk列表",operationId = "ChatMessageChunk_list")
    public TableDataInfo<ChatMessageChunkVo> list(ChatMessageChunkBo bo, PageQuery pageQuery) {
        return chatMessageChunkService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出chat_message_chunk列表
     */
    @SaCheckPermission("ai:chatMessageChunk:export")
    @Log(title = "chat_message_chunk", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @Operation(summary = "导出chat_message_chunk列表",operationId = "ChatMessageChunk_export")
    public void export(ChatMessageChunkBo bo, HttpServletResponse response) {
        List<ChatMessageChunkVo> list = chatMessageChunkService.queryList(bo);
        ExcelUtil.exportExcel(list, "chat_message_chunk", ChatMessageChunkVo.class, response);
    }

    /**
     * 获取chat_message_chunk详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("ai:chatMessageChunk:query")
    @GetMapping("/{id}")
    @Operation(summary = "获取chat_message_chunk详细信息",operationId = "ChatMessageChunk_getInfo")
    public R<ChatMessageChunkVo> getInfo(@NotNull(message = "主键不能为空")
                                         @PathId @PathVariable Long id) {
        return R.ok(chatMessageChunkService.queryById(id));
    }

    /**
     * 新增chat_message_chunk
     */
    @SaCheckPermission("ai:chatMessageChunk:add")
    @Log(title = "chat_message_chunk", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    @Operation(summary = "新增chat_message_chunk",operationId = "ChatMessageChunk_add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ChatMessageChunkBo bo) {
        return toAjax(chatMessageChunkService.insertByBo(bo));
    }

    /**
     * 修改chat_message_chunk
     */
    @SaCheckPermission("ai:chatMessageChunk:edit")
    @Log(title = "chat_message_chunk", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    @Operation(summary = "修改chat_message_chunk",operationId = "ChatMessageChunk_edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ChatMessageChunkBo bo) {
        return toAjax(chatMessageChunkService.updateByBo(bo));
    }

    /**
     * 删除chat_message_chunk
     *
     * @param ids 主键串
     */
    @SaCheckPermission("ai:chatMessageChunk:remove")
    @Log(title = "chat_message_chunk", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除chat_message_chunk",operationId = "ChatMessageChunk_remove")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable @PathIds Long[] ids) {
        return toAjax(chatMessageChunkService.deleteWithValidByIds(List.of(ids), true));
    }
}
