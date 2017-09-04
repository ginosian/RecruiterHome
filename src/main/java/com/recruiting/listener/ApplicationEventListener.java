package com.recruiting.listener;

import com.recruiting.elastic.file.ElasticCandidate;
import com.recruiting.repository.UserRepository;
import com.recruiting.service.CommunicationService;
import com.recruiting.service.entity.CompanyService;
import com.recruiting.service.entity.CandidateService;
import com.recruiting.service.entity.GenericCrudService;
import com.recruiting.utils.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by Martha on 4/25/2017.
 */
@Component
public class ApplicationEventListener {

    private static boolean DATA_LOADED = false;

    @Autowired
    private CommunicationService messagingService;

    @Autowired
    private GenericCrudService genericCrudService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CandidateService candidateService;

    private UserRepository userRepository;

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    public void setGenericCrudService(GenericCrudService genericCrudService) {
        this.genericCrudService = genericCrudService;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @EventListener({ContextRefreshedEvent.class})
    public void onContextRefreshedEvent() {
        if (!DATA_LOADED) {
            DATA_LOADED = true;
            createElasticSearchIndexes();
            DataLoader.createInitialData(genericCrudService);
            DataLoader.createRandomCandidates(genericCrudService, 20);
            DataLoader.createRandomCompanies(genericCrudService, 20);
//            DataLoader.createCandidateWithRealEmail(genericCrudService);
//            DataLoader.createCompanyWithRealEmail(genericCrudService);
//            createConversation(companyService, candidateService, genericCrudService);
            DataLoader.createAdmin(genericCrudService);
        }
    }

    private void createElasticSearchIndexes() {
        if (elasticsearchTemplate.indexExists("candidate"))
            elasticsearchTemplate.deleteIndex("candidate");
        elasticsearchTemplate.createIndex("candidate");
        elasticsearchTemplate.putMapping(ElasticCandidate.class);
        elasticsearchTemplate.refresh("candidate");
    }

    private void createConversation(CompanyService companyService, CandidateService candidateService, GenericCrudService genericCrudService) {
        DataLoader.randomConversation(true, 0, "a0@a0.com", "a0@a0.cst", companyService, candidateService, messagingService);
        DataLoader.randomConversation(false, 5, "a0@a0.com", "c2@c2.cst", companyService, candidateService, messagingService);
        DataLoader.randomConversation(true, 5, "a0@a0.com", "e4@e4.cst", companyService, candidateService, messagingService);
        DataLoader.randomConversation(false, 5, "a0@a0.com", "g6@g6.cst", companyService, candidateService, messagingService);
        DataLoader.randomConversation(true, 5, "a0@a0.com", "i8@i8.cst", companyService, candidateService, messagingService);
        DataLoader.randomConversation(false, 5, "a0@a0.com", "k10@k10.cst", companyService, candidateService, messagingService);
        DataLoader.randomConversation(true, 5, "a0@a0.com", "m12@m12.cst", companyService, candidateService, messagingService);
        DataLoader.randomConversation(false, 5, "a0@a0.com", "o14@o14.cst", companyService, candidateService, messagingService);
        DataLoader.randomConversation(true, 5, "a0@a0.com", "q16@q16.cst", companyService, candidateService, messagingService);
        DataLoader.randomConversation(false, 5, "a0@a0.com", "s18@s18.cst", companyService, candidateService, messagingService);


        DataLoader.randomConversation(true, 0, "a0@a0.com", "a0@a0.cst", companyService, candidateService, messagingService);
        DataLoader.randomConversation(false, 5, "b1@b1.com", "a0@a0.cst", companyService, candidateService, messagingService);
        DataLoader.randomConversation(true, 5, "c2@c2.com", "a0@a0.cst", companyService, candidateService, messagingService);
        DataLoader.randomConversation(false, 5, "d3@d3.com", "a0@a0.cst", companyService, candidateService, messagingService);
        DataLoader.randomConversation(true, 5, "e4@e4.com", "a0@a0.cst", companyService, candidateService, messagingService);
        DataLoader.randomConversation(false, 5, "f5@f5.com", "a0@a0.cst", companyService, candidateService, messagingService);
        DataLoader.randomConversation(true, 5, "g6@g6.com", "a0@a0.cst", companyService, candidateService, messagingService);
        DataLoader.randomConversation(false, 5, "h7@h7.com", "a0@a0.cst", companyService, candidateService, messagingService);
        DataLoader.randomConversation(true, 5, "i8@i8.com", "a0@a0.cst", companyService, candidateService, messagingService);
        DataLoader.randomConversation(false, 5, "j9@j9.com", "a0@a0.cst", companyService, candidateService, messagingService);


//        DataLoader.randomConversation(true, 0, "marta.ginosyan.files@gmail.com", "a0@a0.cst", companyService, candidateService, messagingService);
//        DataLoader.randomConversation(false, 5, "marta.ginosyan.files@gmail.com", "c2@c2.cst", companyService, candidateService, messagingService);
//        DataLoader.randomConversation(true, 5, "marta.ginosyan.files@gmail.com", "e4@e4.cst", companyService, candidateService, messagingService);
//        DataLoader.randomConversation(false, 5, "marta.ginosyan.files@gmail.com", "g6@g6.cst", companyService, candidateService, messagingService);
//        DataLoader.randomConversation(true, 5, "marta.ginosyan.files@gmail.com", "i8@i8.cst", companyService, candidateService, messagingService);

//        DataLoader.randomConversation(true, 0, "a0@a0.com", "marta.ginosyan.test1@gmail.com", companyService, candidateService, messagingService);
//        DataLoader.randomConversation(false, 5, "b1@b1.com", "marta.ginosyan.test1@gmail.com", companyService, candidateService, messagingService);
//        DataLoader.randomConversation(true, 5, "c2@c2.com", "marta.ginosyan.test1@gmail.com", companyService, candidateService, messagingService);
//        DataLoader.randomConversation(false, 5, "d3@d3.com", "marta.ginosyan.test1@gmail.com", companyService, candidateService, messagingService);
//        DataLoader.randomConversation(true, 5, "e4@e4.com", "marta.ginosyan.test1@gmail.com", companyService, candidateService, messagingService);
//        DataLoader.randomConversation(false, 5, "f5@f5.com", "marta.ginosyan.test1@gmail.com", companyService, candidateService, messagingService);
//
//          DataLoader.randomConversation(3, "b0@b0.com", "a0@a0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(3, "b0@b0.com", "b0@b0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(3, "b0@b0.com", "c0@c0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(3, "b0@b0.com", "d0@d0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(3, "b0@b0.com", "e0@e0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(3, "b0@b0.com", "f0@f0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(3, "b0@b0.com", "g0@g0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(3, "b0@b0.com", "h0@h0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(3, "b0@b0.com", "i0@i0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(3, "b0@b0.com", "j0@j0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(3, "b0@b0.com", "k0@k0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(3, "b0@b0.com", "l0@l0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(3, "b0@b0.com", "m0@m0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(3, "b0@b0.com", "n0@n0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(3, "b0@b0.com", "o0@o0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(3, "b0@b0.com", "p0@p0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(3, "b0@b0.com", "q0@q0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(3, "b0@b0.com", "r0@r0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(3, "b0@b0.com", "s0@s0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(3, "b0@b0.com", "t0@t0.cst", companyService, candidateService, genericCrudService);
//
//        DataLoader.randomConversation(4, "c0@c0.com", "a0@a0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(4, "c0@c0.com", "b0@b0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(4, "c0@c0.com", "c0@c0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(4, "c0@c0.com", "d0@d0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(4, "c0@c0.com", "e0@e0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(4, "c0@c0.com", "f0@f0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(4, "c0@c0.com", "g0@g0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(4, "c0@c0.com", "h0@h0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(4, "c0@c0.com", "i0@i0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(4, "c0@c0.com", "j0@j0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(4, "c0@c0.com", "k0@k0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(4, "c0@c0.com", "l0@l0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(4, "c0@c0.com", "m0@m0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(4, "c0@c0.com", "n0@n0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(4, "c0@c0.com", "o0@o0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(4, "c0@c0.com", "p0@p0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(4, "c0@c0.com", "q0@q0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(4, "c0@c0.com", "r0@r0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(4, "c0@c0.com", "s0@s0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(4, "c0@c0.com", "t0@t0.cst", companyService, candidateService, genericCrudService);
//
//
//        DataLoader.randomConversation(5, "d0@d0.com", "a0@a0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(5, "d0@d0.com", "b0@b0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(5, "d0@d0.com", "c0@c0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(5, "d0@d0.com", "d0@d0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(5, "d0@d0.com", "e0@e0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(5, "d0@d0.com", "f0@f0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(5, "d0@d0.com", "g0@g0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(5, "d0@d0.com", "h0@h0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(5, "d0@d0.com", "i0@i0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(5, "d0@d0.com", "j0@j0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(5, "d0@d0.com", "k0@k0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(5, "d0@d0.com", "l0@l0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(5, "d0@d0.com", "m0@m0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(5, "d0@d0.com", "n0@n0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(5, "d0@d0.com", "o0@o0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(5, "d0@d0.com", "p0@p0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(5, "d0@d0.com", "q0@q0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(5, "d0@d0.com", "r0@r0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(5, "d0@d0.com", "s0@s0.cst", companyService, candidateService, genericCrudService);
//        DataLoader.randomConversation(5, "d0@d0.com", "t0@t0.cst", companyService, candidateService, genericCrudService);
    }

}
