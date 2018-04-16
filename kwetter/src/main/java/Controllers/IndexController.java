package Controllers;

import Domain.User;
import Services.KwetterService;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.logging.Logger;

@ManagedBean(name = "IndexController", eager = true)
public class IndexController {
    private String username;
    private String password;
    private String originalURL;
    private KwetterService kwetterService;
    private Logger logger = Logger.getLogger(getClass().getName());

    public IndexController(){
        //Empty because bean
    }

    @PostConstruct
    public void init(){
        ExternalContext exC = FacesContext.getCurrentInstance().getExternalContext();
        originalURL = exC.getRequestMap().get(RequestDispatcher.FORWARD_REQUEST_URI).toString();

        if(originalURL == null || originalURL.isEmpty()){
            originalURL = exC.getRequestContextPath() + "/profile.xhtml";
        }
        else{
            String oQuery = exC.getRequestMap().get(RequestDispatcher.FORWARD_QUERY_STRING).toString();
            if(oQuery != null){
                originalURL += "?" + oQuery;
            }
        }
    }

    @Inject
    public void setKwetterService(KwetterService ks){kwetterService = ks;}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void login() throws IOException{
        try{
            FacesContext context = FacesContext.getCurrentInstance();
            ExternalContext externalContext = context.getExternalContext();
            HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
            try{
                request.login(username, password);
                User user = null;
                if(kwetterService.getBaseUserService().login(username, password)){
                    user = kwetterService.getBaseUserService().getUserByName(username);
                }
                externalContext.getSessionMap().put("user", user);
                externalContext.redirect(originalURL);
            }
            catch(ServletException se){
                logger.warning("Unknown login: " + se.getMessage());
                context.addMessage(null, new FacesMessage("Unknown login"));
            }

        }
        catch(Exception x){
            logger.severe("Login error: " + x.getMessage());
        }
    }

    public void logout(){
        try{
            ExternalContext exc = FacesContext.getCurrentInstance().getExternalContext();
            exc.invalidateSession();
            exc.redirect(exc.getRequestContextPath() + "/index.xhtml");
        }catch(Exception x ){
            logger.severe("logout error: " + x.getMessage());
        }
    }
}
