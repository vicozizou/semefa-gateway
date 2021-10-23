package pe.gob.susalud.ws.siteds.schemas;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.4.5
 * 2021-10-22T23:27:06.720-06:00
 * Generated source version: 3.4.5
 *
 */
@WebServiceClient(name = "SitedsService",
                  wsdlLocation = "file:/Users/victoralfaro/dev/SAC/semefa-gateway/src/main/resources/wsdl/Siteds-Iafas.wsdl",
                  targetNamespace = "http://www.susalud.gob.pe/ws/siteds/schemas")
public class SitedsService_Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://www.susalud.gob.pe/ws/siteds/schemas", "SitedsService");
    public final static QName SitedsServiceSOAP = new QName("http://www.susalud.gob.pe/ws/siteds/schemas", "SitedsServiceSOAP");
    static {
        URL url = null;
        try {
            url = new URL("file:/Users/victoralfaro/dev/SAC/semefa-gateway/src/main/resources/wsdl/Siteds-Iafas.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(SitedsService_Service.class.getName())
                .log(java.util.logging.Level.INFO,
                     "Can not initialize the default wsdl from {0}", "file:/Users/victoralfaro/dev/SAC/semefa-gateway/src/main/resources/wsdl/Siteds-Iafas.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public SitedsService_Service(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public SitedsService_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SitedsService_Service() {
        super(WSDL_LOCATION, SERVICE);
    }

    public SitedsService_Service(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public SitedsService_Service(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public SitedsService_Service(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }




    /**
     *
     * @return
     *     returns SitedsService
     */
    @WebEndpoint(name = "SitedsServiceSOAP")
    public SitedsService getSitedsServiceSOAP() {
        return super.getPort(SitedsServiceSOAP, SitedsService.class);
    }

    /**
     *
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns SitedsService
     */
    @WebEndpoint(name = "SitedsServiceSOAP")
    public SitedsService getSitedsServiceSOAP(WebServiceFeature... features) {
        return super.getPort(SitedsServiceSOAP, SitedsService.class, features);
    }

}
