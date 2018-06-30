package git.janek79.javaeese.eese.configuration;

import java.beans.PropertyVetoException;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@ComponentScan("git.janek79.javaeese.eese")
@EnableTransactionManagement
public class ConfigClass {
	@Bean(destroyMethod="close")
	public ComboPooledDataSource myDataSource() {
		ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
		try {
			comboPooledDataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		
		comboPooledDataSource.setJdbcUrl("jdbc:mysql://db4free.net:3306/sql5vlp5dhq78?useSSL=false");
		comboPooledDataSource.setUser("sqlfbatpp0u82");
		comboPooledDataSource.setPassword("hiberjava");
		
		comboPooledDataSource.setInitialPoolSize(3);
		comboPooledDataSource.setMinPoolSize(1);
		comboPooledDataSource.setMaxPoolSize(20);
		comboPooledDataSource.setMaxIdleTime(30000);
		
		return comboPooledDataSource;
	}
	
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		Properties hibernateProperties = sessionFactory.getHibernateProperties();
		
		hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		hibernateProperties.setProperty("hibernate.show_sql", "true");
		hibernateProperties.setProperty("hibernate.enable_lazy_load_no_trans", "true");
		
		sessionFactory.setPackagesToScan("git.janek79.javaeese.eese.entity");
		sessionFactory.setDataSource(myDataSource());
		sessionFactory.setHibernateProperties(hibernateProperties);
		
		return sessionFactory;
	}
	
	@Bean
	@Autowired
	public HibernateTransactionManager hibernateTransactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory);
		
		return transactionManager;
	}
}
