# easyredsys-liferay7.2

Integración de Easyredsys en Liferay 7.2 para la gestión de pagos online a través de Redsys


## Instala easyredsys
Despliega los modules

`./gradlew clean deploy`

## Configura easyredsys
Configura los datos de tu pasarela de pago desde panel de control -> configuración -> configuración de instancia -> other -> easyredsys

![Easyredsys](https://github.com/majulvez/easyredsys-liferay7.2/blob/master/images/easyredsys.png)


## Crear el endpoint para la respuesta del banco
Crear el CXF extender y el SOAP extender (no sé por qué no pilla la configuración de /configuration al desplegar)

Panel de control -> configuración -> configuración de sistema -> platform -> web api 

### CFX Extender
![CFX](https://github.com/majulvez/easyredsys-liferay7.2/blob/master/images/cfx.png)

### SOAP Extender
![SOAP](https://github.com/majulvez/easyredsys-liferay7.2/blob/master/images/soap.png)


## Personaliza las urls del portlet de ejmplo
Modifica el view.jsp del mvc-portlet, sustituye las urls de ok, ko y notificación por las tuyas y despliega
`
OrderCES orderCES = new OrderCES.Builder(appConfig)
        .transactionType(TransactionType.AUTORIZACION)
        .currency(Currency.EUR)
        .consumerLanguage(Language.SPANISH)
        .order(ds_Order) //Identificador que debes generar con 12 caracteres máximo y único
        .amount((long) (precio * 100)) //La cantidad es sin decimales. Por ejemplo, 10.00€ correspondería al valor 1000
        .productDescription("Descripción del producto")
        .payMethods(PaymentMethod.TARJETA)
        .urlOk("https://www.mycompany.com?notificacion=ok") //La página de vuelta del banco si todo ha ido bien
        .urlKo("https://www.mycompany.com?notificacion=ko") //La página de vuelta del banco si algo ha ido mal
        .urlNotification("https://www.mycompany.com" + "/o/easyredsys-ws/InotificacionSISImpl") //El endpoint donde está escuchando easyredsys-server
        .build();
`

## Úsalo
Pon el portlet en una página. Y empieza a vender :)

![Portlet](https://github.com/majulvez/easyredsys-liferay7.2/blob/master/images/portlet.png)

### Otros
Si estás usando JDK 11 añade esta línea al bin/setenv.sh
`CATALINA_OPTS="$CATALINA_OPTS -Djavax.xml.bind.context.factory=com.sun.xml.bind.v2.ContextFactory -Dcom.sun.xml.bind.v2.runtime.JAXBContextImpl.fastBoot=true -Dcom.sun.xml.bind.v2.bytecode.ClassTailor.noOptimize=true"`