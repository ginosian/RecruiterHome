package com.recruiting.controller;

import com.recruiting.domain.*;
import com.recruiting.elastic.file.ElasticCandidate;
import com.recruiting.elastic.service.CandidateElasticService;
import com.recruiting.model.modelUtils.PageWrapper;
import com.recruiting.model.modelUtils.StringItemModel;
import com.recruiting.model.searchModel.CandidateSearchModel;
import com.recruiting.service.CommunicationService;
import com.recruiting.service.CandidateSearchService;
import com.recruiting.service.EmptyEntityCreationService;
import com.recruiting.service.email.EmailService;
import com.recruiting.service.entity.CompanyService;
import com.recruiting.service.entity.CandidateService;
import com.recruiting.service.entity.ConversationService;
import com.recruiting.service.entity.SkillService;
import com.recruiting.utils.ConstantLabels;
import com.recruiting.utils.TypeConversionUtils;
import com.recruiting.validation.CandidateSearchModelValidator;
import com.recruiting.validation.ConversationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Martha on 5/31/2017.
 */
@Controller
@RequestMapping(value = "/company/search")
@PreAuthorize("hasAnyAuthority('ROLE_COMPANY')")
@PropertySource("classpath:texts.properties")
public class SearchController extends AbstractController {

    @Value("${interview.company.message.title}")
    protected String interview_company_message_title;

    @Autowired
    EmptyEntityCreationService emptyEntityCreationService;

    @Autowired
    CandidateSearchService candidateSearchService;

    @Autowired
    SkillService skillService;

    @Autowired
    CandidateElasticService candidateElasticService;

    @Autowired
    CompanyService companyService;

    @Autowired
    CandidateService candidateService;

    @Autowired
    ConversationService conversationService;

    @Qualifier("javaEmailService")
    @Autowired
    EmailService emailService;

    @Autowired
    CommunicationService messagingService;

    @Autowired
    CandidateSearchModelValidator candidateSearchModelValidator;

    @Autowired
    ConversationValidator conversationValidator;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String search(Model model) {
        model.addAttribute("candidateSearchModel", candidateSearchService.createCandidateSearchModel());
        model.addAttribute("pageWrapper", new PageWrapper<ElasticCandidate>());
        return "search";
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String search(
            @Validated @ModelAttribute(value = "candidateSearchModel") CandidateSearchModel candidateSearchModel,
            BindingResult bindingResult,
            Model model,
            Pageable pageable) {

        PageWrapper<ElasticCandidate> pageWrapper;
        if (!bindingResult.hasErrors()) {
            Page<ElasticCandidate> candidatePage = candidateElasticService.searchCandidates(candidateSearchModel, pageable);
            pageWrapper = new PageWrapper<>(candidatePage, "/company/search");
        } else {
            pageWrapper = new PageWrapper<ElasticCandidate>();
        }

        model.addAttribute("candidateSearchModel", candidateSearchModel);
        model.addAttribute("pageWrapper", pageWrapper);


        return "search";
    }

    @RequestMapping(value = "/preview/candidate/{id}", method = RequestMethod.GET)
    public String previewCandidate(@PathVariable("id") Long id, Model model) {
        model = prepareModelForCandidatePreview(model, id, null);
        return "candidate-account-preview";
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @RequestMapping(value = "/interview")
    public String sendInterviewRequest(
            @Validated @ModelAttribute(value = "conversation") Conversation conversation,
            BindingResult bindingResult,
            Model model) {

        if (!approved()) return "redirect:/company/account";

        if (bindingResult.hasErrors()) {
            model = prepareModelForCandidatePreview(model, conversation.getCandidate().getId(), conversation);
            return "candidate-account-preview";
        }
        messagingService.sendInvitationForInterview(conversation);

        return "redirect:/company/search/preview/candidate/" + conversation.getCandidate().getId();
    }

    @ModelAttribute("skills")
    public List<Skill> skills() {
        return (List<Skill>) skillService.findAll();
    }

    @ModelAttribute("timePeriods")
    public List<StringItemModel> timePeriods() {
        return ConstantLabels.TIME_PERIODS_LIST;
    }

    @InitBinder("candidateSearchModel")
    protected void initCandidateSearchModelBinder(final WebDataBinder binder) {
        binder.addValidators(candidateSearchModelValidator);
    }

    @InitBinder("conversation")
    protected void initConversationBinder(final WebDataBinder binder) {
        binder.addValidators(conversationValidator);
    }

    private Model prepareModelForCandidatePreview(Model model, Long id, Conversation conversation) {
        Company company = companyService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Candidate candidate = candidateService.findById(id);
        if (conversation == null) {
            conversation = conversationService.findByCompanyAndCandidate(company, candidate);
            if (conversation == null) {
                conversation = emptyEntityCreationService.emptyConversation();
                conversation.setCompany(company);
                conversation.setCandidate(candidate);
                conversation.setInterview(emptyEntityCreationService.emptyInterview());
                conversation.getInterview().getCompanyMessage().setTitle(interview_company_message_title);
            }
        }

        model.addAttribute("conversation", conversation);
        model.addAttribute("name", TypeConversionUtils.buildReducedFullName(candidate.getName()));
        return model;
    }

}
