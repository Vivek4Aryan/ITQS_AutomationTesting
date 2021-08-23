package alm;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;

@XmlRootElement(name = "EntityResourceDescriptor")
@XmlType(name = "", propOrder = { "table", "name", "label", "supportsHistory", "supportsAttachment", "supportsLock",
		"supportsGrouping", "supportsMailing", "supportsStorage", "supportsMultiValue", "supportsWorkflow",
		"supportsDataHidingFilter", "supportsVC", "supportsSubtypes", "supportsCopying", "extensionName", "siteEntity",
		"attributes", "firstLevelResource", "secondLevelResources" })
public final class AlmEntityDescriptor {

	private String baseUrl;
	private String collectionName;
	private Collection<AttributeElement> attributes;
	private String extensionName;
	private IsFirstLevelResourceElement isFirstLevelResource;
	private String label;
	private String name;
	private Boolean isSiteEntity;
	private BooleanWithURLType supportsAttachment;
	private Boolean supportsDataHidingFilter;
	private BooleanWithURLType supportsGrouping;
	private BooleanWithURLType supportsHistory;
	private BooleanWithURLType supportsLock;
	private BooleanWithURLType supportsMailing;
	private BooleanWithURLType supportsStorage;
	private SupportSubtypeInfo supportsSubtypes;
	private Boolean supportsMultiValue;
	private BooleanWithURLType supportsVC;
	private Boolean supportsWorkflow;
	private String table;
	private Collection<IsSecondLevelResourceElement> secondLevelResources;
	private BooleanWithURLType supportsCopying;

	/**
	 * Gets base url
	 * 
	 * @return string
	 */
	@XmlAttribute(name = "baseUrl", required = true)
	public final String getBaseUrl() {
		return baseUrl;
	}

	/**
	 * Sets base url
	 * 
	 * @param baseUrl
	 */
	public final void setBaseUrl(final String baseUrl) {
		this.baseUrl = baseUrl;
	}

	/**
	 * Gets collection name
	 * 
	 * @return string
	 */
	@XmlAttribute(name = "collectionName", required = true)
	public final String getCollectionName() {
		return collectionName;
	}

	/**
	 * Sets collection name
	 * 
	 * @param collectionName
	 */
	public final void setCollectionName(final String collectionName) {
		this.collectionName = collectionName;
	}

	/**
	 * Gets attributes
	 * 
	 * @return Collection<AttributeElement>
	 */
	@XmlElementWrapper(name = "Attributes")
	@XmlElement(name = "Attribute")
	public Collection<AttributeElement> getAttributes() {
		if (attributes == null) {
			attributes = new ArrayList<AttributeElement>();
		}
		return attributes;
	}

	/**
	 * Gets second level resources
	 * 
	 * @return Collection<IsSecondLevelResourceElement>
	 */
	@XmlElementWrapper(name = "SecondLevelResources", required = false)
	@XmlElement(name = "IsSecondLevelResource")
	public Collection<IsSecondLevelResourceElement> getSecondLevelResources() {
		if (secondLevelResources == null) {
			secondLevelResources = new ArrayList<IsSecondLevelResourceElement>();
		}
		return secondLevelResources;
	}

	/**
	 * Gets extension name
	 * 
	 * @return string
	 */
	@XmlElement(name = "ExtensionName")
	public String getExtensionName() {
		return extensionName;
	}

	/**
	 * Sets extension name
	 * 
	 * @param extensionName
	 */
	public void setExtensionName(String extensionName) {
		this.extensionName = extensionName;
	}

	/**
	 * Gets first level resource
	 * 
	 * @return IsFirstLevelResourceElement
	 */
	@XmlElement(name = "IsFirstLevelResource")
	public IsFirstLevelResourceElement isFirstLevelResource() {
		return isFirstLevelResource;
	}

	/**
	 * Sets first level resource
	 * 
	 * @param firstLevelResource
	 */
	public void setFirstLevelResource(IsFirstLevelResourceElement firstLevelResource) {
		isFirstLevelResource = firstLevelResource;
	}

	/**
	 * Gets label
	 * 
	 * @return string
	 */
	@XmlElement(name = "Label")
	public String getLabel() {
		return label;
	}

	/**
	 * Sets label
	 * 
	 * @param label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Gets name
	 * 
	 * @return string
	 */
	@XmlElement(name = "Name")
	public String getName() {
		return name;
	}

	/**
	 * Sets name
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets site entity
	 * 
	 * @return boolean
	 */
	@XmlElement(name = "IsSiteEntity")
	public Boolean getSiteEntity() {
		return isSiteEntity;
	}

	/**
	 * Sets site entity
	 * 
	 * @param siteEntity
	 */
	public void setSiteEntity(Boolean siteEntity) {
		isSiteEntity = siteEntity;
	}

	/**
	 * Gets supports attachment
	 * 
	 * @return BooleanWithURLType
	 */
	@XmlElement(name = "SupportsAttachment")
	public BooleanWithURLType getSupportsAttachment() {
		return supportsAttachment;
	}

	/**
	 * Sets supports attachment
	 * 
	 * @param supportsAttachment
	 */
	public void setSupportsAttachment(BooleanWithURLType supportsAttachment) {
		this.supportsAttachment = supportsAttachment;
	}

	/**
	 * Gets supports data hiding filter
	 * 
	 * @return Boolean
	 */
	@XmlElement(name = "SupportsDataHidingFilter")
	public Boolean getSupportsDataHidingFilter() {
		return supportsDataHidingFilter;
	}

	/**
	 * Sets supports data hiding filter
	 * 
	 * @param supportsDataHidingFilter
	 */
	public void setSupportsDataHidingFilter(Boolean supportsDataHidingFilter) {
		this.supportsDataHidingFilter = supportsDataHidingFilter;
	}

	/**
	 * Gets supports grouping
	 * 
	 * @return BooleanWithURLType
	 */
	@XmlElement(name = "SupportsGrouping")
	public BooleanWithURLType getSupportsGrouping() {
		return supportsGrouping;
	}

	/**
	 * Sets supports grouping
	 * 
	 * @param supportsGrouping
	 */
	public void setSupportsGrouping(BooleanWithURLType supportsGrouping) {
		this.supportsGrouping = supportsGrouping;
	}

	/**
	 * Gets supports history
	 * 
	 * @return BooleanWithURLType
	 */
	@XmlElement(name = "SupportsHistory")
	public BooleanWithURLType getSupportsHistory() {
		return supportsHistory;
	}

	/**
	 * Sets supports history
	 * 
	 * @param supportsHistory
	 */
	public void setSupportsHistory(BooleanWithURLType supportsHistory) {
		this.supportsHistory = supportsHistory;
	}

	/**
	 * Gets supports lock
	 * 
	 * @return BooleanWithURLType
	 */
	@XmlElement(name = "SupportsLock")
	public BooleanWithURLType getSupportsLock() {
		return supportsLock;
	}

	/**
	 * Sets supports lock
	 * 
	 * @param supportsLock
	 */
	public void setSupportsLock(BooleanWithURLType supportsLock) {
		this.supportsLock = supportsLock;
	}

	/**
	 * Gets supports mailing
	 * 
	 * @return BooleanWithURLType
	 */
	@XmlElement(name = "SupportsMailing")
	public BooleanWithURLType getSupportsMailing() {
		return supportsMailing;
	}

	/**
	 * Gets supports storage
	 * 
	 * @return BooleanWithURLType
	 */
	@XmlElement(name = "SupportsStorage")
	public BooleanWithURLType getSupportsStorage() {
		return supportsStorage;
	}

	/**
	 * Sets supports mailing
	 * 
	 * @param supportsMailing
	 */
	public void setSupportsMailing(BooleanWithURLType supportsMailing) {
		this.supportsMailing = supportsMailing;
	}

	/**
	 * Sets supports storage
	 * 
	 * @param supportsStorage
	 */
	public void setSupportsStorage(BooleanWithURLType supportsStorage) {
		this.supportsStorage = supportsStorage;
	}

	/**
	 * Gets supports multi value
	 * 
	 * @return Boolean
	 */
	@XmlElement(name = "SupportsMultiValue")
	public Boolean getSupportsMultiValue() {
		return supportsMultiValue;
	}

	/**
	 * Sets supports multi value
	 * 
	 * @param supportsMultiValue
	 */
	public void setSupportsMultiValue(Boolean supportsMultiValue) {
		this.supportsMultiValue = supportsMultiValue;
	}

	/**
	 * Gets supports sub types
	 * 
	 * @return SupportSubtypeInfo
	 */
	@XmlElement(name = "SupportsSubtypes")
	public SupportSubtypeInfo getSupportsSubtypes() {
		return supportsSubtypes;
	}

	/**
	 * Sets supports sub types
	 * 
	 * @param supportsSubtypes
	 */
	public void setSupportsSubtypes(SupportSubtypeInfo supportsSubtypes) {
		this.supportsSubtypes = supportsSubtypes;
	}

	/**
	 * Gets supports vc
	 * 
	 * @return BooleanWithURLType
	 */
	@XmlElement(name = "SupportsVC")
	public BooleanWithURLType getSupportsVC() {
		return supportsVC;
	}

	/**
	 * Sets supports vc
	 * 
	 * @param supportsVC
	 */
	public void setSupportsVC(BooleanWithURLType supportsVC) {
		this.supportsVC = supportsVC;
	}

	/**
	 * Gets supports work flow
	 * 
	 * @return Boolean
	 */
	@XmlElement(name = "SupportsWorkflow")
	public Boolean getSupportsWorkflow() {
		return supportsWorkflow;
	}

	/**
	 * Sets supports work flow
	 * 
	 * @param supportsWorkflow
	 */
	public void setSupportsWorkflow(Boolean supportsWorkflow) {
		this.supportsWorkflow = supportsWorkflow;
	}

	/**
	 * Gets table
	 * 
	 * @return string
	 */
	@XmlElement(name = "Table")
	public String getTable() {
		return table;
	}

	/**
	 * Sets table
	 * 
	 * @param table
	 */
	public void setTable(String table) {
		this.table = table;
	}

	/**
	 * Gets supports copying
	 * 
	 * @return BooleanWithURLType
	 */
	@XmlElement(name = "SupportsCopying")
	public BooleanWithURLType getSupportsCopying() {
		return supportsCopying;
	}

	/**
	 * Sets supports copying
	 * 
	 * @param supportsCopying
	 */
	public void setSupportsCopying(BooleanWithURLType supportsCopying) {
		this.supportsCopying = supportsCopying;
	}

	@XmlRootElement
	public static class BooleanWithURLType {

		private String url;
		private Boolean value;

		/**
		 * Gets url
		 * 
		 * @return string
		 */
		@XmlAttribute(name = "url", required = true)
		public String getUrl() {
			return url;
		}

		/**
		 * Sets url
		 * 
		 * @param url
		 */
		public void setUrl(String url) {
			this.url = url;
		}

		/**
		 * Gets value
		 * 
		 * @return Boolean
		 */
		@XmlValue
		public Boolean getValue() {
			return value;
		}

		/**
		 * Sets value
		 * 
		 * @param value
		 */
		public void setValue(Boolean value) {
			this.value = value;
		}
	}

	@XmlRootElement(name = "IsFirstLevelResource")
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class IsFirstLevelResourceElement extends BooleanWithURLType {

		@XmlAttribute(name = "supportsPOST")
		protected boolean supportsPOST;

		@XmlAttribute(name = "supportsGET")
		protected boolean supportsGET;

		@XmlAttribute(name = "supportsPUT")
		protected boolean supportsPUT;

		@XmlAttribute(name = "supportsDELETE")
		protected boolean supportsDELETE;

		/**
		 * Gets supports post
		 * 
		 * @return boolean
		 */
		public boolean isSupportsPOST() {
			return supportsPOST;
		}

		/**
		 * Gets supports get
		 * 
		 * @return boolean
		 */
		public boolean isSupportsGET() {
			return supportsGET;
		}

		/**
		 * Gets supports put
		 * 
		 * @return boolean
		 */
		public boolean isSupportsPUT() {
			return supportsPUT;
		}

		/**
		 * Gets supports delete
		 * 
		 * @return boolean
		 */
		public boolean isSupportsDELETE() {
			return supportsDELETE;
		}
	}

	@XmlRootElement(name = "Attribute")
	@XmlType
	public static final class AttributeElement {

		private String name;
		private String value;

		/**
		 * Gets name
		 * 
		 * @return string
		 */
		@XmlAttribute(name = "name")
		public String getName() {
			return name;
		}

		/**
		 * Sets name
		 * 
		 * @param name
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * Gets value
		 * 
		 * @return string
		 */
		@XmlValue
		public String getValue() {
			return value;
		}

		/**
		 * Sets value
		 * 
		 * @param value
		 */
		public void setValue(String value) {
			this.value = value;
		}
	}

	@XmlRootElement(name = "IsSecondLevelResource")
	@XmlAccessorType(XmlAccessType.FIELD)
	public static final class IsSecondLevelResourceElement extends IsFirstLevelResourceElement {

		@XmlAttribute(name = "parentEntity", required = true)
		private String parentEntity;

		/**
		 * Gets parent entity
		 * 
		 * @return string
		 */
		public String getParentEntity() {
			return parentEntity;
		}
	}

	@XmlRootElement
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class SupportSubtypeInfo extends BooleanWithURLType {

		@XmlAttribute(name = "subTypeFieldName")
		protected String subTypeFieldName;

		/**
		 * Gets sub type field name
		 * 
		 * @return string
		 */
		public String getSubTypeFieldName() {
			return subTypeFieldName;
		}
	}
}
