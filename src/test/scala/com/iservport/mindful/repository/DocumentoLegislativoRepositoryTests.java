package com.iservport.mindful.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.iservport.mindful.test.TestConfig;

/**
 * @author mauriciofernandesdecastro
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={TestConfig.class})
@Transactional
public class DocumentoLegislativoRepositoryTests {

	private static final Logger logger = LoggerFactory.getLogger(DocumentoLegislativoRepositoryTests.class);

	@Autowired
	private LegalDocumentRepository repository;
	
	@Test
	public void testGroupByQualifier() {
		logger.info("Lista documentos legislativos.");
		repository.findAll();
	}
	
}
