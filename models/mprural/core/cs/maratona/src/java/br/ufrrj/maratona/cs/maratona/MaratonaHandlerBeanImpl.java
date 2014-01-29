// license-header java merge-point
package br.ufrrj.maratona.cs.maratona;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import br.ufrrj.maratona.ServiceLocator;
import br.ufrrj.maratona.cd.Aluno;
import br.ufrrj.maratona.cd.AlunoImpl;
import br.ufrrj.maratona.cd.Problema;
import br.ufrrj.maratona.cd.ProblemaImpl;
import br.ufrrj.maratona.cd.Resolucao;
import br.ufrrj.maratona.eo.DificuldadeProblema;
import br.ufrrj.maratona.vo.ProblemaVO;

/**
 * @see br.ufrrj.maratona.cs.maratona.MaratonaHandler
 */
public class MaratonaHandlerBeanImpl extends MaratonaHandlerBean implements MaratonaHandler, MaratonaHandlerLocal
{
	public Resolucao handleIncluirResolucao(Long idAluno, ProblemaVO problemaVO)
	{
		Aluno aluno = new AlunoImpl();
    	aluno.setId(idAluno);
    	
    	Collection alunos = new ArrayList();

    	try
		{
			alunos = ServiceLocator.instance().getMaratonaHandlerBI().selectAluno(aluno);
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
		}
    	
    	if(alunos != null && !alunos.isEmpty())
    		aluno = (Aluno) alunos.iterator().next();
    	
    	((AlunoImpl) aluno).adicionarProblema(new ProblemaImpl(), new Date(), "", DificuldadeProblema.DIFICIL);
    	
    	try
    	{
			ServiceLocator.instance().getMaratonaHandlerBI().updateAluno(aluno);
		}
    	catch (Exception exception)
    	{
			exception.printStackTrace();
		}
    	
		return null;
	}

	public Problema handleIncluirProblema(ProblemaVO problemaVO) 
	{
		return null;
	}
}
