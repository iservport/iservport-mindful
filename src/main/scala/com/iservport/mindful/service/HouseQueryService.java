//package com.iservport.mindful.service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.inject.Inject;
//
//import com.iservport.network.service.MindfulNetworkKeyName;
//import org.helianto.core.internal.QualifierAdapter;
//import org.helianto.core.internal.SimpleCounter;
//import org.helianto.core.repository.EntityReadAdapter;
//import org.helianto.core.repository.EntityRepository;
//import org.helianto.user.repository.UserEntityAdapter;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort.Direction;
//import org.springframework.stereotype.Service;
//
//import com.iservport.mindful.repository.EntityStatsRepository;
//
///**
// * Serviço para leitura de parlamentos.
// *
// * @author mauriciofernandesdecastro
// */
//@Service
//public class HouseQueryService {
//
//	@Inject
//	private EntityRepository entityRepository;
//
//	@Inject
//	private EntityStatsRepository entityStatsRepository;
//
//	/**
//	 * Lista qualificadores.
//	 *
//	 * @param entityId
//	 */
//	public List<QualifierAdapter> qualifier(int entityId) {
//		List<QualifierAdapter> qualifierList
//		= QualifierAdapter.qualifierAdapterList(MindfulNetworkKeyName.values());
//
//		// realiza a contagem
//		qualifierCount(entityId, qualifierList);
//
//	return qualifierList;
//	}
//
//	/**
//	 * Método auxiliar para contar os qualificadores.
//	 *
//	 * @param entityId
//	 * @param qualifierList
//	 */
//	protected void qualifierCount(int entityId, List<QualifierAdapter> qualifierList) {
//
//		// TODO extrair o operador do usuário autenticado
//		//
//		// Hoje mantemos fixo = 1
//		//
//
//		// conta o entidades agrupados por categoria
//		List<SimpleCounter> counterListAll
//			= entityStatsRepository.countActiveEntitiesGroupByType(1);
//
//		// para cada qualificador preenchemos as contagens
//		for (QualifierAdapter qualifier: qualifierList) {
//			qualifier
//			.setCountItems(counterListAll);
//		}
//
//	}
//
//	/**
//	 * Lista entidades associadas a seus usuários.
//	 *
//	 * @param identityId
//	 * @param entityType
//	 */
//	public Page<EntityReadAdapter> listEntities(int identityId, Character entityType) {
//		Pageable page = new PageRequest(0, 100, Direction.DESC, "lastEvent");
//		List<Integer> exclusions = new ArrayList<Integer>();
//		exclusions.add(0);
//		Page<EntityReadAdapter> userAdaptaterPage =
//				entityRepository.findByIdentityIdAndEntityType(
//						identityId, entityType, 'A', 'A', exclusions, page);
//		return userAdaptaterPage;
//	}
//
//	public UserEntityAdapter entity(Integer userId) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public Page<UserEntityAdapter> search(int entityId, String searchString,
//			Integer pageNumber) {
//		// TODO pesquisar parlamentos
//		return null;
//	}
//
//}
