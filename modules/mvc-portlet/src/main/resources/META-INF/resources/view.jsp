<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="com.miguelangeljulvez.easyredsys.client.core.OrderCES" %>
<%@ page import="com.miguelangeljulvez.easyredsys.client.util.TransactionType" %>
<%@ page import="com.miguelangeljulvez.easyredsys.client.util.Currency" %>
<%@ page import="com.miguelangeljulvez.easyredsys.client.util.PaymentMethod" %>
<%@ page import="com.miguelangeljulvez.easyredsys.client.core.MessageOrderCESRequest" %>
<%@ page import="com.miguelangeljulvez.easyredsys.client.util.Language" %>
<%@ page import="java.util.Random" %>

<%@ include file="/init.jsp" %>

<%
	String ds_Order = getRandomOrder();
	long precio = 10; //10€

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

    MessageOrderCESRequest messageOrderCESRequest = new MessageOrderCESRequest.Builder(orderCES).build();
%>

<div class="text-center">
    <h1>Easyredsys TEST</h1>
    <p>&nbsp;</p>
    <fieldset>
        <legend>Compra Comercio Electrónico Seguro (CES)<br />Los datos bancarios los pide el banco</legend>
        <p>
            Tarjeta: 4548812049400004<br />
            Caducidad: 12/20<br />
            CVV: 123<br />
        </p>

        <form action="<%=messageOrderCESRequest.getRedsysUrl()%>" method="post">
            <input name="Ds_SignatureVersion" value="<%=messageOrderCESRequest.getDs_SignatureVersion()%>" type="hidden"/>
            <input name="Ds_MerchantParameters" value="<%=messageOrderCESRequest.getDs_MerchantParameters()%>" type="hidden"/>
            <input name="Ds_Signature" value="<%=messageOrderCESRequest.getDs_Signature()%>" type="hidden"/>
            <input type="submit" value="<liferay-ui:message key='ir-a-pasarela-de-pago' />" class="btn btn-primary"/>
        </form>
    </fieldset>

</div>


<%!
    private String getRandomOrder() {
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 12; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }

        return sb.toString();
    }
%>