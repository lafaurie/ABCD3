package cu.uci.abos.platform.domain;

public class MenuEntity {

	public String containerMenu;
	public String containerCategory;
	public String functionality;
	public String containerCategoryId;
	public String containerMenuId;
	public Integer index;
	public Integer categoryOrder;

	public MenuEntity(String containerMenu, String containerCategory, String functionality, String containerCategoryId, String containerMenuId) {
		super();
		this.containerMenu = containerMenu;
		this.containerCategory = containerCategory;
		this.functionality = functionality;
		this.containerCategoryId = containerCategoryId;
		this.containerMenuId = containerMenuId;
	}

	public MenuEntity(String containerMenu, String containerCategory, String functionality, String containerCategoryId, String containerMenuId, Integer index,Integer categoryOrder) {
		super();
		this.containerMenu = containerMenu;
		this.containerCategory = containerCategory;
		this.functionality = functionality;
		this.containerCategoryId = containerCategoryId;
		this.containerMenuId = containerMenuId;
		this.index = index;
		this.categoryOrder =categoryOrder;
	}
	
	

}
