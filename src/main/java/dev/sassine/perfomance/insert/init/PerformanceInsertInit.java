package dev.sassine.perfomance.insert.init;

import static dev.sassine.perfomance.insert.model.Product.builder;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import dev.sassine.perfomance.insert.model.Product;
import dev.sassine.perfomance.insert.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PerformanceInsertInit {

	@Autowired
	private ProductRepository repository;

	@Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
	private int batchSize;

	@EventListener(ApplicationReadyEvent.class)
	public void initPerformanceTest() {
		
		
		int totalObjects = 10000;
		
		log.info("Start creating {} objects", totalObjects);
		

		Instant start = Instant.now();
		List<Product> products = range(0, totalObjects)
								.mapToObj(val -> builder()
										.name(format("product-%s", val))
										.build())
								.collect(toList());
		
		log.info("Finished creating {} objects in: {} seconds ", totalObjects,Duration.between(start, Instant.now()).toSeconds());

		
		log.info("Start Inserting ! ");

		start = Instant.now();
		
		for (int i = 0; i < totalObjects; i += batchSize) {
			if (i + batchSize > totalObjects) {
				List<Product> productsAdd = products.subList(i, totalObjects - 1);
				repository.saveAll(productsAdd);
				break;
			}
			List<Product> productsAdd = products.subList(i, i + batchSize);
			repository.saveAll(productsAdd);
		}
		
		log.info("Finished batch inserting {} objects in data base in: {} seconds", totalObjects,Duration.between(start, Instant.now()).toSeconds());
		
		
		/* uncomment to see the between time to insert - descomente para ver a diferença de tempo entre as inserções.
		* 
		* start = Instant.now();
		*
		* repository.saveAll(products);
		*
		* log.info("Finished traditional inserting {} objects in data base in: {} seconds", totalObjects,Duration.between(start, Instant.now()).toSeconds());
		*
		*/
	}
}
