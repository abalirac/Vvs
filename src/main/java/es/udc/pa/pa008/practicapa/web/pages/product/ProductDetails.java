package es.udc.pa.pa008.practicapa.web.pages.product;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.Request;

import java.util.Locale;
import java.text.Format;
import java.text.NumberFormat;

import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Zone;

import es.udc.pa.pa008.practicapa.model.bidservice.BidService;
import es.udc.pa.pa008.practicapa.model.product.Product;
import es.udc.pa.pa008.practicapa.model.bid.Bid;
import es.udc.pa.pa008.practicapa.model.productservice.ProductService;
import es.udc.pa.pa008.practicapa.web.pages.bid.MakeBid;
import es.udc.pa.pa008.practicapa.web.pages.user.Login;
import es.udc.pa.pa008.practicapa.web.services.AuthenticationPolicy;
import es.udc.pa.pa008.practicapa.web.services.AuthenticationPolicyType;
import es.udc.pa.pa008.practicapa.web.util.UserSession;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

@AuthenticationPolicy(AuthenticationPolicyType.ALL_USERS)
public class ProductDetails {

    private Long productId;

    @SessionState(create = false)
    private UserSession userSession;

    @Property
    private Product product;

    @Property
    private Bid bid;

    @Inject
    private ProductService productService;

    @Inject
    private BidService bidService;

    @Inject
    private Locale locale;

    @InjectPage
    private MakeBid makeBid;

    @Inject
    private Request request;

    @Inject
    PageRenderLinkSource linkSource;

    @InjectComponent
    private Zone ajaxAuctionValue;

    Object onActionFromGotoBid(long productId) {

        if (userSession == null)
            return linkSource.createPageRenderLinkWithContext(Login.class, productId);
        else
            return linkSource.createPageRenderLinkWithContext(MakeBid.class, productId);

    }

    Object onActionFromRefreshZone() {

        return request.isXHR() ? ajaxAuctionValue.getBody() : null;

    }

    public Format getFormat() {
        return NumberFormat.getInstance(locale);
    }

    void onActivate(Long productId) {

        this.productId = productId;
        try {
            product = productService.findProduct(productId);
            bid = bidService.findProductLastBid(productId);
        } catch (InstanceNotFoundException e) {
        }
    }

    Long onPassivate() {
        return productId;
    }

}
