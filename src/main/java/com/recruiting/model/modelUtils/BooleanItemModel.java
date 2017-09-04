package com.recruiting.model.modelUtils;

/**
 * Created by Martha on 5/5/2017.
 */
public class BooleanItemModel extends Model {

    private Boolean agreed;

    public BooleanItemModel() {
    }

    public BooleanItemModel(Boolean agreed) {
        this.agreed = agreed;
    }

    public Boolean getAgreed() {
        return agreed;
    }

    public void setAgreed(Boolean agreed) {
        this.agreed = agreed;
    }
}
