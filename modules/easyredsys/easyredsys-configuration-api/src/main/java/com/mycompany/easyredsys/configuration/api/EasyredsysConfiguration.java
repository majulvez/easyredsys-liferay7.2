package com.mycompany.easyredsys.configuration.api;

import aQute.bnd.annotation.metatype.Meta;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

@ExtendedObjectClassDefinition(
        category = "easyredsys",
        scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
        id = "com.mycompany.easyredsys.configuration.api.EasyredsysConfiguration",
        localization = "content/Language",
        name = "variables"
)
public interface EasyredsysConfiguration {

    @Meta.AD(required = false, deflt = "061978060")
    String merchantCode();

    @Meta.AD(required = false, deflt = "001")
    String terminal();

    @Meta.AD(required = false, deflt = "sq7HjrUOBfKmC576ILgskD5srU870gJ7")
    String secretKey();

    @Meta.AD(required = false, deflt = "true")
    boolean testMode();
}