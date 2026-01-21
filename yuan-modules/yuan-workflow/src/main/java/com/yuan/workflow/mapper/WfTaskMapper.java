package com.yuan.workflow.mapper;

import com.yuan.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;
import com.yuan.workflow.domain.WfTask;
import com.yuan.workflow.domain.vo.WfTaskVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * wftMapper接口
 *
 
 * @date Sun Dec 28 11:26:41 CST 2025
 */
@Mapper
public interface WfTaskMapper extends BaseMapperPlus<WfTask, WfTaskVo> {

    void finishAll(@Param("instanceId") Long instanceId,@Param("status") String status);

    int updateOtherTodoTasks(@Param("nodeInstanceId") Long nodeInstanceId,
                              @Param("keepTaskId") Long keepTaskId,
                              @Param("action")  String action,
                              @Param("fromStatus") String fromStatus,
                              @Param("toStatus") String toStatus);

    int countTodoByNode(@Param("nodeInstanceId") Long nodeInstanceId,@Param("status") String status);

    int updateAllTodoTasks(@Param("nodeInstanceId") Long nodeInstanceId,
                            @Param("action")  String action,
                            @Param("fromStatus") String fromStatus,
                            @Param("toStatus") String toStatus,
                           @Param("operatorId" ) Long operatorId);

    List<WfTaskVo> selectVoByNodeInstanceId(@Param("nodeInstanceId") Long nodeInstanceId);
}
