package com.qykj.finance.sys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qykj.finance.sys.model.QuickQueryParams;

/**
 *  快捷查询参数仓库
 *  创 建 人: wenjing <br/>
 *  版 本 号: V1.0.0 <br/>
 */
@Repository
public interface QuickQueryParamsRepository extends JpaRepository<QuickQueryParams, Integer> {

}
