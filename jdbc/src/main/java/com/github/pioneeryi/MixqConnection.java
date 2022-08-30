package com.github.pioneeryi;

import org.apache.calcite.avatica.AvaticaConnection;
import org.apache.calcite.avatica.AvaticaFactory;
import org.apache.calcite.avatica.UnregisteredDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class MixqConnection extends AvaticaConnection {

    private static final Logger logger = LoggerFactory.getLogger(MixqConnection.class);

    protected MixqConnection(UnregisteredDriver driver, AvaticaFactory factory, String url, Properties info) {
        super(driver, factory, url, info);
    }
}
