package es.udc.pa.pa008.practicapa.model.bid;

import java.util.List;

import es.udc.pa.pa008.practicapa.model.bid.Bid;

public class BidBlock {
    private List<Bid> bids;
    private boolean existMoreBids;

    public BidBlock(List<Bid> bids, boolean existMoreBids) {

        this.bids = bids;
        this.existMoreBids = existMoreBids;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public boolean isExistMoreBids() {
        return existMoreBids;
    }

    public void setExistMoreBids(boolean existMoreBids) {
        this.existMoreBids = existMoreBids;
    }
}
