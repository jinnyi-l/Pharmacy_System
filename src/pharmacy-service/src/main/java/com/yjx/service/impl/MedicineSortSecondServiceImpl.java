package com.yjx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjx.dto.CategorySecondDto;
import com.yjx.entity.MedicineSortSecond;
import com.yjx.mapper.MedicineSortSecondMapper;
import com.yjx.service.MedicineSortSecondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class MedicineSortSecondServiceImpl extends ServiceImpl<MedicineSortSecondMapper, MedicineSortSecond> implements MedicineSortSecondService {

    @Autowired
    private MedicineSortSecondMapper medicineSortSecondMapper;

    @Override
    public Integer getDrugCategoryNum() {

        return medicineSortSecondMapper.selectCount(new QueryWrapper<MedicineSortSecond>().gt("id", 0));
    }

    @Override
    public List<CategorySecondDto> getAllSecondCategory(Integer firstCategoryId) {
        return medicineSortSecondMapper.getAllSecondCategory(firstCategoryId);
    }
}
