//package br.com.example.desafiotecnico.config;
//
//import lombok.RequiredArgsConstructor;
//import org.quartz.Scheduler;
//import org.quartz.SchedulerException;
//import org.quartz.spi.TriggerFiredBundle;
//import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.quartz.SchedulerFactoryBean;
//import org.springframework.scheduling.quartz.SpringBeanJobFactory;
//
//@Configuration
//@RequiredArgsConstructor
//public class SchedulerConfiguration {
//
//    @RequiredArgsConstructor
//    public class AutowireCapableBeanJobFactory extends SpringBeanJobFactory {
//
//        private final AutowireCapableBeanFactory beanFactory;
//
//        @Override
//        protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
//            Object jobInstance = super.createJobInstance(bundle);
//            this.beanFactory.autowireBean(jobInstance);
//            this.beanFactory.initializeBean(jobInstance, null);
//            return jobInstance;
//        }
//    }
//
//    @Bean
//    public SchedulerFactoryBean schedulerFactory(ApplicationContext applicationContext) {
//        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
//        schedulerFactoryBean.setJobFactory(new AutowireCapableBeanJobFactory(applicationContext.getAutowireCapableBeanFactory()));
//        return schedulerFactoryBean;
//    }
//
//    @Bean
//    public Scheduler scheduler(ApplicationContext applicationContext) throws SchedulerException {
//        Scheduler scheduler = schedulerFactory(applicationContext).getScheduler();
//        scheduler.start();
//        return scheduler;
//    }
//}