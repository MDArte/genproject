// license-header java merge-point
package br.ufrrj.maratona.cs.maratona;

import java.util.ArrayList;
import java.util.Collection;

import br.ufrrj.maratona.ServiceLocator;
import br.ufrrj.maratona.action.FilterAction;
import br.ufrrj.maratona.cd.Aluno;
import br.ufrrj.maratona.cd.AlunoImpl;
import br.ufrrj.maratona.cd.Problema;
import br.ufrrj.maratona.cd.ProblemaImpl;
import br.ufrrj.maratona.cd.Resolucao;
import br.ufrrj.maratona.eo.CategoriaProblema;
import br.ufrrj.maratona.vo.ProblemaVO;
import br.ufrrj.maratona.vo.ResolucaoVO;

/**
 * @see br.ufrrj.maratona.cs.maratona.MaratonaHandler
 */
public class MaratonaHandlerBeanImpl extends MaratonaHandlerBean implements MaratonaHandler, MaratonaHandlerLocal
{
	public Resolucao handleIncluirResolucao(Long idAluno, ResolucaoVO resolucaoVO) throws MaratonaException
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
    	
    	Problema problema = incluirProblema(resolucaoVO.getProblemaVO());
    	
    	((AlunoImpl) aluno).adicionarProblema(problema, resolucaoVO.getData(), "", resolucaoVO.getDificuldade());
    	
    	try
    	{
			alunos = ServiceLocator.instance().getMaratonaHandlerBI().updateAluno(aluno);
		}
    	catch (Exception exception)
    	{
			exception.printStackTrace();
		}
    	
		return null;
	}

	public Problema handleIncluirProblema(ProblemaVO problemaVO) throws MaratonaException
	{
		/*
		 * Procura se o Problema Já Existe
		 */
		ProblemaVO problemaFilterVO = new ProblemaVO();
		problemaFilterVO.setUrl(problemaVO.getUrl());
		problemaFilterVO.setUrlEquals(true);
		
		Collection problemas = new ArrayList();
		
		try
		{
			problemas = ServiceLocator.instance().getMaratonaHandlerBI().filterProblema(new ProblemaImpl(), new FilterAction(problemaFilterVO, null));
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
			return null;
		}

		/*
		 * Cria Novo Problema
		 */
		if(problemas == null || problemas != null && problemas.isEmpty())
		{
			Problema problema = new ProblemaImpl();
			problema.setUrl(problemaVO.getUrl());
			problema.setCategoria(CategoriaProblema.INICIANTE);
			
			try
			{
				problemas = ServiceLocator.instance().getMaratonaHandlerBI().insertProblema(problema);
			}
			catch (Exception exception)
			{
				exception.printStackTrace();
				return null;
			}
		}
		
		if(problemas != null && !problemas.isEmpty())
		{
			return (Problema) problemas.iterator().next();
		}		
		
		return null;
	}
}
