package com.yuan.ai.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * chat-session对象 chat_session
 *
 * @author ageerle
 * @date Mon Feb 16 14:59:22 CST 2026
 */
@Data
@TableName("chat_session")
public class ChatSession implements Serializable {


    /**
     * 主键
     */
        @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 会话标题
     */
    private String sessionTitle;

    /**
     * 会话内容
     */
    private String sessionContent;

    /**
     * 部门
     */
    private String createDept;

    /**
     * 创建者
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    private Long updateBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 会话ID
     */
    private String conversationId;


}
