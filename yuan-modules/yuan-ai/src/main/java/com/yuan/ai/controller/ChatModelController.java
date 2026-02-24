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
import com.yuan.ai.domain.vo.ChatModelVo;
import com.yuan.ai.domain.bo.ChatModelBo;
import com.yuan.ai.service.ChatModelService;
import com.yuan.core.page.TableDataInfo;

/**
 * chat_model
 *
 * @author ageerle
 * @date Mon Feb 16 14:59:14 CST 2026
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/ai/chatModel")
public class ChatModelController extends BaseController {

    private final ChatModelService chatModelService;

/**
 * 查询chat_model列表
 */
@SaCheckPermission("ai:chatModel:list")
@GetMapping("/list")
    public TableDataInfo<ChatModelVo> list(ChatModelBo bo, PageQuery pageQuery) {
        return chatModelService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出chat_model列表
     */
    @SaCheckPermission("ai:chatModel:export")
    @Log(title = "chat_model", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ChatModelBo bo, HttpServletResponse response) {
        List<ChatModelVo> list = chatModelService.queryList(bo);
        ExcelUtil.exportExcel(list, "chat_model", ChatModelVo.class, response);
    }

    /**
     * 获取chat_model详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("ai:chatModel:query")
    @GetMapping("/{id}")
    public R<ChatModelVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(chatModelService.queryById(id));
    }

    /**
     * 新增chat_model
     */
    @SaCheckPermission("ai:chatModel:add")
    @Log(title = "chat_model", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ChatModelBo bo) {
        return toAjax(chatModelService.insertByBo(bo));
    }

    /**
     * 修改chat_model
     */
    @SaCheckPermission("ai:chatModel:edit")
    @Log(title = "chat_model", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ChatModelBo bo) {
        return toAjax(chatModelService.updateByBo(bo));
    }

    /**
     * 删除chat_model
     *
     * @param ids 主键串
     */
    @SaCheckPermission("ai:chatModel:remove")
    @Log(title = "chat_model", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(chatModelService.deleteWithValidByIds(List.of(ids), true));
    }
}
