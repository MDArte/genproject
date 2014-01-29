
// license-header java merge-point
/**
 * Attention: Generated source! Do not modify by hand!
 */
package br.ufrrj.maratona.cd;

/**
 * <p>
 * Factory class.
 * Is able to find and create objects of type Problema.
 * The Hibernate <em>subclass</em> inheritance
 * strategy is followed.
 * Those can be described as follows:
 * </p>
 * @see br.ufrrj.maratona.cd.Problema
 */
import org.hibernate.Criteria;
import org.hibernate.Session;

import br.ufrj.coppetec.DataObject;
import br.ufrrj.maratona.vo.ProblemaVO;
 
public class ProblemaDAOImpl extends ProblemaDAO
{
	protected Object handleFilter(DataObject vo) throws br.ufrrj.maratona.cd.DAOException 
    {
		Session session = AbstractDAO.currentSession();
		Criteria criterios = session.createCriteria(Problema.class);
		
		if(vo instanceof ProblemaVO)
		{
			
		}

		return criterios;
    }
    
    protected org.hibernate.Criteria handleXmlExport(br.ufrj.coppetec.ValueObject vo, org.hibernate.Session session) throws br.ufrrj.maratona.cd.DAOException
    {
    	return null;
    }
    
}
