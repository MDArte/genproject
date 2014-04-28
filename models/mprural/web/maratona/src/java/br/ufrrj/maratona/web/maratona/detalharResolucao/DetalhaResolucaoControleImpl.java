

// license-header java merge-point
package br.ufrrj.maratona.web.maratona.detalharResolucao;

import java.text.SimpleDateFormat;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.andromda.presentation.bpm4struts.ViewContainer;

import br.ufrrj.maratona.ServiceLocator;
import br.ufrrj.maratona.action.FilterAction;
import br.ufrrj.maratona.cd.Aluno;
import br.ufrrj.maratona.cd.AlunoImpl;
import br.ufrrj.maratona.cd.Resolucao;
import br.ufrrj.maratona.cd.ResolucaoImpl;
import br.ufrrj.maratona.util.Util;
import br.ufrrj.maratona.vo.AlunoVO;

/**
 * @see br.ufrrj.maratona.web.maratona.detalharResolucao.DetalhaResolucaoControle
 */
public class DetalhaResolucaoControleImpl extends DetalhaResolucaoControle
{
    /**
     * @see br.ufrrj.maratona.web.maratona.detalharResolucao.DetalhaResolucaoControle#carregandoDados(br.ufrrj.maratona.web.maratona.detalharResolucao.CarregandoDadosForm)
     */
    public final void carregandoDados(br.ufrrj.maratona.web.maratona.detalharResolucao.CarregandoDadosForm form, ViewContainer container) throws Exception
    {
    	if(form.getIdResolucao() == null)
    		return;
    	
    	Resolucao resolucao = new ResolucaoImpl();
    	resolucao.setId(form.getIdResolucao());
    	
    	Collection resolucoes = ServiceLocator.instance().getMaratonaHandlerBI().selectResolucao(resolucao);
    	
		if(!Util.checkEmpty(resolucoes))
    		resolucao  = (Resolucao) resolucoes.iterator().next();
		
		form.setData(new SimpleDateFormat("dd/MM/yyyy").format(resolucao.getData()));
		form.setUrl(resolucao.getProblema().getUrl());
		form.setResolucao(resolucao.getCodigo());
		form.setDificuldade(resolucao.getDificuldade().getValue());
		
		AlunoVO alunoVO = new AlunoVO();
		alunoVO.setIdResolucao(form.getIdResolucao());
		
		Collection alunos = ServiceLocator.instance().getMaratonaHandlerBI().filterAluno(new AlunoImpl(), new FilterAction(alunoVO, null));
		
		if(!Util.checkEmpty(resolucoes))
		{
    		Aluno aluno = (Aluno) alunos.iterator().next();
    		
    		form.setNomeAluno(aluno.getNome());
    		form.setIdAluno(aluno.getId());
		}
    }
}