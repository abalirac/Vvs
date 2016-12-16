package es.udc.pa.pa008.practicapa.web.pages.bid;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pa.pa008.practicapa.model.bidservice.BidService;
import es.udc.pa.pa008.practicapa.model.bidservice.TimeExpiredException;
import es.udc.pa.pa008.practicapa.model.product.Product;
import es.udc.pa.pa008.practicapa.model.productservice.ProductService;
import es.udc.pa.pa008.practicapa.web.services.AuthenticationPolicy;
import es.udc.pa.pa008.practicapa.web.services.AuthenticationPolicyType;
import es.udc.pa.pa008.practicapa.web.util.UserSession;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

@AuthenticationPolicy(AuthenticationPolicyType.AUTHENTICATED_USERS)
public class MakeBid {
	
	private Product product;
	
	@Property
	private double minValue;
	
	@Property
	private double bidValue;
	
	@Inject
	private BidService bidService;
	
	@Inject
	private ProductService productService;
		
    @SessionState(create=false)
    private UserSession userSession;
	
	@InjectPage
	private BidMade bidMade;
	
	@Component
	private Form makeBidForm;
	
	@Inject
	private Messages messages;
	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	void onActivate(Long productId) throws IllegalArgumentException, TimeExpiredException, InstanceNotFoundException{

			this.product = productService.findProduct(productId);
			
			try {
				bidService.findProductLastBid(productId);
				this.minValue = product.getAuctionValue() + 0.5; //+ 0.5 para puja minima
				
			}catch(InstanceNotFoundException e){

				this.minValue = product.getStartingPrice();
				
			}
			
		
	}
	
	Long onPassivate(){
		return product.getProductId();
	}
	
	void onValidateFromMakeBidForm() {

		if (!makeBidForm.isValid()) {
			return;
		}

		try {
			
			bidService.makeBid(userSession.getUserProfileId(), this.product.getProductId(), bidValue);
			
		}catch(TimeExpiredException e){
			
			makeBidForm.recordError(messages.format("error-BidTimeExpired", bidValue));
			
		}catch(InstanceNotFoundException e){
			
			makeBidForm.recordError(messages.format("error-productNotFound", bidValue));
			
		}catch(IllegalArgumentException e){
			
			makeBidForm.recordError(messages.format("error-lowBidValue", bidValue));
			
		}

	}
	
	Object onSuccess(){
		bidMade.setProductId(product.getProductId());
		return bidMade;
		
	}
	
}
