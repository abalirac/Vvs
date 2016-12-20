package es.udc.pa.pa008.practicapa.web.pages.bid;

import java.util.List;

import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pa.pa008.practicapa.model.bid.Bid;
import es.udc.pa.pa008.practicapa.model.bid.BidBlock;
import es.udc.pa.pa008.practicapa.model.bidservice.BidService;
import es.udc.pa.pa008.practicapa.web.services.AuthenticationPolicy;
import es.udc.pa.pa008.practicapa.web.services.AuthenticationPolicyType;
import es.udc.pa.pa008.practicapa.web.util.UserSession;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

@AuthenticationPolicy(AuthenticationPolicyType.AUTHENTICATED_USERS)
public class UserHistory {

    private final static int BIDS_PER_PAGE = 3;

    private int startIndex = 0;
    private BidBlock bidBlock;
    private Bid bid;

    @Inject
    private BidService bidService;

    @SessionState(create = false)
    private UserSession userSession;

    public List<Bid> getBids() {
        return bidBlock.getBids();
    }

    public Bid getBid() {
        return bid;
    }

    public void setBid(Bid bid) {
        this.bid = bid;
    }

    public Object[] getPreviousLinkContext() {

        if (startIndex - BIDS_PER_PAGE >= 0) {
            return new Object[] { startIndex - BIDS_PER_PAGE };
        } else {
            return null;
        }

    }

    public Object[] getNextLinkContext() {

        if (bidBlock.isExistMoreBids()) {
            return new Object[] { startIndex + BIDS_PER_PAGE };
        } else {
            return null;
        }

    }

    int onPassivate() {
        return startIndex;
    }

    void onActivate(int startIndex) throws InstanceNotFoundException {
        this.startIndex = startIndex;
        bidBlock = bidService.findBidsByUser(userSession.getUserProfileId(), startIndex, BIDS_PER_PAGE);
    }

}
