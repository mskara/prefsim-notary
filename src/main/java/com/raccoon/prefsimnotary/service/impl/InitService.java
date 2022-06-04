package com.raccoon.prefsimnotary.service.impl;

import com.raccoon.prefsimnotary.model.document.Term;
import com.raccoon.prefsimnotary.model.dto.request.UserRegisterRequestDto;
import com.raccoon.prefsimnotary.model.enums.Status;
import com.raccoon.prefsimnotary.service.TermService;
import com.raccoon.prefsimnotary.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@Service
@AllArgsConstructor
public class InitService {

    private final TermService termService;
    private final UserService userService;

//    @PostConstruct
    public void initialize() {

        //-------------------term-----------------------//
        Term term = new Term();
        term.setStartDate(LocalDate.now());
        term.setEndDate(LocalDate.now());
        term.setStatus(Status.ACTIVE);
        termService.addTerm(term);

        //-------------------notary office-----------------------//
//        notaryOfficeRepository.deleteAll();
//        notaryOfficeService.fetchNotaryOfficeListFromTnb(2021);

        //-------------------user-----------------------//

        UserRegisterRequestDto userRegisterRequestDto = new UserRegisterRequestDto();

        userRegisterRequestDto.setUsername("test.user1");
        userRegisterRequestDto.setPassword("test.user1");
        userRegisterRequestDto.setEmail("test.user1@gmail.com");
        userRegisterRequestDto.setName("test");
        userRegisterRequestDto.setSurname("user");
        userRegisterRequestDto.setNotaryClass(1);
        userRegisterRequestDto.setRegisterNumber(111);

        UserRegisterRequestDto userRegisterRequestDto2 = new UserRegisterRequestDto();
        userRegisterRequestDto2.setUsername("test.user2");
        userRegisterRequestDto2.setPassword("test.user2");
        userRegisterRequestDto2.setEmail("test.user2@gmail.com");
        userRegisterRequestDto2.setName("test");
        userRegisterRequestDto2.setSurname("user2");
        userRegisterRequestDto2.setNotaryClass(1);
        userRegisterRequestDto2.setRegisterNumber(222);

        userService.register(userRegisterRequestDto);
        userService.register(userRegisterRequestDto2);

    }

}
