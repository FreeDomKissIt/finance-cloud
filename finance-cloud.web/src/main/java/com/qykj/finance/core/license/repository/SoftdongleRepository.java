package com.qykj.finance.core.license.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qykj.finance.core.license.model.Softdongle;

/**
 *  软件狗实体仓库
 *  创 建 人: wenjing <br/>
 *  版 本 号: V1.0.0 <br/>
 */
@Repository
public interface SoftdongleRepository extends JpaRepository<Softdongle, Integer> {

}
