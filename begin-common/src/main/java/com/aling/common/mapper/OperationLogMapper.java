package com.aling.common.mapper;

import com.aling.common.entity.OperationLog;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 操作日志表 Mapper 接口
 * </p>
 *
 * @author wangly
 * @since 2020-01-21
 */
@Mapper
public interface OperationLogMapper extends BaseMapper<OperationLog> {

}
