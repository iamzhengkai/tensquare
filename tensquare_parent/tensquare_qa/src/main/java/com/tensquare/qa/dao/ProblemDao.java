package com.tensquare.qa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{

    @Query(value = "SELECT * " +
                    "FROM tb_problem " +
                    "WHERE id IN " +
                    "(SELECT problemid FROM tb_pl WHERE labelid = ?) " +
                    "ORDER BY replytime DESC",nativeQuery = true)
    Page<Problem> newList(String label, Pageable pageable);

    @Query(value = "SELECT * FROM tb_problem WHERE id IN (SELECT problemid FROM tb_pl WHERE labelid = ?) AND reply > 0 ORDER BY reply DESC",nativeQuery = true)
    Page<Problem> hotList(String label,Pageable pageable);

    @Query(value = "SELECT * FROM tb_problem WHERE id IN (SELECT problemid FROM tb_pl WHERE labelid = '1') AND reply = 0 ORDER BY updatetime DESC",nativeQuery = true)
    Page<Problem> waitList(String label, Pageable pageable);
}
