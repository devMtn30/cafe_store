package com.payhere.kimsan;

import com.payhere.kimsan.product.domain.search.SearchItemRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(excludeFilters = @ComponentScan.Filter(
	type = FilterType.ASSIGNABLE_TYPE,
	classes = SearchItemRepository.class))
@SpringBootApplication
public class KimsanApplication {

	public static void main(String[] args) {
		SpringApplication.run(KimsanApplication.class, args);
	}

}
