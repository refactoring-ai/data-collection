package refactoringml.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class Database {

	private SessionFactory sf;
	private Session session;

	public Database(SessionFactory sf) {
		this.sf = sf;
	}

	public void openSession() {
		this.session = sf.openSession();
		session.beginTransaction();
	}

	public void commit() {
		this.session.getTransaction().commit();
		this.session.close();
		this.session = null;
	}


	public void persist(Object obj) {
		session.persist(obj);
	}

	public void update(Object obj) {
		session.update(obj);
	}

	public void close() {
		try {
			if (session != null)
				session.close();
		} catch(Exception e) {
			session = null;
		}
	}

	public Yes findYes(Long yesId) {
		return session.get(Yes.class, yesId);
	}

	public boolean projectExists(String gitUrl) {
		return session.createQuery("from Project p where p.gitUrl = :gitUrl")
				.setParameter("gitUrl", gitUrl)
				.getResultList().size() > 0;
	}
}
