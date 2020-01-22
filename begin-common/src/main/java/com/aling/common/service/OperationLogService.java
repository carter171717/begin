package com.aling.common.service;

import com.aling.common.entity.OperationLog;
import com.aling.core.dto.log.OperationLogDTO;
import com.baomidou.mybatisplus.service.IService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作日志表 服务类
 * </p>
 *
 * @author wangly
 * @since 2020-01-21
 */
@Service
public interface OperationLogService extends IService<OperationLog> {

    public void addLog(OperationLogDTO operationLogDTO);

}
