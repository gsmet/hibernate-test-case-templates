package org.hibernate.search.bugs;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.lucene.search.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.testsupport.TestForIssue;
import org.junit.Test;

public class YourTestCase extends SearchTestBase {

	@Override
	public Class<?>[] getAnnotatedClasses() {
		return new Class<?>[]{ Course.class };
	}

	@Test
	@TestForIssue(jiraKey = "HSEARCH-NNNNN") // Please fill in the JIRA key of your issue
	@SuppressWarnings("unchecked")
	public void testYourBug() {
		try ( Session s = getSessionFactory().openSession() ) {
			Course yourEntity26 = new Course( 26L, "java", "java" );
			Course yourEntity49 = new Course( 49L, "Java 10", "muy copado" );
			Course yourEntity53 = new Course( 53L, "Java", "java Michael Javson ..." );
			Course yourEntity54 = new Course( 54L, "Java", "java Michael Javson ..." );

			Transaction tx = s.beginTransaction();
			s.persist( yourEntity26 );
			s.persist( yourEntity49 );
			s.persist( yourEntity53 );
			s.persist( yourEntity54 );
			tx.commit();

			// What you are doing
			{
				FullTextSession session = Search.getFullTextSession( s );
				QueryBuilder qb = session.getSearchFactory().buildQueryBuilder().forEntity( Course.class ).get();
				Query query = qb.phrase().onField( "name" ).andField( "description" ).sentence( "java" ).createQuery();

				List<Course> result = (List<Course>) session.createFullTextQuery( query ).list();
				assertEquals( 4, result.size() );
			}

			// What I recommend (the above works but this should be better for a full text search)
			// see http://docs.jboss.org/hibernate/stable/search/reference/en-US/html_single/#_simple_query_string_queries
			{
				FullTextSession session = Search.getFullTextSession( s );
				QueryBuilder qb = session.getSearchFactory().buildQueryBuilder().forEntity( Course.class ).get();
				Query query = qb.simpleQueryString().onField( "name" ).andField( "description" ).matching( "java" ).createQuery();

				List<Course> result = (List<Course>) session.createFullTextQuery( query ).list();
				assertEquals( 4, result.size() );
			}
		}
	}

}
