package com.github.pioneeryi;

import org.apache.calcite.avatica.AvaticaConnection;
import org.apache.calcite.avatica.AvaticaFactory;
import org.apache.calcite.avatica.UnregisteredDriver;

import java.util.Properties;

public class MixQueryConnection extends AvaticaConnection {

    protected MixQueryConnection(UnregisteredDriver driver, AvaticaFactory factory, String url, Properties info) {
        super(driver, factory, url, info);
    }
}
