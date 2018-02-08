package com.jean.mybatis.generator.controller;

import com.jean.mybatis.generator.constant.DatabaseType;
import com.jean.mybatis.generator.support.provider.IMetadataProvider;
import javafx.fxml.Initializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;


/**
 * Created by jinshubao on 2017/4/8.
 */
public abstract class BaseController implements Initializable {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

}
