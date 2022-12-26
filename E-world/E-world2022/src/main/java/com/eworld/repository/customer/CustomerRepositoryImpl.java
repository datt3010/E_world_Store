package com.eworld.repository.customer;

import com.eword.util.Sortable;
import com.eworld.entity.Account;
import com.eworld.filter.CustomerFilter;
import com.eworld.provider.FindPagingJpaRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.LinkedHashMap;
import java.util.Map;

public class CustomerRepositoryImpl implements CustomerCustomRepository, FindPagingJpaRepository<Account> {

	@PersistenceContext
	private EntityManager em;
	
	
	@Override
	public Page<Account> findPaging(CustomerFilter filter, Pageable pageable) {
		
		StringBuilder countSqlBuilder = new StringBuilder(100)
				.append("SELECT COUNT(c.id) FROM Account c")
				.append(" INNER JOIN AccountRole ar ON ar.accountId = c.id");

		StringBuilder selectSqlBuilder = new StringBuilder(100)
				.append(" SELECT c FROM Account c")
				.append(" INNER JOIN AccountRole ar ON ar.accountId = c.id");
		
		StringBuilder whereClauseSqlBuilder = new StringBuilder(50)
				.append(" WHERE 1=1")
				.append(" AND ar.roleId=3")
				.append(" AND c.status LIKE 'ACTIVE'");

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

		if(filter.getMonth() !=null){
			whereClauseSqlBuilder.append(" AND MONTH(c.createAt)= :month ");
			parameterMap.put("month", filter.getMonth());
		}

		if(filter.getYears() !=null){
			whereClauseSqlBuilder.append(" AND YEAR(c.createAt) = :years ");
			parameterMap.put("years", filter.getYears());
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
