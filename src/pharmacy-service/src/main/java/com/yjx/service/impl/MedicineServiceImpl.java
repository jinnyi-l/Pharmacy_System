package com.yjx.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjx.entity.Medicine;
import com.yjx.mapper.MedicineMapper;
import com.yjx.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineServiceImpl extends ServiceImpl<MedicineMapper, Medicine> implements MedicineService {

    @Autowired
    MedicineMapper medicineMapper;

    @Override
    public IPage<Medicine> searchMedicine(Integer currentPage, Integer size, String nameOrIdOrSort) {
        Page<Medicine> page = new Page<>(currentPage, size);
        return medicineMapper.searchMedicine(page, nameOrIdOrSort);
    }

    @Override
    public IPage<Medicine> searchMedicine2(Integer currentPage, Integer size, String nameOrIdOrSort) {
        Page<Medicine> page = new Page<>(currentPage, size);
        return medicineMapper.searchMedicine2(page, nameOrIdOrSort);
    }

    @Override
    public List<Medicine> getDiscountDrugs() {
        return medicineMapper.getDiscountDrugs();
    }

    @Override
    public Integer getDrugNums() {
        return medicineMapper.getDrugNums();
    }


}
