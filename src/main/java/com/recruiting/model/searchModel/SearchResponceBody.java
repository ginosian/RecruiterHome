package com.recruiting.model.searchModel;

import com.recruiting.model.modelUtils.PageWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Martha on 6/13/2017.
 */
public class SearchResponceBody {

    private Map<String, PageWrapper> pageWrapper;
    private Map<String, CandidateSearchModel> candidateSearchModel;

    public SearchResponceBody() {
    }

    public SearchResponceBody(Map<String, PageWrapper> pageWrapper,
            Map<String, CandidateSearchModel> candidateSearchModel) {
        this.pageWrapper = pageWrapper;
        this.candidateSearchModel = candidateSearchModel;
    }

    public SearchResponceBody(PageWrapper pageWrapper, CandidateSearchModel candidateSearchModel) {
        Map<String, PageWrapper> pageWrapperMap = new HashMap<>();
        pageWrapperMap.put("pageWrapper", pageWrapper);
        Map<String, CandidateSearchModel> candidateSearchModelMap = new HashMap<>();
        candidateSearchModelMap.put("candidateSearchModel", candidateSearchModel);
        this.pageWrapper = pageWrapperMap;
        this.candidateSearchModel = candidateSearchModelMap;
    }

    public Map<String, PageWrapper> getPageWrapper() {
        return pageWrapper;
    }

    public void setPageWrapper(Map<String, PageWrapper> pageWrapper) {
        this.pageWrapper = pageWrapper;
    }

    public Map<String, CandidateSearchModel> getCandidateSearchModel() {
        return candidateSearchModel;
    }

    public void setCandidateSearchModel(Map<String, CandidateSearchModel> candidateSearchModel) {
        this.candidateSearchModel = candidateSearchModel;
    }
}
