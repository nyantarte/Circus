package com.personal.circus.Moneybook;

import com.personal.circus.Config;

public class MoneyBookConfig {
    public final static String IO_DIR=Config.IO_DIR+"/moneybook";
    public final static String CONFIG_FILE_NAME=IO_DIR+"/config.json";
    public final static String TRANSACTION_FILE_NAME= Config.IO_DIR+"/moneybook/transaction.csv";
    public final static int TRANSACTION_MAX=0xFF;
    public final static String LAGACY_FILE_NAME="Moneybook/data.csv";


}
