package com.ms.productservice;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.productservice.dto.ProductDto;
import com.ms.productservice.model.Product;
import com.ms.productservice.repository.ProductRepository;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

	@Container
	/* container shared between all test methods in this class */
	private static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ObjectMapper objectMapper;

	@DynamicPropertySource
	static void setMongoProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
		System.out.println(mongoDBContainer.getReplicaSetUrl());
		dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Test
	void testProductCreate() throws Exception {

		ProductDto productDto = ProductDto.builder()
				.name("test product")
				.description("test product description")
				.price(BigDecimal.valueOf(12.34))
				.build();

		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(productDto))
				)
		.andExpect(status().isCreated());

		// verify data has actually saved to the database
		assertEquals(1, productRepository.findAll().size());
	}

	@Test
	void testProductGet() throws Exception {

		Product dbData = Product.builder()
				.name("get test")
				.description("get test description")
				.price(BigDecimal.valueOf(12.345))
				.build();
		productRepository.save(dbData);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/product")
				.accept(MediaType.APPLICATION_JSON)
				)
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andReturn();

		String json = result.getResponse().getContentAsString();

		Page<Product> pageData = objectMapper.readValue(json, new TypeReference<PageDto<Product>>() {});

		assertTrue(pageData.getTotalElements() > 0);
		assertTrue(pageData.getContent().size() > 0);

		Product product = pageData.getContent().stream().filter(p -> p.getName().equals("get test")).findFirst().orElse(null);

		assertNotNull(product);
		assertNotNull(product.getId());
		assertTrue(!product.getId().isEmpty());
		assertEquals("get test", product.getName());
		assertEquals("get test description", product.getDescription());
		assertEquals(12.345, product.getPrice().doubleValue());

	}

	static class PageDto<T> extends PageImpl<T>{

		private static final long serialVersionUID = -3024724431718858192L;

		public PageDto(List<T> content) {
			super(content);
		}

		public PageDto(List<T> content, Pageable pageable, long total) {
			super(content, pageable, total);
		}

		@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
	    public PageDto(
	    		@JsonProperty("content") List<T> content,
	    		@JsonProperty("number") int number,
	    		@JsonProperty("size") int size,
	    		@JsonProperty("totalElements") Long totalElements,
	    		@JsonProperty("pageable") JsonNode pageable,
	    		@JsonProperty("last") boolean last,
	    		@JsonProperty("totalPages") int totalPages,
	    		@JsonProperty("sort") JsonNode sort,
	    		@JsonProperty("first") boolean first,
	    		@JsonProperty("numberOfElements") int numberOfElements) {

	        super(content, PageRequest.of(number, size), totalElements);
	    }

	}
}
