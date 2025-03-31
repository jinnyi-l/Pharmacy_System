package com.yjx.service.impl;

import com.yjx.entity.LogOperation;
import com.yjx.mapper.LogOperationMapper;
import com.yjx.service.LogOperationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class LogOperationServiceImpl extends ServiceImpl<LogOperationMapper, LogOperation> implements LogOperationService {

}
