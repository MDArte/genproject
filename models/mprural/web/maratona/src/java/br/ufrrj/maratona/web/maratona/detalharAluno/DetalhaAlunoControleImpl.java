
// license-header java merge-point
package br.ufrrj.maratona.web.maratona.detalharAluno;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.andromda.presentation.bpm4struts.ViewContainer;

import br.ufrrj.maratona.ServiceLocator;
import br.ufrrj.maratona.cd.Aluno;
import br.ufrrj.maratona.cd.AlunoImpl;
import br.ufrrj.maratona.cd.Problema;
import br.ufrrj.maratona.cd.Resolucao;
import br.ufrrj.maratona.vo.ProblemaVO;

/**
 * @see br.ufrrj.maratona.web.maratona.DetalharAluno.DetalhaAlunoControle
 */
public class DetalhaAlunoControleImpl extends DetalhaAlunoControle
{
    /**
     * @see br.ufrrj.maratona.web.maratona.DetalharAluno.DetalhaAlunoControle#carregandoDados(br.ufrrj.maratona.web.maratona.DetalharAluno.CarregandoDadosForm)
     */
    public final void carregandoDados(br.ufrrj.maratona.web.maratona.detalharAluno.CarregandoDadosForm form, ViewContainer container) throws Exception
    {
    	if(form.getIdAluno() == null)
    		return;
    	
    	Aluno aluno = new AlunoImpl();
    	aluno.setId(form.getIdAluno());
    	
    	Collection alunos = ServiceLocator.instance().getMaratonaHandlerBI().selectAluno(aluno);
    	
    	if(alunos != null && !alunos.isEmpty())
    		aluno = (Aluno) alunos.iterator().next();
    	
    	form.setNome(aluno.getNome());
    	
    	Collection<ProblemaVO> problemasResolvidos = new ArrayList<ProblemaVO>();
    	
    	for(Resolucao resolucao: (Collection<Resolucao>) aluno.getResolucaos())
    	{
    		ProblemaVO problemaVO = new ProblemaVO();
    		
    		problemaVO.setIdResolucao(resolucao.getId());
    		
    		problemaVO.setDataStr(new SimpleDateFormat("dd/MM/yyyy").format(resolucao.getData()));
    		problemaVO.setUrl(resolucao.getProblema().getUrl());
    		
    		problemasResolvidos.add(problemaVO);
    	}
    	
    	form.setProblemasResolvidos(problemasResolvidos);
    }
}