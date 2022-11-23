package com.eworld.repository;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eword.util.Sortable;
import com.eworld.entity.Product;
import com.eworld.filter.ProductFilter;
import com.eworld.provider.FindPagingJpaRepository;

public class ProductCustomRepositoryImpl implements ProductCustomRepository, FindPagingJpaRepository<Product> {
	
	@PersistenceContext
	EntityManager em;
	
	@Override
	public EntityManager getEntityManager() {
		return em;
	}

	@Override
	public Page<Product> findPaging(ProductFilter filter, Pageable pageable) {
		
		StringBuilder countSqlBuilder = new StringBuilder(100)
				.append("SELECT COUNT(p.id) FROM Product p");
		
		StringBuilder selectSqlBuilder = new StringBuilder(100)
				.append(" SELECT p FROM Product p");
		
		StringBuilder whereClauseSqlBuilder = new StringBuilder(50)
				.append(" WHERE 1=1");
		
		Map<String, Object> parameterMap = new LinkedHashMap<>();
		
		if(StringUtils.isNotBlank(filter.getKeyword())) {
			whereClauseSqlBuilder.append(" AND(");
			
			if(StringUtils.isNumeric(filter.getKeyword())) {
				whereClauseSqlBuilder.append(" p.id = :keywordId OR");
				parameterMap.put("keywordId", Integer.parseInt(filter.getKeyword()));
				
				whereClauseSqlBuilder.append(" p.models = :keywordModels OR");
				parameterMap.put("keywordModels", Integer.parseInt(filter.getKeyword()));
				
				whereClauseSqlBuilder.append(" p.quantity = :keywordQuantity OR");
				parameterMap.put("keywordQuantity", Integer.parseInt(filter.getKeyword()));
			}
			
			whereClauseSqlBuilder.append(" p.name LIKE :keyword OR p.category.name LIKE :keyword OR p.status LIKE :keyword)");
			parameterMap.put("keyword", "%" + filter.getKeyword() + "%");
		}
		
		String orderClause = makeOrderClause(pageable.getSort(), Sortable.PRODUCT, "p");
		
		countSqlBuilder.append(whereClauseSqlBuilder);
		
		selectSqlBuilder
		.append(whereClauseSqlBuilder)
		.append(orderClause);
		
		return findPaging(countSqlBuilder.toString(), selectSqlBuilder.toString(), pageable, parameterMap);
	}

}
