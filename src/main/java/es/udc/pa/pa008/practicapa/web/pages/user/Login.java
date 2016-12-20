package es.udc.pa.pa008.practicapa.web.pages.user;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Cookies;
import org.apache.tapestry5.services.PageRenderLinkSource;

import es.udc.pa.pa008.practicapa.model.userprofile.UserProfile;
import es.udc.pa.pa008.practicapa.model.userservice.IncorrectPasswordException;
import es.udc.pa.pa008.practicapa.model.userservice.UserService;
import es.udc.pa.pa008.practicapa.web.pages.Index;
import es.udc.pa.pa008.practicapa.web.pages.bid.MakeBid;
import es.udc.pa.pa008.practicapa.web.services.AuthenticationPolicy;
import es.udc.pa.pa008.practicapa.web.services.AuthenticationPolicyType;
import es.udc.pa.pa008.practicapa.web.util.CookiesManager;
import es.udc.pa.pa008.practicapa.web.util.UserSession;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

@AuthenticationPolicy(AuthenticationPolicyType.NON_AUTHENTICATED_USERS)
public class Login {

    private Long productId;

    @Property
    private String loginName;

    @Property
    private String password;

    @Property
    private boolean rememberMyPassword;

    @SessionState(create = false)
    private UserSession userSession;

    @Inject
    private Cookies cookies;

    @Component
    private Form loginForm;

    @Inject
    private Messages messages;

    @Inject
    private UserService userService;

    @Inject
    PageRenderLinkSource linkSource;

    private UserProfile userProfile = null;

    void onActivate(Long productId) {
        if (productId != null)
            this.productId = productId;
    }

    Long onPassivate() {
        return this.productId;
    }

    void onValidateFromLoginForm() {

        if (!loginForm.isValid()) {
            return;
        }

        try {
            userProfile = userService.login(loginName, password, false);
        } catch (InstanceNotFoundException e) {
            loginForm.recordError(messages.get("error-authenticationFailed"));
        } catch (IncorrectPasswordException e) {
            loginForm.recordError(messages.get("error-authenticationFailed"));
        }

    }

    Object onSuccess() {

        userSession = new UserSession();
        userSession.setUserProfileId(userProfile.getUserProfileId());
        userSession.setFirstName(userProfile.getFirstName());

        if (rememberMyPassword) {
            CookiesManager.leaveCookies(cookies, loginName, userProfile.getEncryptedPassword());
        }
        if (productId != null)
            return linkSource.createPageRenderLinkWithContext(MakeBid.class, productId);
        else
            return Index.class;

    }

}
