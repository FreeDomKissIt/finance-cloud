package com.qykj.finance.sys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qykj.finance.sys.model.RoleMenu;

/**
 *  角色仓库
 *  创 建 人: wenjing <br/>
 *  版 本 号: V1.0.0 <br/>
 */
@Repository
public interface RoleMenuRepository extends JpaRepository<RoleMenu, Integer> {

}
