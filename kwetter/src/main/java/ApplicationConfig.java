import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/web")
public class ApplicationConfig extends Application {

    public ApplicationConfig(){
        System.out.println("hello world");
    }
}