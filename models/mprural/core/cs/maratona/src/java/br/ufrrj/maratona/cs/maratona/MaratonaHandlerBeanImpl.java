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
import br.ufrrj.maratona.cd.ResolucaoImpl;
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
    		throw new MaratonaException("matatona.handler.incluir.resolucao.encontrar.aluno", exception);
    	}
    	
    	if(alunos != null && !alunos.isEmpty())
    		aluno = (Aluno) alunos.iterator().next();
    	
    	Problema problema = incluirProblema(resolucaoVO.getProblemaVO());
    	
    	((AlunoImpl) aluno).adicionarProblema(problema, resolucaoVO.getData(), resolucaoVO.getCodigo(), resolucaoVO.getDificuldade());
    	
    	try
    	{
    		alunos = ServiceLocator.instance().getMaratonaHandlerBI().updateAluno(aluno);
    	}
    	catch (Exception exception)
    	{
    		throw new MaratonaException("matatona.handler.incluir.resolucao.inserir.resolucao", exception);
    	}
    	
    	if(alunos == null || (alunos != null && alunos.isEmpty()))
		{
    		throw new MaratonaException("matatona.handler.incluir.resolucao.inserir.resolucao");
    	}
    	
    	aluno = (Aluno) alunos.iterator().next();
    	
    	Resolucao resolucao = new ResolucaoImpl();
    	
    	for(Resolucao resolucaoProblema:(Collection<Resolucao>) aluno.getResolucaos())
    	{
    		if(resolucaoProblema.getData().compareTo(resolucaoVO.getData()) == 0 && resolucaoProblema.getCodigo().equals(resolucaoVO.getCodigo()))
    		{
	    		if(resolucaoProblema.getProblema().getId().equals(problema.getId()))
	    		{
	    			resolucao = resolucaoProblema;
	    			break;
	    		}
    		}
    	}
    	
		return resolucao;
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
			throw new MaratonaException("matatona.handler.incluir.problema.encontrar.problema", exception);
		}

		/*
		 * Cria Novo Problema
		 */

		if(problemas == null || (problemas != null && problemas.isEmpty()))
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
				throw new MaratonaException("matatona.handler.incluir.problema.inserir.problema", exception);
			}
		}
		
		if(problemas == null || (problemas != null && problemas.isEmpty()))
		{
			throw new MaratonaException("matatona.handler.incluir.problema.inserir.problema");
		}
		
		return (Problema) problemas.iterator().next();
	}
}

