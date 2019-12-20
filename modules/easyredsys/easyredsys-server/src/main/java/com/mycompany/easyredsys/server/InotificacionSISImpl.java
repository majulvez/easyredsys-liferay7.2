package com.mycompany.easyredsys.server;


import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.miguelangeljulvez.easyredsys.client.AppConfig;
import com.miguelangeljulvez.easyredsys.server.ws.literal.InotificacionSIS;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.jws.WebParam;
import javax.jws.WebService;


@Component(
        immediate = true,
        property = {"easyredsys.jax.ws.service=true"},
        service = InotificacionSIS.class
)
@WebService(serviceName = "InotificacionSIS", endpointInterface = "com.miguelangeljulvez.easyredsys.server.ws.literal.InotificacionSIS")
public class InotificacionSISImpl extends com.miguelangeljulvez.easyredsys.server.ws.literal.InotificacionSISImpl {

    @Override
    public String notificacion(@WebParam(name = "datoEntrada", targetNamespace = "http://notificador.webservice.sis.redsys.es") String datoEntrada) {
        return super.notificacion(datoEntrada);
    }

    @Reference
    public void setAppConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    private Log _log = LogFactoryUtil.getLog(this.getClass());
}
