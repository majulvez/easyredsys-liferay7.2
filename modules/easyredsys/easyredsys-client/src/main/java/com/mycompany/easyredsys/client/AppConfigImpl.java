package com.mycompany.easyredsys.client;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.miguelangeljulvez.easyredsys.client.AppConfig;
import com.miguelangeljulvez.easyredsys.client.OperationException;
import com.miguelangeljulvez.easyredsys.client.core.Notification;
import com.mycompany.easyredsys.configuration.api.EasyredsysConfiguration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Component(
        configurationPid = "com.mycompany.easyredsys.configuration.api.EasyredsysConfiguration",
        immediate = true,
        property = {},
        service = AppConfig.class
)
public class AppConfigImpl implements AppConfig {

    private String merchantCode;
    private String terminal;
    private String secretKey;
    private boolean testMode;

    @Override
    public String getMerchantCode() {
        return merchantCode;
    }

    @Override
    public String getTerminal() {
        return terminal;
    }

    @Override
    public String getSecretKey() {
        return secretKey;
    }

    @Override
    public boolean isTestMode() {
        return testMode;
    }

    @Activate
    @Modified
    public void activate(Map<String, Object> properties) {
        _log.info("Activando AppConfigImpl....");

        easyredsysConfiguration = ConfigurableUtil.createConfigurable(EasyredsysConfiguration.class, properties);

        merchantCode = easyredsysConfiguration.merchantCode();
        terminal = easyredsysConfiguration.terminal();
        secretKey = easyredsysConfiguration.secretKey();
        testMode = easyredsysConfiguration.testMode();

        _log.info("OK");
    }

    @Override
    public void saveNotification(Notification notification) throws OperationException {

        _log.info("Notificación del banco recibida: " + notification);

        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        String ds_Order = notification.getDs_Order();
        long amount = Long.parseLong(notification.getDs_Amount());
        try {
            Date ds_Date = sdf.parse(notification.getDs_Date());
        } catch (ParseException e) {
            _log.error(e);
        }

        //TODO - P.ej. LLamada al servicio para almancer la info en bbdd. Si hay algún error emitir una OperationException para anular la operación
    }


    private Log _log = LogFactoryUtil.getLog(this.getClass());

    private volatile EasyredsysConfiguration easyredsysConfiguration;
}
