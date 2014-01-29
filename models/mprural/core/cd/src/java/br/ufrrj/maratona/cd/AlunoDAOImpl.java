// license-header java merge-point
/**
 * Attention: Generated source! Do not modify by hand!
 */
package br.ufrrj.maratona.cd;

/**
 * <p>
 * Factory class.
 * Is able to find and create objects of type Aluno.
 * The Hibernate <em>subclass</em> inheritance
 * strategy is followed.
 * Those can be described as follows:
 * </p>
 * @see br.ufrrj.maratona.cd.Aluno
 */
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;

import br.ufrj.coppetec.DataObject;
import br.ufrrj.maratona.vo.AlunoVO;

public class AlunoDAOImpl extends AlunoDAO
{
	protected Object handleFilter(DataObject vo) throws br.ufrrj.maratona.cd.DAOException
	{
		Session session = AbstractDAO.currentSession();
		Criteria criterios = session.createCriteria(Aluno.class);
		
		if(vo instanceof AlunoVO)
		{
			AlunoVO alunoVO = (AlunoVO) vo;
			
			if(alunoVO.getIdResolucao() != null)
			{
				criterios.createAlias("resolucaos", "resolucaos");
				criterios.add(Restrictions.eq("resolucaos.id", alunoVO.getIdResolucao()));
			}
		}

		return criterios;
	}

	protected org.hibernate.Criteria handleXmlExport(br.ufrj.coppetec.ValueObject vo, org.hibernate.Session session) throws br.ufrrj.maratona.cd.DAOException
	{
		return null;
	}

	protected Object handleRecuperaTopAlunosMes(Session session, Integer quantidadeAlunos) throws DAOException
	{
		session = AbstractDAO.currentSession();
		Criteria criterios = session.createCriteria(Aluno.class);

		criterios.createAlias("resolucaos", "resolucaos");
		
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.groupProperty("id"));
		projectionList.add(Projections.property("nome"));
		projectionList.add(Projections.rowCount(), "problemas");
		criterios.setProjection(projectionList);
		
		criterios.addOrder(Order.desc("problemas"));
		
		if(quantidadeAlunos != null && quantidadeAlunos > 0)
		{
			criterios.setMaxResults(quantidadeAlunos);
		}
		
		ResultTransformer resultTransformer = new ResultTransformer()
		{

			public Object transformTuple(Object[] tuple, String[] aliases)
			{
				return tuple;
			}

			public List transformList(List list)
			{
				List<AlunoVO> vos = new ArrayList<AlunoVO>();
				
				for(Object[] result:(List<Object[]>) list)
				{
					AlunoVO alunoVO = new AlunoVO();
					alunoVO.setIdAluno((Long) result[0]);
					alunoVO.setNome((String) result[1]);
					alunoVO.setQuantidadeProblemasResolvidos((Integer) result[2]);
					
					vos.add(alunoVO);
				}
				
				return vos;
			}
		};
		
		criterios.setResultTransformer(resultTransformer);
		
		return criterios;
	}
}
