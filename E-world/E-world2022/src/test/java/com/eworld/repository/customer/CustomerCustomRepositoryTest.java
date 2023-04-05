package com.eworld.repository.customer;

import com.eworld.contstant.Gender;
import com.eworld.contstant.UserStatus;
import com.eworld.entity.Account;
import com.eworld.entity.AccountProfile;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerCustomRepositoryTest {
    @Mock
    private  CustomerRepository customerRepository;

    Instant instant = Instant.now();
    Date date = Date.from(instant);

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByUsername() {
        Account account = new Account();
        String username = "admin";
        account.setUsername(username);
        account.setPassword("admin2002");
        when(customerRepository.findByUsername(account.getUsername())).thenReturn(account);
        Account result = customerRepository.findByUsername(username);
        assertEquals(username, result.getUsername());
    }

    @Test
    void findPaging() {

    }

    @Test
    void injectedComponentsAreNotNull(){
        assertThat(customerRepository).isNotNull();
    }

    @Test
    void getProfileByUsername(){
        String username = "datt30102002";
        String firstName = "admin";
        Instant instant = Instant.now();
        Date date = Date.from(instant);
        Account account = new Account();
        account.setId(1);
        account.setUsername("datt30102002");
        account.setPassword("admin");
        AccountProfile accountProfile = new AccountProfile();
        accountProfile.setAccount(account);
        accountProfile.setEmail("test@gmail.com");
        accountProfile.setImage("1.jpg");
        accountProfile.setAddress("test");
        accountProfile.setFirstName("admin");
        accountProfile.setLastName("admin");
        accountProfile.setGioitinh(Gender.Nam);
        accountProfile.setStatus(UserStatus.ACTIVE);
        accountProfile.setDateOfBirth(date);
        accountProfile.setNationality("test");
        accountProfile.setPhone("0909442487");
        account.setAccountProfile(accountProfile);

        when(customerRepository.getByUsername(username)).thenReturn(account.getAccountProfile());
        AccountProfile result = customerRepository.getByUsername(username);
        assertEquals(UserStatus.ACTIVE,result.getStatus());
        assertEquals("admin",result.getLastName());
        assertEquals(firstName, result.getFirstName());
    }


}