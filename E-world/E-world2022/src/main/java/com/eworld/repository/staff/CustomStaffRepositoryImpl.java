package com.eworld.repository.staff;

import com.eword.util.Sortable;
import com.eworld.entity.Account;
import com.eworld.filter.StaffFilter;
import com.eworld.provider.FindPagingJpaRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.LinkedHashMap;
import java.util.Map;

public class CustomStaffRepositoryImpl implements CustomStaffRepository, FindPagingJpaRepository<Account> {

    @PersistenceContext
    EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Page<Account> findPaging(StaffFilter filter, Pageable pageable) {

        StringBuilder countSqlBuilder = new StringBuilder(100)
                .append(" SELECT COUNT(c.id) FROM Account c")
                .append(" LEFT JOIN FETCH AccountRole ar ON ar.accountId = c.id");

        StringBuilder selectSqlBuilder = new StringBuilder(100)
                .append(" SELECT c FROM Account c")
                .append(" LEFT JOIN FETCH AccountRole ar ON ar.accountId = c.id");

        StringBuilder whereClauseSqlBuilder = new StringBuilder(50)
                .append(" WHERE 1=1")
                .append(" AND ar.roleId=2")
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

        String orderClause = makeOrderClause(pageable.getSort(), Sortable.STAFF, "c");

        countSqlBuilder.append(whereClauseSqlBuilder);

        selectSqlBuilder
                .append(whereClauseSqlBuilder)
                .append(orderClause);

        return findPaging(countSqlBuilder.toString(), selectSqlBuilder.toString(), pageable, parameterMap);
    }

}
