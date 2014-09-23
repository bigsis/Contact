package service;
 /* Manage instances of Data Access Objects (DAO) used in the app.
 * This enables you to change the implementation of the actual ContactDao
 * without changing the rest of your application.
 * 
 * @author jim
 */
public abstract class DaoFactory {
	// singleton instance of this factory
	private static DaoFactory factory;
	private ContactDao daoInstance;
	
	/** this class shouldn't be instantiated, but constructor must be visible to subclasses. */
	protected DaoFactory() {
		// nothing to do
	}
	
	public static DaoFactory getInstance() {
		if (factory == null) factory = new service.mem.MemDaoFactory();
		return factory;
	}
	
	/**
	 * Get an instance of a data access object for Contact objects.
	 * Subclasses of the base DaoFactory class must provide a concrete
	 * instance of this method that returns a ContactDao suitable
	 * for their persistence framework.
	 * @return instance of Contact's DAO
	 */
	public abstract ContactDao getContactDao();
	
	/**
	 * Shutdown all persistence services.
	 * This method gives the persistence framework a chance to
	 * gracefully save data and close databases before the
	 * application terminates.
	 */
	public abstract void shutdown();
}
