
// license-header java merge-point
package br.ufrrj.maratona.web.maratona.detalharAluno;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

import org.andromda.presentation.bpm4struts.ViewContainer;

import br.ufrrj.maratona.ServiceLocator;
import br.ufrrj.maratona.cd.Aluno;
import br.ufrrj.maratona.cd.AlunoImpl;
import br.ufrrj.maratona.cd.Resolucao;
import br.ufrrj.maratona.util.Util;
import br.ufrrj.maratona.vo.ProblemaVO;
import br.ufrrj.maratona.vo.ResolucaoVO;

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
        
        if(!Util.checkEmpty(alunos))
            aluno = (Aluno) alunos.iterator().next();
        
        form.setNome(aluno.getNome());
        
        Collection<ResolucaoVO> problemasResolvidos = new ArrayList<ResolucaoVO>();
        
        for(Resolucao resolucao: (Collection<Resolucao>) aluno.getResolucaos())
        {
            ResolucaoVO resolucaoVO = new ResolucaoVO();
            
            resolucaoVO.setIdResolucao(resolucao.getId());
            
            resolucaoVO.setDataStr(new SimpleDateFormat("dd/MM/yyyy").format(resolucao.getData()));
            
            ProblemaVO problemaVO = new ProblemaVO();
            problemaVO.setUrl(resolucao.getProblema().getUrl());
            
            resolucaoVO.setProblemaVO(problemaVO);
            
            problemasResolvidos.add(resolucaoVO);
        }
        
        form.setProblemasResolvidos(problemasResolvidos);
    }
}
