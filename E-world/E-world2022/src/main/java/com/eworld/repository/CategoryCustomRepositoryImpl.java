package com.eworld.repository;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eword.util.Sortable;
import com.eworld.entity.Category;
import com.eworld.filter.CategoryFilter;
import com.eworld.provider.FindPagingJpaRepository;

public class CategoryCustomRepositoryImpl implements CategoryCustomRepository, FindPagingJpaRepository<Category> {
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public EntityManager getEntityManager() {
		return em;
	}

	@Override
	public Page<Category> findPaging(CategoryFilter filter, Pageable pageable) {
		StringBuilder countSqlBuilder = new StringBuilder(100)
				.append("SELECT COUNT(c.id) FROM Category c");
		
		StringBuilder selectSqlBuilder = new StringBuilder(100)
				.append(" SELECT c FROM Category c");
		
		StringBuilder whereClauseSqlBuilder = new StringBuilder(50)
				.append(" WHERE 1=1");
		
		Map<String, Object> parameterMap = new LinkedHashMap<>();
		
		if(StringUtils.isNotBlank(filter.getKeyword())) {
			whereClauseSqlBuilder.append(" AND(");
			
			if(StringUtils.isNumeric(filter.getKeyword())) {
				whereClauseSqlBuilder.append(" c.id = :keywordId OR");
				parameterMap.put("keywordId", Integer.parseInt(filter.getKeyword()));
				
				whereClauseSqlBuilder.append(" c.age = :keywordAge OR");
				parameterMap.put("keywordAge", Integer.parseInt(filter.getKeyword()));
			}
			
			whereClauseSqlBuilder.append(" c.name LIKE :keyword OR c.status LIKE :keyword)");
			parameterMap.put("keyword", "%" + filter.getKeyword() + "%");
		}
		
		String orderClause = makeOrderClause(pageable.getSort(), Sortable.CATEGORY, "c");
		
		countSqlBuilder.append(whereClauseSqlBuilder);
		
		selectSqlBuilder
		.append(whereClauseSqlBuilder)
		.append(orderClause);
		
		return findPaging(countSqlBuilder.toString(), selectSqlBuilder.toString(), pageable, parameterMap);
	}
	
	
}
