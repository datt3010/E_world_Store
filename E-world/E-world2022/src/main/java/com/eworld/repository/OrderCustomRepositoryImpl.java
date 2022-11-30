package com.eworld.repository;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eword.util.Sortable;
import com.eworld.entity.Order;
import com.eworld.filter.OrderFilter;
import com.eworld.provider.FindPagingJpaRepository;

public class OrderCustomRepositoryImpl implements OrderCustomRepository, FindPagingJpaRepository<Order> {
	
	@PersistenceContext
	EntityManager em;
	
	@Override
	public EntityManager getEntityManager() {
		return em;
	}

	@Override
	public Page<Order> findPaging(OrderFilter filter, Pageable pageable) {
		StringBuilder countSqlBuilder = new StringBuilder(100)
				.append("SELECT COUNT(o.id) FROM Order o");

		StringBuilder selectSqlBuilder = new StringBuilder(100)
				.append(" SELECT o FROM Order o");
		
		StringBuilder whereClauseSqlBuilder = new StringBuilder(50)
				.append(" WHERE 1=1");
		
		Map<String, Object> parameterMap = new LinkedHashMap<>();
		
		if(StringUtils.isNotBlank(filter.getKeyword())) {
			whereClauseSqlBuilder.append(" AND(");
			
			if(StringUtils.isNumeric(filter.getKeyword())) {
				whereClauseSqlBuilder.append(" o.id = :keywordId OR");
				parameterMap.put("keywordId", Integer.parseInt(filter.getKeyword()));
				whereClauseSqlBuilder.append(" o.totalPrice = :keywordTotal OR");
				parameterMap.put("keywordTotal", Double.parseDouble(filter.getKeyword()));
			}
		}
		
		String orderClause = makeOrderClause(pageable.getSort(), Sortable.ORDER, "o");
		
		countSqlBuilder.append(whereClauseSqlBuilder);
		
		selectSqlBuilder
		.append(whereClauseSqlBuilder)
		.append(orderClause);
		
		return findPaging(countSqlBuilder.toString(), selectSqlBuilder.toString(), pageable, parameterMap);
	}

}
