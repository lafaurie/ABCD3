package cu.uci.abcd.acquisition.ui.controller;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import cu.uci.abcd.domain.acquisition.Desiderata;
import cu.uci.abcd.domain.acquisition.PurchaseOrder;
import cu.uci.abcd.domain.acquisition.PurchaseRequest;
import cu.uci.abcd.domain.acquisition.Suggestion;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abcd.domain.common.User;
import cu.uci.abcd.domain.management.library.Library;
import cu.uci.abcd.domain.management.library.Provider;
import cu.uci.abcd.domain.management.library.Worker;

public class   AllSpecification {  

	//-------------------ESPECIFICACIONES DE SUGERENCIAS----------------------	  
	
	// RF_AQ2_Listar Sugerencias Pendientes
	// RF_AQ1_Listar todas las Sugerencias realizadas
	public Specification<Suggestion> searchSuggestionByState(final Nomenclator state,final String params){
			
			return new Specification<Suggestion>() 
					  
				{   
				       
				@Override
				public Predicate toPredicate(Root<Suggestion> root,CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) 
				{
					Path<Nomenclator> findByStatePath = root.get("state");
				    
					Predicate predicateAND = null;
					Predicate predicateOR = null;
				
					
					predicateAND = criteriaBuilder.and(criteriaBuilder.and(criteriaBuilder.equal(findByStatePath, state)));

					if (params != null) {
						predicateOR = criteriaBuilder.or(
								criteriaBuilder.like(criteriaBuilder.upper(root.<String> get("title")), "%" + params.toUpperCase() + "%"), 
								criteriaBuilder.like(criteriaBuilder.upper(root.<String> get("author")), "%" + params.toUpperCase() + "%"),
								criteriaBuilder.like(criteriaBuilder.upper(root.<String> get("editorial")), "%" + params.toUpperCase() + "%"));
					}
					if (predicateOR == null) {
						return predicateAND;
					} else {
						return criteriaBuilder.and(predicateAND,
								predicateOR);
					}					
					
				}
		 };
	}
	
	
	//RF_AQ1.3_Filtrar listado de Sugerencias
	public Specification<Suggestion> searchApprovedSuggestionByParametersSpecification(final String title,final String autor,
			final Date dt_since, final Date dt_until, final User userType,final Nomenclator state, final Library library){
		
		return new Specification<Suggestion>() 				  
			{   			       
			@Override
			public Predicate toPredicate(Root<Suggestion> root,CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) 
			{
				List<Predicate> predicateList = new ArrayList<Predicate>();
	
				Path<Date> findByDatePath=root.get("registerDate");
				Path<User> user = root.get("user");
				Path<Nomenclator> stateS = root.get("state");
				Path<Library> libraryPath = root.get("library");
				
				if (title!= null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.<String> get("title")), "%" + title.toUpperCase()+ "%")));
				}
				if (autor != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.<String> get("author")), "%" + autor.toUpperCase()+ "%")));
				}
						
				if (dt_since!= null && dt_until != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.between(findByDatePath, dt_since, dt_until)));
				}
				
				if (userType != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(user, userType)));
				}
				if (state != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(stateS, state)));
				}
				
				if (library != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(libraryPath, library)));
				}
				   
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
	 };
	}
	
	public Specification<Suggestion> searchPendingSuggestionByParametersSpecification(final String title,final String autor, final Nomenclator state){
		
		return new Specification<Suggestion>() 
				  
			{   
			       
			@Override
			public Predicate toPredicate(Root<Suggestion> root,CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) 
			{
				List<Predicate> predicateList = new ArrayList<Predicate>();
	
				Path<Nomenclator> findByStatePath=root.get("state");
				
				predicateList.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.<String> get("title")), "%" + title.toUpperCase()+ "%")));		
				predicateList.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.<String> get("author")), "%" + autor.toUpperCase()+ "%")));
				predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(findByStatePath,state)));

				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
				
			}
	 };
	}
	
	public Specification<Suggestion> searchSuggestionDesiderataId(final Long idDesiderata,final Nomenclator state){
		
		return new Specification<Suggestion>() 
				{
			       
			@Override
			public Predicate toPredicate(Root<Suggestion> root,CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) 
			{
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<Nomenclator> findByStatePath = root.get("state");
				Path<Desiderata> IdPath = root.get("associateDesiderata");
				
				predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(IdPath.get("desiderataID"),idDesiderata)));
				predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(findByStatePath, state)));

				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
	 };
}
	
	public Specification<Suggestion> searchSuggestionByTitle(final String title,final Nomenclator state){
		
		return new Specification<Suggestion>() 
				  
			{   
			       
			@Override
			public Predicate toPredicate(Root<Suggestion> root,CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) 
			{
				List<Predicate> predicateList = new ArrayList<Predicate>();
			
				Path<Nomenclator> findByStatePath=root.get("state");
				
				predicateList.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.<String> get("title")), "%" + title.toUpperCase()+ "%")));		
				predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(findByStatePath, state)));

				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
				
			}
	 };
}

	public Specification<Suggestion> searchSuggestionByAuthor(final String autor,final Nomenclator state){
	
	return new Specification<Suggestion>() 
			  
		{   
		       
		@Override
		public Predicate toPredicate(Root<Suggestion> root,CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) 
		{
			List<Predicate> predicateList = new ArrayList<Predicate>();

			Path<Nomenclator> findByStatePath=root.get("state");
			
			predicateList.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.<String> get("author")), "%" + autor.toUpperCase()+ "%")));
			predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(findByStatePath, state)));

			return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			
		}
 };
}

	public Specification<Suggestion> searchSuggestionByEditor(final String editor,final Nomenclator state){
	
	return new Specification<Suggestion>() 
			  
		{   
		       
		@Override
		public Predicate toPredicate(Root<Suggestion> root,CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) 
		{
			List<Predicate> predicateList = new ArrayList<Predicate>();

			Path<Nomenclator> findByStatePath=root.get("state");
			
			predicateList.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.<String> get("editorial")), "%" + editor.toUpperCase()+ "%")));		
			predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(findByStatePath, state)));

			return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			
		}
 };
}

	
	//-------------------ESPECIFICACIONES DE DESIDERATAS-----------------------------
	
	public Specification<Desiderata> searchDesiderataByPurchaseRequest(final Nomenclator state, final String params){
		
		return new Specification<Desiderata>() 				  
			{   			       
			@Override
			public Predicate toPredicate(Root<Desiderata> root,CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) 
			{
				Path<Nomenclator> findBystatePath = root.get("state");
			    
				Predicate predicateAND = null;
				Predicate predicateOR = null;
			
				
				predicateAND = criteriaBuilder.and(criteriaBuilder.and(criteriaBuilder.equal(findBystatePath, state)));

				if (params != null) {
					predicateOR = criteriaBuilder.or(
							criteriaBuilder.like(criteriaBuilder.upper(root.<String> get("title")), "%" + params.toUpperCase() + "%"), 
							criteriaBuilder.like(criteriaBuilder.upper(root.<String> get("author")), "%" + params.toUpperCase() + "%"),
							criteriaBuilder.like(criteriaBuilder.upper(root.<String> get("editorial")), "%" + params.toUpperCase() + "%"));
					}
				if (predicateOR == null) {
					return predicateAND;
				} else {
					return criteriaBuilder.and(predicateAND,
							predicateOR);
				}
			
			}
			};
	}
	
	public Specification<Desiderata> searchDesiderataByState(final Nomenclator state){
		
		return new Specification<Desiderata>() 
				  
			{   
			       
			@Override
			public Predicate toPredicate(Root<Desiderata> root,CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) 
			{
				List<Predicate> predicateList = new ArrayList<Predicate>();

				Path<Nomenclator> findBystatePath = root.get("state");
			    
				predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(findBystatePath, state)));
		
					
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
			};
	}

	public Specification<Desiderata> searchDesiderataByParametersSpecification(
			final String title, final String author, final String editorial,
			final Date dt_since, final Date dt_until, final Nomenclator state,final Library library) {
	
		return new Specification<Desiderata>() 
				  
			{   
			       
			@Override
			public Predicate toPredicate(Root<Desiderata> root,CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) 
			{
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<Library> libraryPath = root.get("library");
				Path<Nomenclator> findByStatePath=root.get("state");
				Path<Date> findByDatePath=root.get("creationDate");
				
				if(title!=null)
				predicateList.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.<String> get("title")), "%" + title.toUpperCase()+ "%")));		
				if(author!=null)
				predicateList.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.<String> get("author")), "%" + author.toUpperCase()+ "%")));		
				if(editorial!=null)
				predicateList.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.<String> get("editorial")), "%" + editorial.toUpperCase()+ "%")));
				if (library != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(libraryPath, library)));
				}
				predicateList.add(criteriaBuilder.and(criteriaBuilder.between(findByDatePath, dt_since, dt_until)));
				
				if(state!=null)
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(findByStatePath,state)));
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
	 };
	}

	public Specification<Desiderata> searchDesideratasByPurchaseRequestId(final Long idPurchaseOrder,final Nomenclator state){
		
		return new Specification<Desiderata>() 				  
			{   			       
			@Override
			public Predicate toPredicate(Root<Desiderata> root,CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) 
			{
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<Nomenclator> findByStatePath = root.get("state");
				
				Path<PurchaseRequest> IdPath = root.get("purchaseRequest");
				predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(IdPath.get("purchaseRequestID"),idPurchaseOrder)));
				predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(findByStatePath, state)));

				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
	 };
	}

	//-----------------ESPECIFICACIONES DE SOLICITUDES DE COMPRA----------------------

	public Specification<PurchaseRequest> searchPurchaseRequestByState(final Nomenclator state, final String requestNumber){
		
		return new Specification<PurchaseRequest>() 				  
			{   			       
			@Override
			public Predicate toPredicate(Root<PurchaseRequest> root,CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) 
			{
				List<Predicate> predicateList = new ArrayList<Predicate>();

				Path<Nomenclator> findByStatePath = root.get("state");
			    
				predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(findByStatePath, state)));
				
				if(requestNumber!=null)					
				  predicateList.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.<String> get("requestNumber")), "%" + requestNumber.toUpperCase()+ "%")));		
				
							
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));

			}
	 };
}
	
	public Specification<PurchaseRequest> searchPurchaseRequestByParameters(
			final Date since, final Date until, final Worker creatorID,
			final String requestNumber, final Nomenclator state, final Library library) {
	
		return new Specification<PurchaseRequest>() 
				  
			{   
			       
			@Override
			public Predicate toPredicate(Root<PurchaseRequest> root,CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) 
			{
				List<Predicate> predicateList = new ArrayList<Predicate>();
				
				Path<Nomenclator> findByStatePath=root.get("state");
			   	Path<Date> findByDatePath=root.get("creationDate");
			   	Path<Worker> findByCreatorPath=root.get("creator");
			 	Path<Library> libraryPath=root.get("area");
			 	
			   	if (state != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(findByStatePath, state)));
				}
				
				predicateList.add(criteriaBuilder.and(criteriaBuilder.between(findByDatePath, since, until)));
				
				if (creatorID != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(findByCreatorPath, creatorID)));
				}
				
				if(requestNumber!=null)
				  predicateList.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.<String> get("requestNumber")), "%" + requestNumber.toUpperCase()+ "%")));		
				
				if (library != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(libraryPath, library)));
				}
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
				
			}
	 };
	}
	
	
	//-----------------ESPECIFICACIONES DE ORDER DE COMPRA----------------------
	
	public Specification<PurchaseOrder> searchPurchaseOrderByState(final Nomenclator state){
	
		return new Specification<PurchaseOrder>() 
				  
				{   
			
				@Override
				public Predicate toPredicate(Root<PurchaseOrder> root,CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) 
				{
					List<Predicate> predicateList = new ArrayList<Predicate>();

					Path<Nomenclator> findByStatePath = root.get("state");
				    
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(findByStatePath, state)));
								
					return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));

				}
		 };
	}

	public Specification<PurchaseOrder> searchPendingPurchaseOrderByParameters(final long approvedByID,final long createdByID, final Nomenclator state){
		
		return new Specification<PurchaseOrder>() 
				  
			{   
			       
			@Override
			public Predicate toPredicate(Root<PurchaseOrder> root,CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) 
			{
				List<Predicate> predicateList = new ArrayList<Predicate>();
	
				Path<Nomenclator> findByStatePath=root.get("state");
			   	Path<Worker> findByApprovedByPath=root.get("approvedBy");
			   	Path<Worker> findByCreatorPath=root.get("creator");
			   	
			   	if(approvedByID!=-1)
				predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(findByApprovedByPath.get("workerID"), approvedByID)));
				if(createdByID!=-1)
			   	predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(findByCreatorPath .get("workerID"), createdByID)));
				predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(findByStatePath,state)));

				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
				
			}
	 };
	}
	
	public Specification<PurchaseOrder> searchConsultPurchaseOrderByParameters(final Date since, final Date until,final Provider provider,final Integer indice, final Double totalAmount, final Worker creator, final Nomenclator state, final String orderNumber, final Library library){
		
		return new Specification<PurchaseOrder>() 				  
			{   			       
			@Override
			public Predicate toPredicate(Root<PurchaseOrder> root,CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) 
			{
				List<Predicate> predicateList = new ArrayList<Predicate>();
	
				Path<Date> findByDatePath=root.get("creationDate");
			   	Path<Provider> findByProviderPath=root.get("provider");
			   	Path<Worker> findByCreatorPath=root.get("creator");
			 	Path<Nomenclator> findByStatePath=root.get("state");
			 	Path<Library> libraryPath=root.get("library");
			 	
				if (provider != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(findByProviderPath, provider)));
				}
				if (creator != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(findByCreatorPath, creator)));
				}
				if (state != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(findByStatePath, state)));
				}
				
				if(orderNumber!=null)
				predicateList.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.<String> get("orderNumber")), "%" + orderNumber.toUpperCase() + "%")));
				
				if(totalAmount!=null){
				if(indice!=0){
			   		if(indice==1)
					   	predicateList.add(criteriaBuilder.and(criteriaBuilder.greaterThan(root.<Double>get("totalAmount"), totalAmount)));
			   		if(indice==2)
					   	predicateList.add(criteriaBuilder.and(criteriaBuilder.lessThan(root.<Double>get("totalAmount"), totalAmount)));
			   		if(indice==3)
					   	predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(root.<Double>get("totalAmount"), totalAmount)));
			   	}}
			 
				if (library != null) {
					predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(libraryPath, library)));
				}
				
				predicateList.add(criteriaBuilder.and(criteriaBuilder.between(findByDatePath, since, until)));
				
				return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
				
			}
	 };
	}

}

