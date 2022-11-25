package com.eworld.repository;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eword.util.Sortable;
import com.eworld.entity.Account;
import com.eworld.filter.CustomerFilter;
import com.eworld.provider.FindPagingJpaRepository;

public class CustomerRepositoryImpl implements CustomerCustomRepository, FindPagingJpaRepository<Account> {

	@PersistenceContext
	private EntityManager em;
	
	
	@Override
	public Page<Account> findPaging(CustomerFilter filter, Pageable pageable) {
		
		StringBuilder countSqlBuilder = new StringBuilder(100)
				.append("SELECT COUNT(c.id) FROM Account c");
		
		StringBuilder selectSqlBuilder = new StringBuilder(100)
				.append(" SELECT c FROM Account c");
		
		StringBuilder whereClauseSqlBuilder = new StringBuilder(50)
				.append(" WHERE 1=1");

		Map<String, Object> parameterMap = new LinkedHashMap<>();
		
		if(StringUtils.isNotBlank(filter.getKeyword())) {
			whereClauseSqlBuilder.append(" AND(");
			
			if(StringUtils.isNumeric(filter.getKeyword())) {
				whereClauseSqlBuilder.append(" c.id = :keywordInt OR c.age = :keywordInt OR");
				parameterMap.put("keywordInt", Integer.parseInt(filter.getKeyword()));
			}
			
			whereClauseSqlBuilder.append(" c.firstName LIKE :keyword OR c.lastName LIKE :keyword OR c.gioitinh LIKE :keyword)");
			parameterMap.put("keyword", "%" + filter.getKeyword() + "%");
		}
		
		String orderClause = makeOrderClause(pageable.getSort(), Sortable.CUSTOMER, "c");
		
		countSqlBuilder.append(whereClauseSqlBuilder);
		
		selectSqlBuilder
		.append(whereClauseSqlBuilder)
		.append(orderClause);
		
		return findPaging(countSqlBuilder.toString(), selectSqlBuilder.toString(), pageable, parameterMap);
	}


	@Override
	public EntityManager getEntityManager() {
		return em;
	}

}
