package com.zxw.aop.log;

import com.zxw.aop.log.context.DefaultOperatorGetServiceImpl;
import com.zxw.aop.log.context.IOperatorGetService;
import com.zxw.aop.log.function.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportAware;
import org.springframework.context.annotation.Role;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.List;

//@Configuration
@Slf4j
public class LogRecordProxyAutoConfiguration implements ImportAware {

  private AnnotationAttributes enableLogRecord;


  @Bean
  @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
  public LogRecordOperationSource logRecordOperationSource() {
    return new LogRecordOperationSource();
  }

  @Bean
  @ConditionalOnMissingBean(IFunctionService.class)
  public IFunctionService functionService(ParseFunctionFactory parseFunctionFactory) {
    return new DefaultFunctionServiceImpl(parseFunctionFactory);
  }

  @Bean
  public ParseFunctionFactory parseFunctionFactory(@Autowired List<IParseFunction> parseFunctions) {
    return new ParseFunctionFactory(parseFunctions);
  }

  @Bean
  @ConditionalOnMissingBean(IParseFunction.class)
  public DefaultParseFunction parseFunction() {
    return new DefaultParseFunction();
  }


  @Bean
  @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
  public BeanFactoryLogRecordAdvisor logRecordAdvisor(IFunctionService functionService) {
    BeanFactoryLogRecordAdvisor advisor =
            new BeanFactoryLogRecordAdvisor();
    advisor.setLogRecordOperationSource(logRecordOperationSource());
    advisor.setAdvice(logRecordInterceptor(functionService));
    return advisor;
  }

  @Bean
  @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
  public LogRecordInterceptor logRecordInterceptor(IFunctionService functionService) {
    LogRecordInterceptor interceptor = new LogRecordInterceptor();
    interceptor.setLogRecordOperationSource(logRecordOperationSource());
    interceptor.setTenant(enableLogRecord.getString("tenant"));
    interceptor.setFunctionService(functionService);
    return interceptor;
  }

  @Bean
  @ConditionalOnMissingBean(IOperatorGetService.class)
  @Role(BeanDefinition.ROLE_APPLICATION)
  public IOperatorGetService operatorGetService() {
    return new DefaultOperatorGetServiceImpl();
  }

  @Bean
  @ConditionalOnMissingBean(ILogRecordService.class)
  @Role(BeanDefinition.ROLE_APPLICATION)
  public ILogRecordService recordService() {
    return new DefaultLogRecordServiceImpl();
  }

  @Override
  public void setImportMetadata(AnnotationMetadata importMetadata) {
    this.enableLogRecord = AnnotationAttributes.fromMap(
            importMetadata.getAnnotationAttributes(EnableLogRecord.class.getName(), false));
    if (this.enableLogRecord == null) {
      log.info("@EnableCaching is not present on importing class");
    }
  }
}